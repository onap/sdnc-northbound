/*-
 * ============LICENSE_START=======================================================
 * ONAP : SDN-C
 * ================================================================================
 * Copyright (C) 2020 FUJITSU Intellectual Property. All rights
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

import org.onap.ccsdk.sli.core.sli.provider.MdsalHelper;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalserviceService;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Defines a base implementation for your provider. This class extends from a
 * helper class which provides storage for the most commonly used components of
 * the MD-SAL. Additionally the base class provides some basic LOGging and
 * initialization / clean up methods.
 *
 */
public class OpticalServiceProvider implements AutoCloseable, OpticalserviceService {

	private static final Logger LOG = LoggerFactory.getLogger(OpticalServiceProvider.class);

	private static final String APPLICATION_NAME = "optical-service";

	private final ExecutorService executor;

	protected DataBroker dataBroker;
	protected NotificationPublishService notificationService;
	protected RpcProviderRegistry rpcRegistry;
	protected BindingAwareBroker.RpcRegistration<OpticalserviceService> rpcRegistration;
	private final OpticalServiceClient opticalServiceClient;

	public OpticalServiceProvider(final DataBroker dataBroker,
			final NotificationPublishService notificationPublishService, final RpcProviderRegistry rpcProviderRegistry,
			final OpticalServiceClient opticalServiceClient) {

		LOG.info("Creating provider for {}", APPLICATION_NAME);
		executor = Executors.newFixedThreadPool(1);
		this.dataBroker = dataBroker;
		this.notificationService = notificationPublishService;
		this.rpcRegistry = rpcProviderRegistry;
		this.opticalServiceClient = opticalServiceClient;
		initialize();
	}

	public void initialize() {
		LOG.info("Initializing provider for {}", APPLICATION_NAME);
		rpcRegistration = rpcRegistry.addRpcImplementation(OpticalserviceService.class, this);
		LOG.info("Initialization complete for {}", APPLICATION_NAME);
	}

	protected void initializeChild() {
		// Override if you have custom initialization intelligence
	}

	@Override
	public void close() throws Exception {
		LOG.info("Closing provider for {}", APPLICATION_NAME);
		executor.shutdown();
		rpcRegistration.close();
		LOG.info("Successfully closed provider for {}", APPLICATION_NAME);
	}

	@Override
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
					serviceDataBuilder.setResponseCode(errorCode);
					serviceDataBuilder.setResponseMessage(errorMessage);

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

	@Override
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
					serviceDataBuilder.setResponseCode(errorCode);
					serviceDataBuilder.setResponseMessage(errorMessage);
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
}
