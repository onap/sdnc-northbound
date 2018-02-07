package org.onap.sdnc.northbound;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.OptimisticLockFailedException;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.BrgTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.BrgTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.BrgTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.BrgTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ContrailRouteTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ContrailRouteTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ContrailRouteTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ContrailRouteTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.GENERICRESOURCEAPIService;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.NetworkTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.NetworkTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.NetworkTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.NetworkTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.PreloadNetworkTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.PreloadNetworkTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.PreloadNetworkTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.PreloadNetworkTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.PreloadVnfTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.PreloadVnfTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.PreloadVnfTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.PreloadVnfTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.PreloadVnfs;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.PreloadVnfsBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.SecurityZoneTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.SecurityZoneTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.SecurityZoneTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.SecurityZoneTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ServiceTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ServiceTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ServiceTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ServiceTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.Services;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ServicesBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.TunnelxconnTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.TunnelxconnTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.TunnelxconnTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.TunnelxconnTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VfModuleTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VfModuleTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VfModuleTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VfModuleTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VnfTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VnfTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VnfTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VnfTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.brg.response.information.BrgResponseInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.contrail.route.response.information.ContrailRouteResponseInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.network.response.information.NetworkResponseInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.preload.data.PreloadData;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.preload.data.PreloadDataBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.preload.model.information.VnfPreloadList;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.preload.model.information.VnfPreloadListBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.preload.model.information.VnfPreloadListKey;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.request.information.RequestInformation;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.sdnc.request.header.SdncRequestHeader;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.sdnc.request.header.SdncRequestHeader.SvcAction;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.security.zone.response.information.SecurityZoneResponseInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.data.ServiceData;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.data.ServiceDataBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.model.infrastructure.Service;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.model.infrastructure.ServiceBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.model.infrastructure.ServiceKey;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.response.information.ServiceResponseInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.status.ServiceStatus.RequestStatus;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.status.ServiceStatus.RpcAction;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.status.ServiceStatusBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.tunnelxconn.response.information.TunnelxconnResponseInformationBuilder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;

/**
 * Defines a base implementation for your provider. This class extends from a
 * helper class which provides storage for the most commonly used components of
 * the MD-SAL. Additionally the base class provides some basic logging and
 * initialization / clean up methods.
 *
 * To use this, copy and paste (overwrite) the following method into the
 * TestApplicationProviderModule class which is auto generated under
 * src/main/java in this project (created only once during first compilation):
 *
 * <pre>
 *
 * &#64;Override
 * public java.lang.AutoCloseable createInstance() {
 *
 * 	// final GENERIC-RESOURCE-APIProvider provider = new
 * 	// GENERIC-RESOURCE-APIProvider();
 * 	final GenericResourceApiProvider provider = new GenericResourceApiProvider();
 * 	provider.setDataBroker(getDataBrokerDependency());
 * 	provider.setNotificationService(getNotificationServiceDependency());
 * 	provider.setRpcRegistry(getRpcRegistryDependency());
 * 	provider.initialize();
 * 	return new AutoCloseable() {
 *
 * 		&#64;Override
 * 		public void close() throws Exception {
 * 			// TODO: CLOSE ANY REGISTRATION OBJECTS CREATED USING ABOVE
 * 			// BROKER/NOTIFICATION
 * 			// SERVIE/RPC REGISTRY
 * 			provider.close();
 * 		}
 * 	};
 * }
 *
 * </pre>
 */

public class GenericResourceApiProvider implements AutoCloseable, GENERICRESOURCEAPIService {

	private static final String APP_NAME = "generic-resource-api";
	private static final String CALLED_STR = "{} called.";
	private static final String NULL_OR_EMPTY_ERROR_LOG = "exiting {} because of null or empty service-instance-id";
	private static final String NULL_OR_EMPTY_ERROR_PARAM = "invalid input, null or empty service-instance-id";
	private static final String ADDING_INPUT_DATA_LOG = "Adding INPUT data for {} [{}] input: {}";
	private static final String ADDING_OPERATIONAL_DATA_LOG = "Adding OPERATIONAL data for {} [{}] operational-data: {}";
	private static final String OPERATIONAL_DATA_PARAM = "operational-data";
	private static final String NO_SERVICE_LOGIC_ACTIVE = "No service logic active for ";
	private static final String SERVICE_LOGIC_SEARCH_ERROR_MESSAGE = "Caught exception looking for service logic";
	private static final String ERROR_CODE_PARAM = "error-code";
	private static final String ERROR_MESSAGE_PARAM = "error-message";
	private static final String ACK_FINAL_PARAM = "ack-final";
	private static final String SERVICE_OBJECT_PATH_PARAM = "service-object-path";
	private static final String UPDATING_MDSAL_ERROR_MESSAGE = "Caught Exception updating MD-SAL for {} [{}] \n";
	private static final String RETURNED_FAILED_MESSAGE = "Returned FAILED for {} [{}] {}";
	private static final String UPDATING_MDSAL_INFO_MESSAGE = "Updating MD-SAL for {} [{}] ServiceData: {}";
	private static final String UPDATED_MDSAL_INFO_MESSAGE = "Updated MD-SAL for {} [{}]";
	private static final String RETURNED_SUCCESS_MESSAGE = "Returned SUCCESS for {} [{}] {}";
	private static final String NON_NULL_PARAM = "non-null";
	private static final String NULL_PARAM = "null";
    private static final String SERVICE_LOGIC_EXECUTION_ERROR_MESSAGE = "Caught exception executing service logic for {} ";
    private static final String UPDATING_TREE_INFO_MESSAGE = "Updating OPERATIONAL tree.";
    private static final String EMPTY_SERVICE_INSTANCE_MESSAGE = "exiting {} because the service-instance does not have any service data in SDNC";
    private static final String INVALID_INPUT_ERROR_MESSAGE = "invalid input: the service-instance does not have any service data in SDNC";
    private static final String ALLOTTED_RESOURCE_ID_PARAM = "allotted-resource-id";
    private static final String ERROR_NETWORK_ID = "error";

	private final Logger log = LoggerFactory.getLogger(GenericResourceApiProvider.class);
	private final ExecutorService executor;
	private final GenericResourceApiSvcLogicServiceClient svcLogicClient;

    protected DataBroker dataBroker;
	protected NotificationPublishService notificationService;
	protected RpcProviderRegistry rpcRegistry;
	protected BindingAwareBroker.RpcRegistration<GENERICRESOURCEAPIService> rpcRegistration;

    public GenericResourceApiProvider(
			DataBroker dataBroker,
			NotificationPublishService notificationPublishService,
			RpcProviderRegistry rpcProviderRegistry,
			GenericResourceApiSvcLogicServiceClient client
	) {
		log.info("Creating provider for {}", APP_NAME);
		executor = Executors.newFixedThreadPool(1);
		setDataBroker(dataBroker);
		setNotificationService(notificationPublishService);
		setRpcRegistry(rpcProviderRegistry);
		svcLogicClient = client;
		initialize();

	}

	public void initialize() {
		log.info("Initializing provider for {}", APP_NAME);
		// Create the top level containers
		createContainers();
		try {
			GenericResourceApiUtil.loadProperties();
		} catch (Exception e) {
			log.error("Caught Exception while trying to load properties file", e);
		}

		log.info("Initialization complete for {}", APP_NAME);
	}

	protected void initializeChild() {
		// Override if you have custom initialization intelligence
	}

	@Override
	public void close() throws Exception {
		log.info("Closing provider for {}", APP_NAME);
		executor.shutdown();
		rpcRegistration.close();
		log.info("Successfully closed provider for {}", APP_NAME);
	}

	private static class Iso8601Util {

		private static TimeZone timeZone = TimeZone.getTimeZone("UTC");
		private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		private Iso8601Util() {}

		static {
			dateFormat.setTimeZone(timeZone);
		}
		private static String now() {
			return dateFormat.format(new Date());
		}
	}

	public void setDataBroker(DataBroker dataBroker) {
		this.dataBroker = dataBroker;
		if (log.isDebugEnabled()) {
			log.debug("DataBroker set to {}", dataBroker == null ? NULL_PARAM : NON_NULL_PARAM);
		}
	}

	public void setNotificationService(NotificationPublishService notificationService) {
		this.notificationService = notificationService;
		if (log.isDebugEnabled()) {
			log.debug("Notification Service set to {}", notificationService == null ? NULL_PARAM : NON_NULL_PARAM);
		}
	}

	public void setRpcRegistry(RpcProviderRegistry rpcRegistry) {
		this.rpcRegistry = rpcRegistry;
		if (log.isDebugEnabled()) {
			log.debug("RpcRegistry set to {}", rpcRegistry == null ? NULL_PARAM : NON_NULL_PARAM);
		}
	}

	private void createContainers() {

		final WriteTransaction t = dataBroker.newReadWriteTransaction();

		// Create the service-instance container
		t.merge(LogicalDatastoreType.CONFIGURATION, InstanceIdentifier.create(Services.class),
				new ServicesBuilder().build());
		t.merge(LogicalDatastoreType.OPERATIONAL, InstanceIdentifier.create(Services.class),
				new ServicesBuilder().build());

		// Create the PreloadVnfs container
		t.merge(LogicalDatastoreType.CONFIGURATION, InstanceIdentifier.create(PreloadVnfs.class),
				new PreloadVnfsBuilder().build());
		t.merge(LogicalDatastoreType.OPERATIONAL, InstanceIdentifier.create(PreloadVnfs.class),
				new PreloadVnfsBuilder().build());

		try {
			CheckedFuture<Void, TransactionCommitFailedException> checkedFuture = t.submit();
			checkedFuture.get();
			log.info("Create containers succeeded!");

		} catch (InterruptedException | ExecutionException e) {
			log.error("Create containers failed: ", e);
		}
	}

	private void setServiceStatus(ServiceStatusBuilder serviceStatusBuilder, String errorCode, String errorMessage,
			String ackFinal) {
		serviceStatusBuilder.setResponseCode(errorCode);
		serviceStatusBuilder.setResponseMessage(errorMessage);
		serviceStatusBuilder.setFinalIndicator(ackFinal);
		serviceStatusBuilder.setResponseTimestamp(Iso8601Util.now());
	}

