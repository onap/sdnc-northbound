/*-
 * ============LICENSE_START=======================================================
 * ONAP : SDN-C
 * ================================================================================
 * Copyright (C) 2019-2020 Fujitsu Limited Intellectual Property. All rights
 *                             reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.onap.sdnc.northbound;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.checkerframework.checker.fenum.qual.SwingElementOrientation;
import org.onap.ccsdk.sli.core.sli.provider.MdsalHelper;
import org.onap.ccsdk.sli.core.sli.provider.SvcLogicService;
import org.opendaylight.mdsal.binding.api.DataBroker;
import org.opendaylight.mdsal.binding.api.NotificationPublishService;
import org.opendaylight.mdsal.binding.api.RpcProviderService;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreate;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDelete;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteOutputBuilder;
import org.opendaylight.yangtools.binding.Rpc;
import org.opendaylight.yangtools.concepts.Registration;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Defines a base implementation for your provider. This class extends from a
 * helper class which provides storage for the most commonly used components of
 * the MD-SAL. Additionally the base class provides some basic LOGging and
 * initialization / clean up methods.
 *
 */
@Singleton
@Component(immediate = true)
public class OpticalServiceProvider implements AutoCloseable {

	private static final Logger LOG = LoggerFactory.getLogger(OpticalServiceProvider.class);

	private static final String APPLICATION_NAME = "optical-service";

	private final ExecutorService executor;

	protected DataBroker dataBroker;
	protected RpcProviderService rpcProviderService;
	protected Registration rpcRegistration;
	private final OpticalServiceClient opticalServiceClient;

	private static SvcLogicService svcLogic = null;

	@Inject
	@Activate
	public OpticalServiceProvider(@Reference final DataBroker dataBroker,
	        @Reference final RpcProviderService rpcProviderService) {
				this(dataBroker, rpcProviderService, new OpticalServiceClient(getSvcLogicService()));
			}

	public OpticalServiceProvider(final DataBroker dataBroker,final RpcProviderService rpcProviderService,
			final OpticalServiceClient opticalServiceClient) {

		LOG.info("Creating provider for {}", APPLICATION_NAME);
		executor = Executors.newFixedThreadPool(1);
		this.dataBroker = dataBroker;
		this.rpcProviderService= rpcProviderService;
		this.opticalServiceClient = opticalServiceClient;
		initialize();
	}

	public void initialize() {
		LOG.info("Initializing provider for {}", APPLICATION_NAME);

        if (rpcRegistration == null) {
            if (rpcProviderService != null) {
                rpcRegistration = rpcProviderService.registerRpcImplementations(
					(OpticalServiceCreate) this::opticalServiceCreate,
					(OpticalServiceDelete) this::opticalServiceDelete

				);
            }
        }
		LOG.info("Initialization complete for {}", APPLICATION_NAME);
	}

	protected void initializeChild() {
		// Override if you have custom initialization intelligence
	}

	@Override
	@PreDestroy
	@Deactivate
	public void close() throws Exception {
		LOG.info("Closing provider for {}", APPLICATION_NAME);
		executor.shutdown();
		rpcRegistration.close();
		LOG.info("Successfully closed provider for {}", APPLICATION_NAME);
	}