	private void setServiceStatus(ServiceStatusBuilder serviceStatusBuilder, RequestInformation requestInformation) {
		if (requestInformation != null && requestInformation.getRequestAction() != null) {
			serviceStatusBuilder.setAction(requestInformation.getRequestAction().toString());
		}
	}

	private void setServiceStatus(ServiceStatusBuilder serviceStatusBuilder, SdncRequestHeader requestHeader) {
		if (requestHeader != null && requestHeader.getSvcAction() != null) {
			switch (requestHeader.getSvcAction()) {
			case Assign:
				serviceStatusBuilder.setRpcAction(RpcAction.Assign);
				break;
			case Unassign:
				serviceStatusBuilder.setRpcAction(RpcAction.Unassign);
				break;
			case Activate:
				serviceStatusBuilder.setRpcAction(RpcAction.Activate);
				break;
			case Deactivate:
				serviceStatusBuilder.setRpcAction(RpcAction.Deactivate);
				break;
			case Delete:
				serviceStatusBuilder.setRpcAction(RpcAction.Delete);
				break;
			default:
				log.error("Unknown SvcAction: {}", requestHeader.getSvcAction());
				break;
			}
		}
	}

	private void getServiceData(String siid, ServiceDataBuilder serviceDataBuilder) {
		// default to config
		getServiceData(siid, serviceDataBuilder, LogicalDatastoreType.CONFIGURATION);
	}

	private void getServiceData(String siid, ServiceDataBuilder serviceDataBuilder, LogicalDatastoreType type) {
		// See if any data exists yet for this siid, if so grab it.
		InstanceIdentifier<Service> serviceInstanceIdentifier = InstanceIdentifier
			.builder(Services.class)
			.child(Service.class, new ServiceKey(siid))
			.build();

		ReadOnlyTransaction readTx = dataBroker.newReadOnlyTransaction();
		Optional<Service> data = Optional.absent();
		try {
			data = readTx.read(type, serviceInstanceIdentifier).get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Caught Exception reading MD-SAL ({}) data for [{}] ", type, siid, e);
		}

		if (data != null && data.isPresent()) {
			ServiceData serviceData = data.get().getServiceData();
			if (serviceData != null) {
				log.info("Read MD-SAL ({}) data for [{}] ServiceData: {}", type, siid, serviceData);
				serviceDataBuilder.setSdncRequestHeader(serviceData.getSdncRequestHeader());
				serviceDataBuilder.setRequestInformation(serviceData.getRequestInformation());
				serviceDataBuilder.setServiceInformation(serviceData.getServiceInformation());
				serviceDataBuilder.setServiceRequestInput(serviceData.getServiceRequestInput());
				serviceDataBuilder.setServiceTopology(serviceData.getServiceTopology());
				serviceDataBuilder.setServiceLevelOperStatus(serviceData.getServiceLevelOperStatus());
				serviceDataBuilder.setNetworks(serviceData.getNetworks());
				serviceDataBuilder.setVnfs(serviceData.getVnfs());
				serviceDataBuilder.setProvidedAllottedResources(serviceData.getProvidedAllottedResources());
				serviceDataBuilder.setConsumedAllottedResources(serviceData.getConsumedAllottedResources());
				// service-instance-id needs to be set
			} else {
				log.info("No service-data found in MD-SAL ({}) for [{}]", type, siid);
			}
		} else {
			log.info("No data found in MD-SAL ({}) for [{}]", type, siid);
		}
	}

	private void getPreloadData(String vnfName, String vnfType, PreloadDataBuilder preloadDataBuilder) {
		// default to config
		getPreloadData(vnfName, vnfType, preloadDataBuilder, LogicalDatastoreType.CONFIGURATION);
	}

	private void getPreloadData(String preloadName, String preloadType, PreloadDataBuilder preloadDataBuilder,
			LogicalDatastoreType type) {
		// See if any data exists yet for this name/type, if so grab it.
		InstanceIdentifier<VnfPreloadList> preloadInstanceIdentifier = InstanceIdentifier
			.builder(PreloadVnfs.class)
			.child(VnfPreloadList.class, new VnfPreloadListKey(preloadName, preloadType))
			.build();

		ReadOnlyTransaction readTx = dataBroker.newReadOnlyTransaction();
		Optional<VnfPreloadList> data = Optional.absent();
		try {
			data = readTx.read(type, preloadInstanceIdentifier).get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Caught Exception reading MD-SAL ({}) for [{},{}] ", type, preloadName, preloadType, e);
		}

		if (data != null && data.isPresent()) {
			PreloadData preloadData = data.get().getPreloadData();
			if (preloadData != null) {
				log.info("Read MD-SAL ({}) data for [{},{}] PreloadData: {}", type, preloadName, preloadType,
						preloadData);
				preloadDataBuilder.setVnfTopologyInformation(preloadData.getVnfTopologyInformation());
				preloadDataBuilder.setNetworkTopologyInformation(preloadData.getNetworkTopologyInformation());
				preloadDataBuilder.setOperStatus(preloadData.getOperStatus());
			} else {
				log.info("No preload-data found in MD-SAL ({}) for [{},{}] ", type, preloadName, preloadType);
			}
		} else {
			log.info("No data found in MD-SAL ({}) for [{},{}] ", type, preloadName, preloadType);
		}
	}

	private void saveService(final Service entry, boolean merge, LogicalDatastoreType storeType) {
		// Each entry will be identifiable by a unique key, we have to create that
		// identifier
		InstanceIdentifier<Service> path = InstanceIdentifier
			.builder(Services.class)
			.child(Service.class, entry.getKey())
			.build();

		trySaveEntry(entry, merge, storeType, path);
	}

	private void savePreloadList(final VnfPreloadList entry, boolean merge, LogicalDatastoreType storeType) {

		// Each entry will be identifiable by a unique key, we have to create that
		// identifier
		InstanceIdentifier<VnfPreloadList> path = InstanceIdentifier
			.builder(PreloadVnfs.class)
			.child(VnfPreloadList.class, entry.getKey())
			.build();

		trySaveEntry(entry, merge, storeType, path);
	}

	private <T extends DataObject> void trySaveEntry(T entry, boolean merge, LogicalDatastoreType storeType,
		InstanceIdentifier<T> path) {
		int tries = 2;
		while (true) {
			try {
				save(entry, merge, storeType, path);
				break;
			} catch (OptimisticLockFailedException e) {
				if (--tries <= 0) {
					log.debug("Got OptimisticLockFailedException on last try - failing ");
					throw new IllegalStateException(e);
				}
				log.debug("Got OptimisticLockFailedException - trying again ");
			}
			catch (TransactionCommitFailedException ex){
				log.debug("Update DataStore failed");
				throw new IllegalStateException(ex);
			}
		}
	}

	private <T extends DataObject> void save(T entry, boolean merge, LogicalDatastoreType storeType,
		InstanceIdentifier<T> path)
		throws TransactionCommitFailedException {
		WriteTransaction tx = dataBroker.newWriteOnlyTransaction();
		if (merge) {
			tx.merge(storeType, path, entry);
		} else {
			tx.put(storeType, path, entry);
		}
		tx.submit().checkedGet();
		log.debug("Update DataStore succeeded");
	}

	private void deleteService(final Service entry, LogicalDatastoreType storeType) {
		// Each entry will be identifiable by a unique key, we have to create
		// that identifier
		InstanceIdentifier<Service> path = InstanceIdentifier
			.builder(Services.class)
			.child(Service.class, entry.getKey())
			.build();

		tryDeleteEntry(storeType, path);
	}

	private void tryDeleteEntry(LogicalDatastoreType storeType, InstanceIdentifier<Service> path) {
		int tries = 2;
		while (true) {
			try {
				delete(storeType, path);
				break;
			} catch (OptimisticLockFailedException e) {
				if (--tries <= 0) {
					log.debug("Got OptimisticLockFailedException on last try - failing ");
					throw new IllegalStateException(e);
				}
				log.debug("Got OptimisticLockFailedException - trying again ");
			}
			catch (TransactionCommitFailedException ex){
				log.debug("Update DataStore failed");
				throw new IllegalStateException(ex);
			}
		}
	}

	private void delete(LogicalDatastoreType storeType, InstanceIdentifier<Service> path)
		throws TransactionCommitFailedException {
		WriteTransaction tx = dataBroker.newWriteOnlyTransaction();
		tx.delete(storeType, path);
		tx.submit().checkedGet();
		log.debug("DataStore delete succeeded");
	}