	public ListenableFuture<RpcResult<OpticalServiceCreateOutput>> opticalServiceCreate(
			OpticalServiceCreateInput input) {
		final String svcOperation = "optical-service-create";

		Properties parms = new Properties();
		OpticalServiceCreateOutputBuilder serviceDataBuilder = new OpticalServiceCreateOutputBuilder();

		LOG.info(svcOperation + " called.");
		if (input == null) {
			LOG.debug("exiting " + svcOperation + " because of invalid input");
			serviceDataBuilder.setResponseCode("403");
			RpcResult<OpticalServiceCreateOutput> rpcResult = RpcResultBuilder.<OpticalServiceCreateOutput>status(true)
					.withResult(serviceDataBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}
		// add input to parms
		LOG.info("Adding INPUT data for " + svcOperation + " input: " + input);
		OpticalServiceCreateInputBuilder inputBuilder = new OpticalServiceCreateInputBuilder(input);
		MdsalHelper.toProperties(parms, inputBuilder.build());
		Properties outputParams = new Properties();
		String errorCode = null;

		// Call SLI sync method
		try {
			if (opticalServiceClient.hasGraph(APPLICATION_NAME, svcOperation, null, "sync")) {
				try {
					outputParams = opticalServiceClient.execute(APPLICATION_NAME, svcOperation, null, "sync",
							serviceDataBuilder, parms);
					errorCode = outputParams.getProperty("error-code");
					LOG.info("Response Code" + errorCode);
					String errorMessage = outputParams.getProperty("error-message");
                                        String ackFinalIndicator = outputParams.getProperty("ack-final-indicator");
					serviceDataBuilder.setResponseCode(errorCode);
					serviceDataBuilder.setResponseMessage(errorMessage);
                                        serviceDataBuilder.setAckFinalIndicator(ackFinalIndicator);

				} catch (Exception e) {
					LOG.error("Caught exception executing service LOGic for " + svcOperation, e);
					serviceDataBuilder.setResponseCode("500");
				}
			} else {
				LOG.error("No service LOGic active for OpticalService: '" + svcOperation + "'");
				serviceDataBuilder.setResponseCode("503");
			}
		} catch (Exception e) {
			LOG.error("Caught exception looking for service LOGic", e);
			serviceDataBuilder.setResponseCode("500");
		}

		if (!("0".equals(errorCode) || "200".equals(errorCode))) {
			LOG.error("Returned FAILED for " + svcOperation + " error code: '" + errorCode + "'");
		} else {
			LOG.info("Returned SUCCESS for " + svcOperation + " ");
		}

		RpcResult<OpticalServiceCreateOutput> rpcResult = RpcResultBuilder.<OpticalServiceCreateOutput>status(true)
				.withResult(serviceDataBuilder.build()).build();
		// return error
		return Futures.immediateFuture(rpcResult);
	}

	public ListenableFuture<RpcResult<OpticalServiceDeleteOutput>> opticalServiceDelete(
			OpticalServiceDeleteInput input) {
		final String svcOperation = "optical-service-delete";

		Properties parms = new Properties();
		OpticalServiceDeleteOutputBuilder serviceDataBuilder = new OpticalServiceDeleteOutputBuilder();


		LOG.info(svcOperation + " called.");

		if (input == null) {
			LOG.debug("exiting " + svcOperation + " because of invalid input");
			serviceDataBuilder.setResponseCode("Input is null");
			RpcResult<OpticalServiceDeleteOutput> rpcResult = RpcResultBuilder.<OpticalServiceDeleteOutput>status(true)
					.withResult(serviceDataBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// add input to parms
		LOG.info("Adding INPUT data for " + svcOperation + " input: " + input);
		OpticalServiceDeleteInputBuilder inputBuilder = new OpticalServiceDeleteInputBuilder(input);
		MdsalHelper.toProperties(parms, inputBuilder.build());
		Properties outputParams = new Properties();

		// Call SLI sync method
		try {

			if (opticalServiceClient.hasGraph(APPLICATION_NAME, svcOperation, null, "sync")) {
				LOG.info("OofpcipocClient has a Directed Graph for '" + svcOperation + "'");

				try {
					outputParams = opticalServiceClient.execute(APPLICATION_NAME, svcOperation, null, "sync",
							serviceDataBuilder, parms);
					String errorCode = outputParams.getProperty("error-code");
					LOG.info("Response Code" + errorCode);
                                        String errorMessage = outputParams.getProperty("error-message");
                                        String ackFinalIndicator = outputParams.getProperty("ack-final-indicator");
                                        serviceDataBuilder.setResponseCode(errorCode);
                                        serviceDataBuilder.setResponseMessage(errorMessage);
                                        serviceDataBuilder.setAckFinalIndicator(ackFinalIndicator);
					
				} catch (Exception e) {
					LOG.error("Caught exception executing service LOGic for " + svcOperation, e);
					serviceDataBuilder.setResponseCode("500");
				}
			} else {
				LOG.error("No service LOGic active for Oofpcipoc: '" + svcOperation + "'");
				serviceDataBuilder.setResponseCode("503");
			}
		} catch (Exception e) {
			LOG.error("Caught exception looking for service LOGic", e);
			serviceDataBuilder.setResponseCode("500");
		}

		String errorCode = serviceDataBuilder.getResponseCode();

		if (!("0".equals(errorCode) || "200".equals(errorCode))) {
			LOG.error("Returned FAILED for " + svcOperation + " error code: '" + errorCode + "'");
		} else {
			LOG.info("Returned SUCCESS for " + svcOperation + " ");
		}

		RpcResult<OpticalServiceDeleteOutput> rpcResult = RpcResultBuilder.<OpticalServiceDeleteOutput>status(true)
				.withResult(serviceDataBuilder.build()).build();

		return Futures.immediateFuture(rpcResult);
	}

	   private static SvcLogicService getSvcLogicService() {
		if (svcLogic == null) {
			svcLogic = findSvcLogicService();
		}

		return (svcLogic);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
    private static SvcLogicService findSvcLogicService() {
		BundleContext bctx = FrameworkUtil.getBundle(SvcLogicService.class).getBundleContext();

		SvcLogicService svcLogic = null;

		// Get SvcLogicService reference
        ServiceReference sref = bctx.getServiceReference(SvcLogicService.NAME);
		if (sref != null) {
			svcLogic = (SvcLogicService) bctx.getService(sref);

		} else {
			LOG.warn("Cannot find service reference for " + SvcLogicService.NAME);

		}

		return (svcLogic);
	}
}