	@Override
	public Future<RpcResult<ServiceTopologyOperationOutput>> serviceTopologyOperation(
			ServiceTopologyOperationInput input) {

		final String svcOperation = "service-topology-operation";
		ServiceData serviceData;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info(CALLED_STR, svcOperation);
		// create a new response object
		ServiceTopologyOperationOutputBuilder responseBuilder = new ServiceTopologyOperationOutputBuilder();

		if (input == null || input.getServiceInformation() == null
				|| input.getServiceInformation().getServiceInstanceId() == null
				|| input.getServiceInformation().getServiceInstanceId().length() == 0) {
			log.debug(NULL_OR_EMPTY_ERROR_LOG, svcOperation);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage(NULL_OR_EMPTY_ERROR_PARAM);
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<ServiceTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<ServiceTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Grab the service instance ID from the input buffer
		String siid = input.getServiceInformation().getServiceInstanceId();

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		ServiceDataBuilder serviceDataBuilder = new ServiceDataBuilder();
		getServiceData(siid, serviceDataBuilder);

		ServiceDataBuilder operDataBuilder = new ServiceDataBuilder();
		getServiceData(siid, operDataBuilder, LogicalDatastoreType.OPERATIONAL);

		// Set the serviceStatus based on input
		setServiceStatus(serviceStatusBuilder, input.getSdncRequestHeader());
		setServiceStatus(serviceStatusBuilder, input.getRequestInformation());

		/*
		 * // setup a service-data object builder // ACTION service-topology-operation
		 * // INPUT: // USES uses service-operation-information // OUTPUT: // uses
		 * topology-response-common; // uses service-response-information;
		 */

		log.info(ADDING_INPUT_DATA_LOG, svcOperation, siid, input);
		ServiceTopologyOperationInputBuilder inputBuilder = new ServiceTopologyOperationInputBuilder(input);
		GenericResourceApiUtil.toProperties(parms, inputBuilder.build());

		log.info(ADDING_OPERATIONAL_DATA_LOG, svcOperation, siid,
				operDataBuilder.build());
		GenericResourceApiUtil.toProperties(parms, OPERATIONAL_DATA_PARAM, operDataBuilder);

		// Call SLI sync method
		// Get SvcLogicService reference

		ErrorObject error = new ErrorObject("200", "");
		Properties respProps = null;
		String ackFinal = "Y";
		String serviceObjectPath = null;

		try {
			if (svcLogicClient.hasGraph(APP_NAME, svcOperation, null, "sync")) {
				try {
					respProps = svcLogicClient.execute(APP_NAME, svcOperation, null, "sync", serviceDataBuilder, parms);
				} catch (Exception e) {
					log.error(SERVICE_LOGIC_EXECUTION_ERROR_MESSAGE, svcOperation, e);
					error.setMessage(e.getMessage());
					error.setStatusCode("500");
				}
			} else {
				error.setMessage(NO_SERVICE_LOGIC_ACTIVE + APP_NAME + ": '" + svcOperation + "'");
				error.setStatusCode("503");
			}
		} catch (Exception e) {
			error.setMessage(e.getMessage());
			error.setStatusCode("500");
			log.error(SERVICE_LOGIC_SEARCH_ERROR_MESSAGE, e);
		}

		if (respProps != null) {
			error.setStatusCode(respProps.getProperty(ERROR_CODE_PARAM));
			error.setMessage(respProps.getProperty(ERROR_MESSAGE_PARAM));
			ackFinal = respProps.getProperty(ACK_FINAL_PARAM, "Y");
			serviceObjectPath = respProps.getProperty(SERVICE_OBJECT_PATH_PARAM);
		}

		setServiceStatus(serviceStatusBuilder, error.getStatusCode(), error.getMessage(), ackFinal);
		serviceStatusBuilder.setRequestStatus(RequestStatus.Synccomplete);
		serviceStatusBuilder.setRpcName(svcOperation);

		if (validateErrorObject(error)) {
			responseBuilder.setResponseCode(error.getStatusCode());
			responseBuilder.setResponseMessage(error.getMessage());
			responseBuilder.setAckFinalIndicator(ackFinal);

			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			try {
				saveService(serviceBuilder.build(), true, LogicalDatastoreType.CONFIGURATION);
			} catch (Exception e) {
				log.error(UPDATING_MDSAL_ERROR_MESSAGE, svcOperation, siid, e);
			}
			log.error(RETURNED_FAILED_MESSAGE, svcOperation, siid, responseBuilder.build());
			RpcResult<ServiceTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<ServiceTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {
			serviceData = serviceDataBuilder.build();
			log.info(UPDATING_MDSAL_INFO_MESSAGE, svcOperation, siid, serviceData);

			// service object
			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceData(serviceData);
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			saveService(serviceBuilder.build(), false, LogicalDatastoreType.CONFIGURATION);

			if (isValidRequest(input) && input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Delete)) {
				// Only update operational tree on delete
				log.info("Delete from both CONFIGURATION and OPERATIONAL tree.");
				deleteService(serviceBuilder.build(), LogicalDatastoreType.OPERATIONAL);
				deleteService(serviceBuilder.build(), LogicalDatastoreType.CONFIGURATION);
			}

			ServiceResponseInformationBuilder serviceResponseInformationBuilder = new ServiceResponseInformationBuilder();
			serviceResponseInformationBuilder.setInstanceId(siid);
			serviceResponseInformationBuilder.setObjectPath(serviceObjectPath);
			responseBuilder.setServiceResponseInformation(serviceResponseInformationBuilder.build());

		} catch (Exception e) {
			log.error(UPDATING_MDSAL_ERROR_MESSAGE, svcOperation, siid, e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error(RETURNED_FAILED_MESSAGE, svcOperation, siid, responseBuilder.build());
			RpcResult<ServiceTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<ServiceTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null) {
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info(UPDATED_MDSAL_INFO_MESSAGE, svcOperation, siid);
		log.info(RETURNED_SUCCESS_MESSAGE, svcOperation, siid, responseBuilder.build());

		RpcResult<ServiceTopologyOperationOutput> rpcResult = RpcResultBuilder
				.<ServiceTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}

	private boolean validateErrorObject(ErrorObject error) {
		return
			!error.getStatusCode().isEmpty() && !("0".equals(error.getStatusCode()) || "200".equals(error.getStatusCode()));
	}

	private boolean isValidRequest(ServiceTopologyOperationInput input){
		return input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null;
	}

	@Override
	public Future<RpcResult<VnfTopologyOperationOutput>> vnfTopologyOperation(VnfTopologyOperationInput input) {

		final String svcOperation = "vnf-topology-operation";
		ServiceData serviceData;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info(CALLED_STR, svcOperation);
		// create a new response object
		VnfTopologyOperationOutputBuilder responseBuilder = new VnfTopologyOperationOutputBuilder();

		if (input == null || input.getServiceInformation() == null
				|| input.getServiceInformation().getServiceInstanceId() == null
				|| input.getServiceInformation().getServiceInstanceId().length() == 0) {
			log.debug(NULL_OR_EMPTY_ERROR_LOG, svcOperation);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage(NULL_OR_EMPTY_ERROR_PARAM);
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<VnfTopologyOperationOutput> rpcResult = RpcResultBuilder.<VnfTopologyOperationOutput>status(true)
					.withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Grab the service instance ID from the input buffer
		String siid = input.getServiceInformation().getServiceInstanceId();

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		if (input.getVnfInformation() == null || input.getVnfInformation().getVnfId() == null
				|| input.getVnfInformation().getVnfId().length() == 0) {
			log.debug("exiting {} because of null or empty vnf-id", svcOperation);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage("invalid input, null or empty vnf-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<VnfTopologyOperationOutput> rpcResult = RpcResultBuilder.<VnfTopologyOperationOutput>status(true)
					.withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		ServiceDataBuilder serviceDataBuilder = new ServiceDataBuilder();
		getServiceData(siid, serviceDataBuilder);

		ServiceDataBuilder operDataBuilder = new ServiceDataBuilder();
		getServiceData(siid, operDataBuilder, LogicalDatastoreType.OPERATIONAL);

		// Set the serviceStatus based on input
		setServiceStatus(serviceStatusBuilder, input.getSdncRequestHeader());
		setServiceStatus(serviceStatusBuilder, input.getRequestInformation());

		//
		// setup a service-data object builder
		// ACTION vnf-topology-operation
		// INPUT:
		// USES sdnc-request-header;
		// USES request-information;
		// USES service-information;
		// USES vnf-request-information
		// OUTPUT:
		// USES vnf-topology-response-body;
		// USES vnf-information
		// USES service-information
		//
		// container service-data
		// uses vnf-configuration-information;
		// uses oper-status;

		log.info(ADDING_INPUT_DATA_LOG, svcOperation, siid, input);
		VnfTopologyOperationInputBuilder inputBuilder = new VnfTopologyOperationInputBuilder(input);
		GenericResourceApiUtil.toProperties(parms, inputBuilder.build());

		log.info(ADDING_OPERATIONAL_DATA_LOG, svcOperation, siid,
				operDataBuilder.build());
		GenericResourceApiUtil.toProperties(parms, OPERATIONAL_DATA_PARAM, operDataBuilder);

		// Call SLI sync method
		// Get SvcLogicService reference

		Properties respProps = null;
		ErrorObject error = new ErrorObject("200", "");
		String ackFinal = "Y";
		String serviceObjectPath = null;

		try {
			if (svcLogicClient.hasGraph(APP_NAME, svcOperation, null, "sync")) {
				try {
					respProps = svcLogicClient.execute(APP_NAME, svcOperation, null, "sync", serviceDataBuilder, parms);
				} catch (Exception e) {
					log.error(SERVICE_LOGIC_EXECUTION_ERROR_MESSAGE, svcOperation, e);
					error.setMessage(e.getMessage());
					error.setStatusCode("500");
				}
			} else {
				error.setMessage(NO_SERVICE_LOGIC_ACTIVE + APP_NAME + ": '" + svcOperation + "'");
				error.setStatusCode("503");
			}
		} catch (Exception e) {
			error.setStatusCode("500");
			error.setMessage(e.getMessage());
			log.error(SERVICE_LOGIC_SEARCH_ERROR_MESSAGE, e);
		}

		if (respProps != null) {
			error.setMessage(respProps.getProperty(ERROR_MESSAGE_PARAM));
			error.setStatusCode(respProps.getProperty(ERROR_CODE_PARAM));
			ackFinal = respProps.getProperty(ACK_FINAL_PARAM, "Y");
			serviceObjectPath = respProps.getProperty("vnf-object-path");
		}

		setServiceStatus(serviceStatusBuilder, error.getStatusCode(), error.getMessage(), ackFinal);
		serviceStatusBuilder.setRequestStatus(RequestStatus.Synccomplete);
		serviceStatusBuilder.setRpcName(svcOperation);

		if (validateErrorObject(error)) {
			responseBuilder.setResponseCode(error.getStatusCode());
			responseBuilder.setResponseMessage(error.getMessage());
			responseBuilder.setAckFinalIndicator(ackFinal);

			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			try {
				saveService(serviceBuilder.build(), true, LogicalDatastoreType.CONFIGURATION);
				if (isValidRequest(input) &&
					(input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Delete) ||
						input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Activate))) {

					// Only update operational tree on activate or delete
					log.info(UPDATING_TREE_INFO_MESSAGE);
					saveService(serviceBuilder.build(), false, LogicalDatastoreType.OPERATIONAL);
				}
			} catch (Exception e) {
				log.error(UPDATING_MDSAL_ERROR_MESSAGE, svcOperation, siid, e);
			}
			log.error(RETURNED_FAILED_MESSAGE, svcOperation, siid, responseBuilder.build());
			RpcResult<VnfTopologyOperationOutput> rpcResult = RpcResultBuilder.<VnfTopologyOperationOutput>status(true)
					.withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {
			serviceData = serviceDataBuilder.build();
			log.info(UPDATING_MDSAL_INFO_MESSAGE, svcOperation, siid, serviceData);

			// service object
			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceData(serviceData);
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			saveService(serviceBuilder.build(), false, LogicalDatastoreType.CONFIGURATION);

			if (isValidRequest(input) && input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Activate) ) {
				// Only update operational tree on Assign

				log.info(UPDATING_TREE_INFO_MESSAGE);
				saveService(serviceBuilder.build(), false, LogicalDatastoreType.OPERATIONAL);
			}

			ServiceResponseInformationBuilder serviceResponseInformationBuilder = new ServiceResponseInformationBuilder();
			serviceResponseInformationBuilder.setInstanceId(siid);
			serviceResponseInformationBuilder.setObjectPath(serviceObjectPath);
			responseBuilder.setServiceResponseInformation(serviceResponseInformationBuilder.build());

		} catch (Exception e) {
			log.error(UPDATING_MDSAL_ERROR_MESSAGE, svcOperation, siid, e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error(RETURNED_FAILED_MESSAGE, svcOperation, siid, responseBuilder.build());
			RpcResult<VnfTopologyOperationOutput> rpcResult = RpcResultBuilder.<VnfTopologyOperationOutput>status(true)
					.withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null) {
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info(UPDATED_MDSAL_INFO_MESSAGE, svcOperation, siid);
		log.info(RETURNED_SUCCESS_MESSAGE, svcOperation, siid, responseBuilder.build());

		RpcResult<VnfTopologyOperationOutput> rpcResult = RpcResultBuilder.<VnfTopologyOperationOutput>status(true)
				.withResult(responseBuilder.build()).build();
		// return success
		return Futures.immediateFuture(rpcResult);
	}

	private boolean isValidRequest(VnfTopologyOperationInput input){
		return input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null;
	}

	@Override
	public Future<RpcResult<VfModuleTopologyOperationOutput>> vfModuleTopologyOperation(
			VfModuleTopologyOperationInput input) {

		final String svcOperation = "vf-module-topology-operation";
		ServiceData serviceData;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info(CALLED_STR, svcOperation);
		// create a new response object
		VfModuleTopologyOperationOutputBuilder responseBuilder = new VfModuleTopologyOperationOutputBuilder();

		if (input == null || input.getServiceInformation() == null
				|| input.getServiceInformation().getServiceInstanceId() == null
				|| input.getServiceInformation().getServiceInstanceId().length() == 0) {
			log.debug(NULL_OR_EMPTY_ERROR_LOG, svcOperation);
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage(NULL_OR_EMPTY_ERROR_PARAM);
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<VfModuleTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<VfModuleTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		if (input.getVnfInformation() == null || input.getVnfInformation().getVnfId() == null
				|| input.getVnfInformation().getVnfId().length() == 0) {
			log.debug("exiting {} because of null or empty vnf-id", svcOperation);
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, null or empty vnf-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<VfModuleTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<VfModuleTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		if (input.getVfModuleInformation() == null || input.getVfModuleInformation().getVfModuleId() == null
				|| input.getVfModuleInformation().getVfModuleId().length() == 0) {
			log.debug("exiting {} because of null or empty vf-module-id", svcOperation);
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input, vf-module-id is null or empty");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<VfModuleTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<VfModuleTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Grab the service instance ID from the input buffer
		String siid = input.getServiceInformation().getServiceInstanceId();

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		ServiceDataBuilder serviceDataBuilder = new ServiceDataBuilder();
		getServiceData(siid, serviceDataBuilder);

		ServiceDataBuilder operDataBuilder = new ServiceDataBuilder();
		getServiceData(siid, operDataBuilder, LogicalDatastoreType.OPERATIONAL);

		// Set the serviceStatus based on input
		setServiceStatus(serviceStatusBuilder, input.getSdncRequestHeader());
		setServiceStatus(serviceStatusBuilder, input.getRequestInformation());

		//
		// setup a service-data object builder
		// ACTION vnf-topology-operation
		// INPUT:
		// USES sdnc-request-header;
		// USES request-information;
		// USES service-information;
		// USES vnf-request-information
		// OUTPUT:
		// USES vnf-topology-response-body;
		// USES vnf-information
		// USES service-information
		//
		// container service-data
		// uses vnf-configuration-information;
		// uses oper-status;

		log.info(ADDING_INPUT_DATA_LOG, svcOperation, siid, input);
		VfModuleTopologyOperationInputBuilder inputBuilder = new VfModuleTopologyOperationInputBuilder(input);
		GenericResourceApiUtil.toProperties(parms, inputBuilder.build());

		log.info(ADDING_OPERATIONAL_DATA_LOG, svcOperation, siid,
				operDataBuilder.build());
		GenericResourceApiUtil.toProperties(parms, OPERATIONAL_DATA_PARAM, operDataBuilder);

		// Call SLI sync method
		// Get SvcLogicService reference

		ErrorObject error = new ErrorObject("200", "");
		Properties respProps = null;
		String ackFinal = "Y";
		String serviceObjectPath = null;

		try {
			if (svcLogicClient.hasGraph(APP_NAME, svcOperation, null, "sync")) {

				try {
					respProps = svcLogicClient.execute(APP_NAME, svcOperation, null, "sync", serviceDataBuilder, parms);
				} catch (Exception e) {
					log.error("Caught exception executing service logic for " + svcOperation, e);
					error.setMessage(e.getMessage());
					error.setStatusCode("500");
				}
			} else {
				error.setMessage(NO_SERVICE_LOGIC_ACTIVE + APP_NAME + ": '" + svcOperation + "'");
				error.setStatusCode("503");
			}
		} catch (Exception e) {
			error.setMessage(e.getMessage());
			error.setStatusCode("500");
			log.error(SERVICE_LOGIC_SEARCH_ERROR_MESSAGE, e);
		}

		if (respProps != null) {
			error.setStatusCode(respProps.getProperty(ERROR_CODE_PARAM));
			error.setMessage(respProps.getProperty(ERROR_MESSAGE_PARAM));
			ackFinal = respProps.getProperty(ACK_FINAL_PARAM, "Y");
			serviceObjectPath = respProps.getProperty("vf-module-object-path");
		}

		setServiceStatus(serviceStatusBuilder, error.getStatusCode(), error.getMessage(), ackFinal);
		serviceStatusBuilder.setRequestStatus(RequestStatus.Synccomplete);
		serviceStatusBuilder.setRpcName(svcOperation);

		if (validateErrorObject(error)) {
			responseBuilder.setResponseCode(error.getStatusCode());
			responseBuilder.setResponseMessage(error.getStatusCode());
			responseBuilder.setAckFinalIndicator(ackFinal);

			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			try {
				saveService(serviceBuilder.build(), true, LogicalDatastoreType.CONFIGURATION);
			} catch (Exception e) {
				log.error(UPDATING_MDSAL_ERROR_MESSAGE, svcOperation, siid, e);
			}
			log.error(RETURNED_FAILED_MESSAGE, svcOperation, siid, responseBuilder.build());
			RpcResult<VfModuleTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<VfModuleTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {
			serviceData = serviceDataBuilder.build();
			log.info(UPDATING_MDSAL_INFO_MESSAGE, svcOperation, siid, serviceData);

			// service object
			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceData(serviceData);
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			saveService(serviceBuilder.build(), false, LogicalDatastoreType.CONFIGURATION);

			if (input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null) {
				// Only update operational tree on activate or delete
				if (input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Unassign)
						|| input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Activate)) {
					log.info(UPDATING_TREE_INFO_MESSAGE);
					saveService(serviceBuilder.build(), false, LogicalDatastoreType.OPERATIONAL);
				}
			}

			ServiceResponseInformationBuilder serviceResponseInformationBuilder = new ServiceResponseInformationBuilder();
			serviceResponseInformationBuilder.setInstanceId(siid);
			serviceResponseInformationBuilder.setObjectPath(serviceObjectPath);
			responseBuilder.setServiceResponseInformation(serviceResponseInformationBuilder.build());

		} catch (Exception e) {
			log.error(UPDATING_MDSAL_ERROR_MESSAGE, svcOperation, siid, e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error(RETURNED_FAILED_MESSAGE, svcOperation, siid, responseBuilder.build());
			RpcResult<VfModuleTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<VfModuleTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null) {
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info(UPDATED_MDSAL_INFO_MESSAGE, svcOperation, siid);
		log.info(RETURNED_SUCCESS_MESSAGE, svcOperation, siid, responseBuilder.build());

		RpcResult<VfModuleTopologyOperationOutput> rpcResult = RpcResultBuilder
				.<VfModuleTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
		// return success
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<NetworkTopologyOperationOutput>> networkTopologyOperation(
			NetworkTopologyOperationInput input) {

		final String svcOperation = "network-topology-operation";
		ServiceData serviceData;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info(CALLED_STR, svcOperation);
		// create a new response object
		NetworkTopologyOperationOutputBuilder responseBuilder = new NetworkTopologyOperationOutputBuilder();

		if (input == null || input.getServiceInformation() == null
				|| input.getServiceInformation().getServiceInstanceId() == null
				|| input.getServiceInformation().getServiceInstanceId().length() == 0) {
			log.debug(NULL_OR_EMPTY_ERROR_LOG, svcOperation);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage(NULL_OR_EMPTY_ERROR_PARAM);
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<NetworkTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<NetworkTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		String siid = input.getServiceInformation().getServiceInstanceId();

		// Get the service-instance service data from MD-SAL
		ServiceDataBuilder serviceDataBuilder = new ServiceDataBuilder();
		getServiceData(siid, serviceDataBuilder);

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		ServiceData sd = serviceDataBuilder.build();
		if (sd == null || sd.getServiceLevelOperStatus() == null) {
			log.debug(EMPTY_SERVICE_INSTANCE_MESSAGE, svcOperation);
			responseBuilder.setResponseCode("404");
			responseBuilder
					.setResponseMessage(INVALID_INPUT_ERROR_MESSAGE);
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<NetworkTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<NetworkTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		log.info(ADDING_INPUT_DATA_LOG, svcOperation, siid, input);
		NetworkTopologyOperationInputBuilder inputBuilder = new NetworkTopologyOperationInputBuilder(input);
		GenericResourceApiUtil.toProperties(parms, inputBuilder.build());

		// Call SLI sync method
		// Get SvcLogicService reference

		Properties respProps = null;

		ErrorObject error = new ErrorObject("200", "");
		String ackFinal = "Y";
		String networkId = ERROR_NETWORK_ID;
		String serviceObjectPath = null;
		String networkObjectPath = null;

		try {
			if (svcLogicClient.hasGraph(APP_NAME, svcOperation, null, "sync")) {

				try {
					respProps = svcLogicClient.execute(APP_NAME, svcOperation, null, "sync", serviceDataBuilder, parms);
				} catch (Exception e) {
					log.error(SERVICE_LOGIC_EXECUTION_ERROR_MESSAGE, svcOperation, e);
					error.setStatusCode("500");
					error.setMessage(e.getMessage());
				}
			} else {
				error.setStatusCode("503");
				error.setMessage(NO_SERVICE_LOGIC_ACTIVE + APP_NAME + ": '" + svcOperation + "'");
			}
		} catch (Exception e) {
			error.setStatusCode("500");
			error.setMessage(e.getMessage());
			log.error(SERVICE_LOGIC_SEARCH_ERROR_MESSAGE, e);
		}

		if (respProps != null) {
			error.setStatusCode(respProps.getProperty(ERROR_CODE_PARAM));
			error.setMessage(respProps.getProperty(ERROR_MESSAGE_PARAM));
			ackFinal = respProps.getProperty(ACK_FINAL_PARAM, "Y");
			networkId = respProps.getProperty("networkId");
			serviceObjectPath = respProps.getProperty(SERVICE_OBJECT_PATH_PARAM);
			networkObjectPath = respProps.getProperty("network-object-path");
		}

		if (validateErrorObject(error)) {
			responseBuilder.setResponseCode(error.getStatusCode());
			responseBuilder.setResponseMessage(error.getMessage());
			responseBuilder.setAckFinalIndicator(ackFinal);

			log.error(RETURNED_FAILED_MESSAGE, svcOperation, siid, responseBuilder.build());

			RpcResult<NetworkTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<NetworkTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {

			serviceData = serviceDataBuilder.build();
			log.info(UPDATING_MDSAL_INFO_MESSAGE, svcOperation, siid, serviceData);

			// service object
			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceData(serviceData);
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			saveService(serviceBuilder.build(), false, LogicalDatastoreType.CONFIGURATION);

			if (isValidRequest(input) &&
				(input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Activate) ||
					input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Create))) {
				// Only update operational tree on Activate
				log.info(UPDATING_TREE_INFO_MESSAGE);
				saveService(serviceBuilder.build(), false, LogicalDatastoreType.OPERATIONAL);
			}

			NetworkResponseInformationBuilder networkResponseInformationBuilder = new NetworkResponseInformationBuilder();
			networkResponseInformationBuilder.setInstanceId(networkId);
			networkResponseInformationBuilder.setObjectPath(networkObjectPath);
			responseBuilder.setNetworkResponseInformation(networkResponseInformationBuilder.build());

			ServiceResponseInformationBuilder serviceResponseInformationBuilder = new ServiceResponseInformationBuilder();
			serviceResponseInformationBuilder.setInstanceId(siid);
			serviceResponseInformationBuilder.setObjectPath(serviceObjectPath);
			responseBuilder.setServiceResponseInformation(serviceResponseInformationBuilder.build());

		} catch (IllegalStateException e) {
			log.error(UPDATING_MDSAL_ERROR_MESSAGE, svcOperation, siid, e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error(RETURNED_FAILED_MESSAGE, svcOperation, siid, responseBuilder.build());
			RpcResult<NetworkTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<NetworkTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null) {
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info(UPDATED_MDSAL_INFO_MESSAGE, svcOperation, siid);
		log.info(RETURNED_SUCCESS_MESSAGE, svcOperation, siid, responseBuilder.build());

		RpcResult<NetworkTopologyOperationOutput> rpcResult = RpcResultBuilder
				.<NetworkTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}

	private boolean isValidRequest(NetworkTopologyOperationInput input) {
		return input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null;
	}

	@Override
	public Future<RpcResult<ContrailRouteTopologyOperationOutput>> contrailRouteTopologyOperation(
			ContrailRouteTopologyOperationInput input) {

		final String svcOperation = "contrail-route-topology-operation";
		ServiceData serviceData;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info(CALLED_STR, svcOperation);
		// create a new response object
		ContrailRouteTopologyOperationOutputBuilder responseBuilder = new ContrailRouteTopologyOperationOutputBuilder();

		if (input == null || input.getServiceInformation() == null
				|| input.getServiceInformation().getServiceInstanceId() == null
				|| input.getServiceInformation().getServiceInstanceId().length() == 0) {
			log.debug(NULL_OR_EMPTY_ERROR_LOG, svcOperation);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage(NULL_OR_EMPTY_ERROR_PARAM);
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<ContrailRouteTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<ContrailRouteTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		String siid = input.getServiceInformation().getServiceInstanceId();

		// Get the service-instance service data from MD-SAL
		ServiceDataBuilder serviceDataBuilder = new ServiceDataBuilder();
		getServiceData(siid, serviceDataBuilder);

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		ServiceData sd = serviceDataBuilder.build();
		if (sd == null || sd.getServiceLevelOperStatus() == null) {
			log.debug(EMPTY_SERVICE_INSTANCE_MESSAGE, svcOperation);
			responseBuilder.setResponseCode("404");
			responseBuilder
					.setResponseMessage(INVALID_INPUT_ERROR_MESSAGE);
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<ContrailRouteTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<ContrailRouteTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		log.info("Adding INPUT data for " + svcOperation + " [" + siid + "] input: " + input);
		ContrailRouteTopologyOperationInputBuilder inputBuilder = new ContrailRouteTopologyOperationInputBuilder(input);
		GenericResourceApiUtil.toProperties(parms, inputBuilder.build());

		// Call SLI sync method
		// Get SvcLogicService reference

		Properties respProps = null;

		ErrorObject error = new ErrorObject("200", "");
		String ackFinal = "Y";
		String allottedResourceId = ERROR_NETWORK_ID;
		String serviceObjectPath = null;
		String contrailRouteObjectPath = null;

		try {
			if (svcLogicClient.hasGraph(APP_NAME, svcOperation, null, "sync")) {

				try {
					respProps = svcLogicClient.execute(APP_NAME, svcOperation, null, "sync", serviceDataBuilder, parms);
				} catch (Exception e) {
					log.error(SERVICE_LOGIC_EXECUTION_ERROR_MESSAGE, svcOperation, e);
					error.setMessage(e.getMessage());
					error.setStatusCode("500");
				}
			} else {
                error.setMessage(NO_SERVICE_LOGIC_ACTIVE + APP_NAME + ": '" + svcOperation + "'");
                error.setStatusCode("503");
			}
		} catch (Exception e) {
            error.setMessage(e.getMessage());
            error.setStatusCode("500");
			log.error(SERVICE_LOGIC_SEARCH_ERROR_MESSAGE, e);
		}

		if (respProps != null) {
			error.setStatusCode(respProps.getProperty(ERROR_CODE_PARAM));
			error.setMessage(respProps.getProperty(ERROR_MESSAGE_PARAM));
			ackFinal = respProps.getProperty(ACK_FINAL_PARAM, "Y");
			allottedResourceId = respProps.getProperty(ALLOTTED_RESOURCE_ID_PARAM);
			serviceObjectPath = respProps.getProperty(SERVICE_OBJECT_PATH_PARAM);
			contrailRouteObjectPath = respProps.getProperty("contrail-route-object-path");
		}

		if (validateErrorObject(error)) {
			responseBuilder.setResponseCode(error.getStatusCode());
			responseBuilder.setResponseMessage(error.getMessage());
			responseBuilder.setAckFinalIndicator(ackFinal);

			log.error(RETURNED_FAILED_MESSAGE, svcOperation, siid, responseBuilder.build());

			RpcResult<ContrailRouteTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<ContrailRouteTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {

			serviceData = serviceDataBuilder.build();
			log.info(UPDATING_MDSAL_INFO_MESSAGE, svcOperation, siid, serviceData);

			// service object
			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceData(serviceData);
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			saveService(serviceBuilder.build(), false, LogicalDatastoreType.CONFIGURATION);

			if (isValidRequest(input) &&
                (input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Unassign) ||
                    input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Activate))) {
				// Only update operational tree on activate or delete
                log.info(UPDATING_TREE_INFO_MESSAGE);
                saveService(serviceBuilder.build(), false, LogicalDatastoreType.OPERATIONAL);
			}

			ContrailRouteResponseInformationBuilder contrailRouteResponseInformationBuilder = new ContrailRouteResponseInformationBuilder();
			contrailRouteResponseInformationBuilder.setInstanceId(allottedResourceId);
			contrailRouteResponseInformationBuilder.setObjectPath(contrailRouteObjectPath);
			responseBuilder.setContrailRouteResponseInformation(contrailRouteResponseInformationBuilder.build());

			ServiceResponseInformationBuilder serviceResponseInformationBuilder = new ServiceResponseInformationBuilder();
			serviceResponseInformationBuilder.setInstanceId(siid);
			serviceResponseInformationBuilder.setObjectPath(serviceObjectPath);
			responseBuilder.setServiceResponseInformation(serviceResponseInformationBuilder.build());

		} catch (IllegalStateException e) {
			log.error(UPDATING_MDSAL_ERROR_MESSAGE, svcOperation, siid, e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error(RETURNED_FAILED_MESSAGE, svcOperation, siid, responseBuilder.build());
			RpcResult<ContrailRouteTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<ContrailRouteTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null) {
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info(UPDATED_MDSAL_INFO_MESSAGE, svcOperation, siid);
		log.info(RETURNED_SUCCESS_MESSAGE, svcOperation, siid, responseBuilder.build());

		RpcResult<ContrailRouteTopologyOperationOutput> rpcResult = RpcResultBuilder
				.<ContrailRouteTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}

    private boolean isValidRequest(ContrailRouteTopologyOperationInput input) {
        return input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null;
    }

    @Override
	public Future<RpcResult<SecurityZoneTopologyOperationOutput>> securityZoneTopologyOperation(
			SecurityZoneTopologyOperationInput input) {

		final String svcOperation = "security-zone-topology-operation";
		ServiceData serviceData;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info(CALLED_STR, svcOperation);
		// create a new response object
		SecurityZoneTopologyOperationOutputBuilder responseBuilder = new SecurityZoneTopologyOperationOutputBuilder();

		if (input == null || input.getServiceInformation() == null
				|| input.getServiceInformation().getServiceInstanceId() == null
				|| input.getServiceInformation().getServiceInstanceId().length() == 0) {
			log.debug(NULL_OR_EMPTY_ERROR_LOG, svcOperation);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage(NULL_OR_EMPTY_ERROR_PARAM);
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<SecurityZoneTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<SecurityZoneTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		String siid = input.getServiceInformation().getServiceInstanceId();

		// Get the service-instance service data from MD-SAL
		ServiceDataBuilder serviceDataBuilder = new ServiceDataBuilder();
		getServiceData(siid, serviceDataBuilder);

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		ServiceData sd = serviceDataBuilder.build();
		if (sd == null || sd.getServiceLevelOperStatus() == null) {
			log.debug(EMPTY_SERVICE_INSTANCE_MESSAGE, svcOperation);
			responseBuilder.setResponseCode("404");
			responseBuilder
					.setResponseMessage(INVALID_INPUT_ERROR_MESSAGE);
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<SecurityZoneTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<SecurityZoneTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		log.info(ADDING_INPUT_DATA_LOG, svcOperation, siid, input);
		SecurityZoneTopologyOperationInputBuilder inputBuilder = new SecurityZoneTopologyOperationInputBuilder(input);
		GenericResourceApiUtil.toProperties(parms, inputBuilder.build());

		// Call SLI sync method
		// Get SvcLogicService reference

		Properties respProps = null;

		ErrorObject error = new ErrorObject("200", "");
		String ackFinal = "Y";
		String allottedResourceId = ERROR_NETWORK_ID;
		String serviceObjectPath = null;
		String securityZoneObjectPath = null;

		try {
			if (svcLogicClient.hasGraph(APP_NAME, svcOperation, null, "sync")) {

				try {
					respProps = svcLogicClient.execute(APP_NAME, svcOperation, null, "sync", serviceDataBuilder, parms);
				} catch (Exception e) {
					log.error(SERVICE_LOGIC_EXECUTION_ERROR_MESSAGE, svcOperation, e);
					error.setMessage(e.getMessage());
					error.setStatusCode("500");
				}
			} else {
				error.setMessage(NO_SERVICE_LOGIC_ACTIVE + APP_NAME + ": '" + svcOperation + "'");
				error.setStatusCode("503");
			}
		} catch (Exception e) {
			error.setStatusCode("500");
			error.setMessage(e.getMessage());
			log.error(SERVICE_LOGIC_SEARCH_ERROR_MESSAGE, e);
		}

		if (respProps != null) {
			error.setStatusCode(respProps.getProperty(ERROR_CODE_PARAM));
			error.setMessage(respProps.getProperty(ERROR_MESSAGE_PARAM));
			ackFinal = respProps.getProperty(ACK_FINAL_PARAM, "Y");
			allottedResourceId = respProps.getProperty(ALLOTTED_RESOURCE_ID_PARAM);
			serviceObjectPath = respProps.getProperty(SERVICE_OBJECT_PATH_PARAM);
			securityZoneObjectPath = respProps.getProperty("security-zone-object-path");
		}

		if (validateErrorObject(error)) {
			responseBuilder.setResponseCode(error.getStatusCode());
			responseBuilder.setResponseMessage(error.getMessage());
			responseBuilder.setAckFinalIndicator(ackFinal);

			log.error(RETURNED_FAILED_MESSAGE, svcOperation, siid, responseBuilder.build());

			RpcResult<SecurityZoneTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<SecurityZoneTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {

			serviceData = serviceDataBuilder.build();
			log.info(UPDATING_MDSAL_INFO_MESSAGE, svcOperation, siid, serviceData);

			// service object
			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceData(serviceData);
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			saveService(serviceBuilder.build(), false, LogicalDatastoreType.CONFIGURATION);

			if (isValidRequest(input) &&
                (input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Unassign) ||
                    input.getSdncRequestHeader().getSvcAction().equals(SvcAction.Activate))) {
				// Only update operational tree on activate or delete
                log.info(UPDATING_TREE_INFO_MESSAGE);
                saveService(serviceBuilder.build(), false, LogicalDatastoreType.OPERATIONAL);
			}

			SecurityZoneResponseInformationBuilder securityZoneResponseInformationBuilder = new SecurityZoneResponseInformationBuilder();
			securityZoneResponseInformationBuilder.setInstanceId(allottedResourceId);
			securityZoneResponseInformationBuilder.setObjectPath(securityZoneObjectPath);
			responseBuilder.setSecurityZoneResponseInformation(securityZoneResponseInformationBuilder.build());

			ServiceResponseInformationBuilder serviceResponseInformationBuilder = new ServiceResponseInformationBuilder();
			serviceResponseInformationBuilder.setInstanceId(siid);
			serviceResponseInformationBuilder.setObjectPath(serviceObjectPath);
			responseBuilder.setServiceResponseInformation(serviceResponseInformationBuilder.build());

		} catch (IllegalStateException e) {
			log.error(UPDATING_MDSAL_ERROR_MESSAGE, svcOperation, siid, e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error(RETURNED_FAILED_MESSAGE, svcOperation, siid, responseBuilder.build());
			RpcResult<SecurityZoneTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<SecurityZoneTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(error.getStatusCode());
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null) {
			responseBuilder.setResponseMessage(error.getMessage());
		}
		log.info(UPDATED_MDSAL_INFO_MESSAGE, svcOperation, siid);
		log.info(RETURNED_SUCCESS_MESSAGE, svcOperation, siid, responseBuilder.build());

		RpcResult<SecurityZoneTopologyOperationOutput> rpcResult = RpcResultBuilder
				.<SecurityZoneTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}

    private boolean isValidRequest(SecurityZoneTopologyOperationInput input) {
        return input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null;
    }

    @Override
	public Future<RpcResult<TunnelxconnTopologyOperationOutput>> tunnelxconnTopologyOperation(
			TunnelxconnTopologyOperationInput input) {

		final String SVC_OPERATION = "tunnelxconn-topology-operation";
		ServiceData serviceData;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info(CALLED_STR, SVC_OPERATION);
		// create a new response object
		TunnelxconnTopologyOperationOutputBuilder responseBuilder = new TunnelxconnTopologyOperationOutputBuilder();

		if (input == null || input.getServiceInformation() == null
				|| input.getServiceInformation().getServiceInstanceId() == null
				|| input.getServiceInformation().getServiceInstanceId().length() == 0) {
			log.debug(NULL_OR_EMPTY_ERROR_LOG, SVC_OPERATION);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage(NULL_OR_EMPTY_ERROR_PARAM);
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<TunnelxconnTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<TunnelxconnTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		String siid = input.getServiceInformation().getServiceInstanceId();
/*
		// Get the service-instance service data from MD-SAL
		ServiceDataBuilder serviceDataBuilder = new ServiceDataBuilder();
		getServiceData(siid, serviceDataBuilder);

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		ServiceData sd = serviceDataBuilder.build();
		if (sd == null || sd.getServiceLevelOperStatus() == null) {
			log.debug("exiting {} because the service-instance does not have any service data in SDNC", SVC_OPERATION);
			responseBuilder.setResponseCode("404");
			responseBuilder
					.setResponseMessage("invalid input: the service-instance does not have any service data in SDNC");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<TunnelxconnTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<TunnelxconnTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}*/

		log.info(ADDING_INPUT_DATA_LOG, SVC_OPERATION, siid, input);
		TunnelxconnTopologyOperationInputBuilder inputBuilder = new TunnelxconnTopologyOperationInputBuilder(input);
		GenericResourceApiUtil.toProperties(parms, inputBuilder.build());

		// Call SLI sync method
		// Get SvcLogicService reference

		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";
		String allottedResourceId = ERROR_NETWORK_ID;
		String serviceObjectPath = null;
		String tunnelxconnObjectPath = null;

		try {
			if (svcLogicClient.hasGraph(APP_NAME, SVC_OPERATION, null, "sync")) {

				try {
					respProps = svcLogicClient.execute(APP_NAME, SVC_OPERATION, null, "sync",  parms);
				} catch (Exception e) {
					log.error("Caught exception executing service logic for {}", SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = NO_SERVICE_LOGIC_ACTIVE + APP_NAME + ": '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		} catch (Exception e) {
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error(SERVICE_LOGIC_SEARCH_ERROR_MESSAGE, e);
		}

		if (respProps != null) {
			errorCode = respProps.getProperty(ERROR_CODE_PARAM);
			errorMessage = respProps.getProperty(ERROR_MESSAGE_PARAM);
			ackFinal = respProps.getProperty(ACK_FINAL_PARAM, "Y");
			allottedResourceId = respProps.getProperty(ALLOTTED_RESOURCE_ID_PARAM);
			serviceObjectPath = respProps.getProperty(SERVICE_OBJECT_PATH_PARAM);
			tunnelxconnObjectPath = respProps.getProperty("tunnelxconn-object-path");
		}

		if (errorCode != null && errorCode.length() != 0 && !(errorCode.equals("0") || errorCode.equals("200"))) {
			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);

			log.error(RETURNED_FAILED_MESSAGE, SVC_OPERATION, siid, responseBuilder.build());

			RpcResult<TunnelxconnTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<TunnelxconnTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {



			TunnelxconnResponseInformationBuilder tunnelxconnResponseInformationBuilder = new TunnelxconnResponseInformationBuilder();
			tunnelxconnResponseInformationBuilder.setInstanceId(allottedResourceId);
			tunnelxconnResponseInformationBuilder.setObjectPath(tunnelxconnObjectPath);
			responseBuilder.setTunnelxconnResponseInformation(tunnelxconnResponseInformationBuilder.build());

			ServiceResponseInformationBuilder serviceResponseInformationBuilder = new ServiceResponseInformationBuilder();
			serviceResponseInformationBuilder.setInstanceId(siid);
			serviceResponseInformationBuilder.setObjectPath(serviceObjectPath);
			responseBuilder.setServiceResponseInformation(serviceResponseInformationBuilder.build());

		} catch (IllegalStateException e) {
			log.error(UPDATING_MDSAL_ERROR_MESSAGE, SVC_OPERATION, siid, e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error(RETURNED_FAILED_MESSAGE, SVC_OPERATION, siid, responseBuilder.build());
			RpcResult<TunnelxconnTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<TunnelxconnTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null) {
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info(UPDATED_MDSAL_INFO_MESSAGE, SVC_OPERATION, siid);
		log.info(RETURNED_SUCCESS_MESSAGE, SVC_OPERATION, siid, responseBuilder.build());

		RpcResult<TunnelxconnTopologyOperationOutput> rpcResult = RpcResultBuilder
				.<TunnelxconnTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<BrgTopologyOperationOutput>> brgTopologyOperation(BrgTopologyOperationInput input) {
		final String SVC_OPERATION = "brg-topology-operation";
		ServiceData serviceData;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info(CALLED_STR, SVC_OPERATION);
		// create a new response object
		BrgTopologyOperationOutputBuilder responseBuilder = new BrgTopologyOperationOutputBuilder();

		if (input == null || input.getServiceInformation() == null
				|| input.getServiceInformation().getServiceInstanceId() == null
				|| input.getServiceInformation().getServiceInstanceId().length() == 0) {
			log.debug(NULL_OR_EMPTY_ERROR_LOG, SVC_OPERATION);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage(NULL_OR_EMPTY_ERROR_PARAM);
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<BrgTopologyOperationOutput> rpcResult = RpcResultBuilder.<BrgTopologyOperationOutput>status(true)
					.withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		String siid = input.getServiceInformation().getServiceInstanceId();

/*		// Get the service-instance service data from MD-SAL
		ServiceDataBuilder serviceDataBuilder = new ServiceDataBuilder();
		getServiceData(siid, serviceDataBuilder);

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		ServiceData sd = serviceDataBuilder.build();
		if (sd == null || sd.getServiceLevelOperStatus() == null) {
			log.debug("exiting {} because the service-instance does not have any service data in SDNC", SVC_OPERATION);
			responseBuilder.setResponseCode("404");
			responseBuilder
					.setResponseMessage("invalid input: the service-instance does not have any service data in SDNC");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<BrgTopologyOperationOutput> rpcResult = RpcResultBuilder.<BrgTopologyOperationOutput>status(true)
					.withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}*/

		log.info(ADDING_INPUT_DATA_LOG, SVC_OPERATION, siid, input);
		BrgTopologyOperationInputBuilder inputBuilder = new BrgTopologyOperationInputBuilder(input);
		GenericResourceApiUtil.toProperties(parms, inputBuilder.build());

		// Call SLI sync method
		// Get SvcLogicService reference

		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";
		String allottedResourceId = ERROR_NETWORK_ID;
		String serviceObjectPath = null;
		String brgObjectPath = null;

		try {
			if (svcLogicClient.hasGraph(APP_NAME, SVC_OPERATION, null, "sync")) {

				try {
					respProps = svcLogicClient.execute(APP_NAME, SVC_OPERATION, null, "sync", parms);
				} catch (Exception e) {
					log.error("Caught exception executing service logic for {}", SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = NO_SERVICE_LOGIC_ACTIVE + APP_NAME + ": '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		} catch (Exception e) {
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error(SERVICE_LOGIC_SEARCH_ERROR_MESSAGE, e);
		}

		if (respProps != null) {
			errorCode = respProps.getProperty(ERROR_CODE_PARAM);
			errorMessage = respProps.getProperty(ERROR_MESSAGE_PARAM);
			ackFinal = respProps.getProperty(ACK_FINAL_PARAM, "Y");
			allottedResourceId = respProps.getProperty(ALLOTTED_RESOURCE_ID_PARAM);
			serviceObjectPath = respProps.getProperty(SERVICE_OBJECT_PATH_PARAM);
			brgObjectPath = respProps.getProperty("brg-object-path");
		}

		if (errorCode != null && errorCode.length() != 0 && !(errorCode.equals("0") || errorCode.equals("200"))) {
			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);

			log.error(RETURNED_FAILED_MESSAGE, SVC_OPERATION, siid, responseBuilder.build());

			RpcResult<BrgTopologyOperationOutput> rpcResult = RpcResultBuilder.<BrgTopologyOperationOutput>status(true)
					.withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {


			BrgResponseInformationBuilder brgResponseInformationBuilder = new BrgResponseInformationBuilder();
			brgResponseInformationBuilder.setInstanceId(allottedResourceId);
			brgResponseInformationBuilder.setObjectPath(brgObjectPath);
			responseBuilder.setBrgResponseInformation(brgResponseInformationBuilder.build());

			ServiceResponseInformationBuilder serviceResponseInformationBuilder = new ServiceResponseInformationBuilder();
			serviceResponseInformationBuilder.setInstanceId(siid);
			serviceResponseInformationBuilder.setObjectPath(serviceObjectPath);
			responseBuilder.setServiceResponseInformation(serviceResponseInformationBuilder.build());

		} catch (IllegalStateException e) {
			log.error(UPDATING_MDSAL_ERROR_MESSAGE, SVC_OPERATION, siid, e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error(RETURNED_FAILED_MESSAGE, SVC_OPERATION, siid, responseBuilder.build());
			RpcResult<BrgTopologyOperationOutput> rpcResult = RpcResultBuilder.<BrgTopologyOperationOutput>status(true)
					.withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null) {
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info(UPDATED_MDSAL_INFO_MESSAGE, SVC_OPERATION, siid);
		log.info(RETURNED_SUCCESS_MESSAGE, SVC_OPERATION, siid, responseBuilder.build());

		RpcResult<BrgTopologyOperationOutput> rpcResult = RpcResultBuilder.<BrgTopologyOperationOutput>status(true)
				.withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<PreloadVnfTopologyOperationOutput>> preloadVnfTopologyOperation(
			PreloadVnfTopologyOperationInput input) {

		final String SVC_OPERATION = "preload-vnf-topology-operation";
		PreloadData preloadData;
		Properties parms = new Properties();

		log.info(CALLED_STR, SVC_OPERATION);
		// create a new response object
		PreloadVnfTopologyOperationOutputBuilder responseBuilder = new PreloadVnfTopologyOperationOutputBuilder();

		// Result from savePreloadData
		final SettableFuture<RpcResult<Void>> futureResult = SettableFuture.create();

		if (input == null || input.getVnfTopologyInformation() == null
				|| input.getVnfTopologyInformation().getVnfTopologyIdentifier() == null) {
			log.debug("exiting {} because of null input", SVC_OPERATION);
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input: input is null");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadVnfTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<PreloadVnfTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Grab the name and type from the input buffer
		String preload_name = input.getVnfTopologyInformation().getVnfTopologyIdentifier().getVnfName();
		String preload_type = input.getVnfTopologyInformation().getVnfTopologyIdentifier().getVnfType();

		// Make sure we have a preload_name and preload_type
		if (preload_name == null || preload_name.length() == 0 || preload_type == null || preload_type.length() == 0) {
			log.debug("exiting {} vnf-name or vnf-type is null or empty", SVC_OPERATION);
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("invalid input: vnf-name or vnf-type is null or empty");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadVnfTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<PreloadVnfTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		PreloadDataBuilder preloadDataBuilder = new PreloadDataBuilder();
		getPreloadData(preload_name, preload_type, preloadDataBuilder);

		PreloadDataBuilder operDataBuilder = new PreloadDataBuilder();
		getPreloadData(preload_name, preload_type, operDataBuilder, LogicalDatastoreType.OPERATIONAL);

		// setup a preload-data object builder
		// ACTION preload-vnf-topology-operation
		// INPUT:
		// USES sdnc-request-header;
		// USES request-information;
		// uses vnf-topology-information;
		// OUTPUT:
		// USES vnf-topology-response-body;
		//
		// container preload-data
		// uses vnf-topology-information;
		// uses network-topology-information;
		// uses oper-status;

		log.info("Adding INPUT data for {} [{},{}] input: {}", SVC_OPERATION, preload_name, preload_type, input);
		PreloadVnfTopologyOperationInputBuilder inputBuilder = new PreloadVnfTopologyOperationInputBuilder(input);
		GenericResourceApiUtil.toProperties(parms, inputBuilder.build());
		log.info("Adding OPERATIONAL data for {} [{},{}] operational-data: {}", SVC_OPERATION, preload_name,
				preload_type, operDataBuilder.build());
		GenericResourceApiUtil.toProperties(parms, OPERATIONAL_DATA_PARAM, operDataBuilder);

		// Call SLI sync method
		// Get SvcLogicService reference

		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";

		try {
			if (svcLogicClient.hasGraph(APP_NAME, SVC_OPERATION, null, "sync")) {

				try {
					respProps = svcLogicClient.execute(APP_NAME, SVC_OPERATION, null, "sync", preloadDataBuilder, parms);
				} catch (Exception e) {
					log.error("Caught exception executing service logic for {}", SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = NO_SERVICE_LOGIC_ACTIVE + APP_NAME + ": '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		} catch (Exception e) {
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error(SERVICE_LOGIC_SEARCH_ERROR_MESSAGE, e);
		}

		if (respProps != null) {
			errorCode = respProps.getProperty(ERROR_CODE_PARAM);
			errorMessage = respProps.getProperty(ERROR_MESSAGE_PARAM);
			ackFinal = respProps.getProperty(ACK_FINAL_PARAM, "Y");
			// internalError = respProps.getProperty("internal-error", "false");
		}

		if (errorCode != null && errorCode.length() != 0 && !(errorCode.equals("0") || errorCode.equals("200"))) {

			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);

			VnfPreloadListBuilder preloadVnfListBuilder = new VnfPreloadListBuilder();
			preloadVnfListBuilder.setVnfName(preload_name);
			preloadVnfListBuilder.setVnfType(preload_type);
			preloadVnfListBuilder.setPreloadData(preloadDataBuilder.build());
			log.error("Returned FAILED for {} [{},{}] error code: '{}', Reason: '{}'", SVC_OPERATION, preload_name,
					preload_type, errorCode, errorMessage);
			try {
				savePreloadList(preloadVnfListBuilder.build(), true, LogicalDatastoreType.CONFIGURATION);
			} catch (Exception e) {
				log.error("Caught Exception updating MD-SAL for {} [{},{}] \n", SVC_OPERATION, preload_name,
						preload_type, e);
			}
			log.debug("Sending Success rpc result due to external error");
			RpcResult<PreloadVnfTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<PreloadVnfTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {
			preloadData = preloadDataBuilder.build();
			log.info("Updating MD-SAL for {} [{},{}] preloadData: {}", SVC_OPERATION, preload_name, preload_type,
					preloadData);
			// svc-configuration-list
			VnfPreloadListBuilder preloadVnfListBuilder = new VnfPreloadListBuilder();
			preloadVnfListBuilder.setVnfName(preload_name);
			preloadVnfListBuilder.setVnfType(preload_type);
			preloadVnfListBuilder.setPreloadData(preloadData);

			// merge flag sets to false to allow it to be overwritten (not appended)
			savePreloadList(preloadVnfListBuilder.build(), false, LogicalDatastoreType.CONFIGURATION);
			log.info(UPDATING_TREE_INFO_MESSAGE);
			savePreloadList(preloadVnfListBuilder.build(), false, LogicalDatastoreType.OPERATIONAL);
		} catch (Exception e) {
			log.error("Caught Exception updating MD-SAL for {} [{},{}] \n", SVC_OPERATION, preload_name, preload_type,
					e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error("Returned FAILED for {} [{},{}] {}", SVC_OPERATION, preload_name, preload_type,
					responseBuilder.build());
			RpcResult<PreloadVnfTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<PreloadVnfTopologyOperationOutput>status(false).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null) {
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info("Updated MD-SAL for {} [{},{}]", SVC_OPERATION, preload_name, preload_type);
		log.info("Returned SUCCESS for {} [{},{}] {}", SVC_OPERATION, preload_name, preload_type,
				responseBuilder.build());

		RpcResult<PreloadVnfTopologyOperationOutput> rpcResult = RpcResultBuilder
				.<PreloadVnfTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<PreloadNetworkTopologyOperationOutput>> preloadNetworkTopologyOperation(
			PreloadNetworkTopologyOperationInput input) {

		final String SVC_OPERATION = "preload-network-topology-operation";
		PreloadData preloadData;
		Properties parms = new Properties();

		log.info(CALLED_STR, SVC_OPERATION);
		// create a new response object
		PreloadNetworkTopologyOperationOutputBuilder responseBuilder = new PreloadNetworkTopologyOperationOutputBuilder();

		// Result from savePreloadData
		final SettableFuture<RpcResult<Void>> futureResult = SettableFuture.create();

		if (input == null || input.getNetworkTopologyInformation() == null
				|| input.getNetworkTopologyInformation().getNetworkTopologyIdentifier() == null) {

			log.debug("exiting {} because of null input", SVC_OPERATION);
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("input is null");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<PreloadNetworkTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Grab the name and type from the input buffer
		String preload_name = input.getNetworkTopologyInformation().getNetworkTopologyIdentifier().getNetworkName();
		String preload_type = input.getNetworkTopologyInformation().getNetworkTopologyIdentifier().getNetworkType();

		// Make sure we have a preload_name and preload_type
		if (preload_name == null || preload_name.length() == 0 || preload_type == null || preload_type.length() == 0) {
			log.debug("exiting {} because of invalid preload-name", SVC_OPERATION);
			responseBuilder.setResponseCode("403");
			responseBuilder.setResponseMessage("input, invalid preload-name");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<PreloadNetworkTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		PreloadDataBuilder preloadDataBuilder = new PreloadDataBuilder();
		getPreloadData(preload_name, preload_type, preloadDataBuilder);

		PreloadDataBuilder operDataBuilder = new PreloadDataBuilder();
		getPreloadData(preload_name, preload_type, operDataBuilder, LogicalDatastoreType.OPERATIONAL);

		//
		// setup a preload-data object builder
		// ACTION preload-network-topology-operation
		// INPUT:
		// USES sdnc-request-header;
		// USES request-information;
		// uses network-topology-information;
		// OUTPUT:
		// USES vnf-topology-response-body;
		//
		// container preload-data
		// uses vnf-topology-information;
		// uses network-topology-information;
		// uses oper-status;

		log.info("Adding INPUT data for {} [{},{}] input: {}", SVC_OPERATION, preload_name, preload_type, input);
		PreloadNetworkTopologyOperationInputBuilder inputBuilder = new PreloadNetworkTopologyOperationInputBuilder(
				input);
		GenericResourceApiUtil.toProperties(parms, inputBuilder.build());
		log.info("Adding OPERATIONAL data for {} [{},{}] operational-data: {}", SVC_OPERATION, preload_name,
				preload_type, operDataBuilder.build());
		GenericResourceApiUtil.toProperties(parms, OPERATIONAL_DATA_PARAM, operDataBuilder);

		// Call SLI sync method
		// Get SvcLogicService reference

		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";

		try {
			if (svcLogicClient.hasGraph(APP_NAME, SVC_OPERATION, null, "sync")) {

				try {
					respProps = svcLogicClient.execute(APP_NAME, SVC_OPERATION, null, "sync", preloadDataBuilder, parms);
				} catch (Exception e) {
					log.error("Caught exception executing service logic for {}", SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = NO_SERVICE_LOGIC_ACTIVE + APP_NAME + ": '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		} catch (Exception e) {
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error(SERVICE_LOGIC_SEARCH_ERROR_MESSAGE, e);
		}

		if (respProps != null) {
			errorCode = respProps.getProperty(ERROR_CODE_PARAM);
			errorMessage = respProps.getProperty(ERROR_MESSAGE_PARAM);
			ackFinal = respProps.getProperty(ACK_FINAL_PARAM, "Y");
			// internalError = respProps.getProperty("internal-error", "false");
		}

		if (errorCode != null && errorCode.length() != 0 && !(errorCode.equals("0") || errorCode.equals("200"))) {

			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);

			VnfPreloadListBuilder preloadVnfListBuilder = new VnfPreloadListBuilder();
			preloadVnfListBuilder.setVnfName(preload_name);
			preloadVnfListBuilder.setVnfType(preload_type);
			preloadVnfListBuilder.setPreloadData(preloadDataBuilder.build());
			log.error("Returned FAILED for {} [{},{}] error code: '{}', Reason: '{}'", SVC_OPERATION, preload_name,
					preload_type, errorCode, errorMessage);
			try {
				savePreloadList(preloadVnfListBuilder.build(), true, LogicalDatastoreType.CONFIGURATION);
			} catch (Exception e) {
				log.error("Caught Exception updating MD-SAL for {} [{},{}] \n", SVC_OPERATION, preload_name,
						preload_type, e);

			}
			log.debug("Sending Success rpc result due to external error");
			RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<PreloadNetworkTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {
			preloadData = preloadDataBuilder.build();
			log.info("Updating MD-SAL for {} [{},{}] preloadData: {}", SVC_OPERATION, preload_name, preload_type,
					preloadData);
			// svc-configuration-list
			VnfPreloadListBuilder preloadVnfListBuilder = new VnfPreloadListBuilder();
			preloadVnfListBuilder.setVnfName(preload_name);
			preloadVnfListBuilder.setVnfType(preload_type);
			preloadVnfListBuilder.setPreloadData(preloadData);

			// merge flag sets to false to allow it to be overwritten (not appended)
			savePreloadList(preloadVnfListBuilder.build(), false, LogicalDatastoreType.CONFIGURATION);
			log.info(UPDATING_TREE_INFO_MESSAGE);
			savePreloadList(preloadVnfListBuilder.build(), false, LogicalDatastoreType.OPERATIONAL);
		} catch (Exception e) {
			log.error("Caught Exception updating MD-SAL for " + SVC_OPERATION + " [" + preload_name + "," + preload_type
					+ "] \n", e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error("Returned FAILED for {} [{},{}] {}", SVC_OPERATION, preload_name, preload_type,
					responseBuilder.build());
			RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult = RpcResultBuilder
					.<PreloadNetworkTopologyOperationOutput>status(false).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null) {
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info("Updated MD-SAL for {} [{},{}]", SVC_OPERATION, preload_name, preload_type);
		log.info("Returned SUCCESS for {} [{},{}] {}", SVC_OPERATION, preload_name, preload_type,
				responseBuilder.build());

		RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult = RpcResultBuilder
				.<PreloadNetworkTopologyOperationOutput>status(true).withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}

}
