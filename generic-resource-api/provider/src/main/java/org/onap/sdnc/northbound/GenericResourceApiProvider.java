package org.onap.sdnc.northbound;

import static org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType.*;
import static org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.sdnc.request.header.SdncRequestHeader.SvcAction.*;

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
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VfModuleTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VfModuleTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VfModuleTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VfModuleTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VnfTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VnfTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VnfTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VnfTopologyOperationOutputBuilder;
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
 * Defines a base implementation for your provider. This class extends from a helper class
 * which provides storage for the most commonly used components of the MD-SAL. Additionally the
 * base class provides some basic logging and initialization / clean up methods.
 *
 * To use this, copy and paste (overwrite) the following method into the TestApplicationProviderModule
 * class which is auto generated under src/main/java in this project
 * (created only once during first compilation):
 *
 * <pre>

    @Override
    public java.lang.AutoCloseable createInstance() {

         //final GENERIC-RESOURCE-APIProvider provider = new GENERIC-RESOURCE-APIProvider();
         final GenericResourceApiProvider provider = new GenericResourceApiProvider();
         provider.setDataBroker( getDataBrokerDependency() );
         provider.setNotificationService( getNotificationServiceDependency() );
         provider.setRpcRegistry( getRpcRegistryDependency() );
         provider.initialize();
         return new AutoCloseable() {

            @Override
            public void close() throws Exception {
                //TODO: CLOSE ANY REGISTRATION OBJECTS CREATED USING ABOVE BROKER/NOTIFICATION
                //SERVIE/RPC REGISTRY
                provider.close();
            }
        };
    }


    </pre>
 */

public class GenericResourceApiProvider implements AutoCloseable, GENERICRESOURCEAPIService{

    private final Logger log = LoggerFactory.getLogger( GenericResourceApiProvider.class );
    private final String appName = "generic-resource-api";

    private final ExecutorService executor;

    protected DataBroker dataBroker;
    protected NotificationPublishService notificationService;
    protected RpcProviderRegistry rpcRegistry;
    protected BindingAwareBroker.RpcRegistration<GENERICRESOURCEAPIService> rpcRegistration;

    public GenericResourceApiProvider(DataBroker dataBroker2,
                                      NotificationPublishService notificationPublishService,
            RpcProviderRegistry rpcProviderRegistry) {
        log.info( "Creating provider for {}", appName);
        executor = Executors.newFixedThreadPool(1);
        dataBroker = dataBroker2;
        notificationService = notificationPublishService;
        rpcRegistry = rpcProviderRegistry;
        initialize();

    }

    public void initialize(){
        log.info("Initializing provider for {}", appName);
        // Create the top level containers
        createContainers();
        try {
            GenericResourceApiUtil.loadProperties();
        } catch (Exception e) {
            log.error("Caught Exception while trying to load properties file");
        }
        rpcRegistration = rpcRegistry.addRpcImplementation(GENERICRESOURCEAPIService.class, this);

        log.info("Initialization complete for {}", appName);
    }


    protected void initializeChild() {
        //Override if you have custom initialization intelligence
    }


    @Override
    public void close() throws Exception {
        log.info("Closing provider for {}", appName);
        executor.shutdown();
        rpcRegistration.close();
        log.info("Successfully closed provider for {}", appName);
    }

    private static class Iso8601Util
    {
        private static TimeZone tz = TimeZone.getTimeZone("UTC");
        private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        static { df.setTimeZone(tz); }

        private static String now() {
            return df.format(new Date());
        }
    }


    public void setDataBroker(DataBroker dataBroker) {
        this.dataBroker = dataBroker;
        if( log.isDebugEnabled() ){
            log.debug("DataBroker set to {}", (dataBroker==null?"null":"non-null"));
        }
    }

    public void setNotificationService(NotificationPublishService notificationService) {
        this.notificationService = notificationService;
        if( log.isDebugEnabled() ){
            log.debug("Notification Service set to {}", (notificationService==null?"null":"non-null"));
        }
    }

    public void setRpcRegistry(RpcProviderRegistry rpcRegistry) {
        this.rpcRegistry = rpcRegistry;
        if( log.isDebugEnabled() ){
            log.debug("RpcRegistry set to {}", (rpcRegistry==null?"null":"non-null"));
        }
    }

    private void createContainers() {

        final WriteTransaction t = dataBroker.newReadWriteTransaction();

        // Create the service-instance container
        t.merge(CONFIGURATION, InstanceIdentifier.create(Services.class),
                new ServicesBuilder().build());
        t.merge(OPERATIONAL, InstanceIdentifier.create(Services.class),
                new ServicesBuilder().build());

        // Create the PreloadVnfs container
        t.merge(CONFIGURATION, InstanceIdentifier.create(PreloadVnfs.class),
                new PreloadVnfsBuilder().build());
        t.merge(OPERATIONAL, InstanceIdentifier.create(PreloadVnfs.class),
                new PreloadVnfsBuilder().build());

        try {
            CheckedFuture<Void, TransactionCommitFailedException> checkedFuture = t.submit();
            checkedFuture.get();
            log.info("Create containers succeeded!");

        } catch (InterruptedException | ExecutionException e) {
            log.error("Create containers failed: ", e);
        }
    }

    private void setServiceStatus(ServiceStatusBuilder serviceStatusBuilder, String errorCode, String errorMessage, String ackFinal)
    {
        serviceStatusBuilder.setResponseCode(errorCode);
        serviceStatusBuilder.setResponseMessage(errorMessage);
        serviceStatusBuilder.setFinalIndicator(ackFinal);
        serviceStatusBuilder.setResponseTimestamp(Iso8601Util.now());
    }

    private void setServiceStatus(ServiceStatusBuilder serviceStatusBuilder,  RequestInformation requestInformation)
    {
        if (requestInformation != null && requestInformation.getRequestAction() != null) {
            serviceStatusBuilder.setAction(requestInformation.getRequestAction().toString());
        }

        /*
        if (requestInformation != null && requestInformation.getRequestSubAction() != null) {
            switch (requestInformation.getRequestSubAction())
            {
                case SUPP:
                    serviceStatusBuilder.setVnfsdnSubaction(VnfsdnSubaction.SUPP);
                    break;
                case CANCEL:
                    serviceStatusBuilder.setVnfsdnSubaction(VnfsdnSubaction.CANCEL);
                    break;
                default:
                    log.error("Unknown RequestSubAction: " + requestInformation.getRequestSubAction() );
                    break;
            };
        }
        */
    }

    private void setServiceStatus(ServiceStatusBuilder serviceStatusBuilder,  SdncRequestHeader requestHeader)
    {
        if (requestHeader != null && requestHeader.getSvcAction() != null) {
            switch (requestHeader.getSvcAction())
            {
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
                    log.error("Unknown SvcAction: {}", requestHeader.getSvcAction() );
                    break;
            }
        }
    }

    private void getServiceData(String siid, ServiceDataBuilder serviceDataBuilder)
    {
        // default to config
        getServiceData(siid,serviceDataBuilder, CONFIGURATION);
    }


    private void getServiceData(String siid, ServiceDataBuilder serviceDataBuilder, LogicalDatastoreType type)
    {
        // See if any data exists yet for this siid, if so grab it.
        InstanceIdentifier serviceInstanceIdentifier =
                InstanceIdentifier.<Services>builder(Services.class)
                .child(Service.class, new ServiceKey(siid)).build();
        ReadOnlyTransaction readTx = dataBroker.newReadOnlyTransaction();
        Optional<Service> data = null;
        try {
            data = (Optional<Service>) readTx.read(type, serviceInstanceIdentifier).get();
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

    private void getPreloadData(String vnf_name, String vnf_type, PreloadDataBuilder preloadDataBuilder)
    {
        // default to config
        getPreloadData(vnf_name, vnf_type ,preloadDataBuilder, CONFIGURATION);
    }

    private void getPreloadData(String preload_name, String preload_type, PreloadDataBuilder preloadDataBuilder, LogicalDatastoreType type)
    {
        // See if any data exists yet for this name/type, if so grab it.
        InstanceIdentifier preloadInstanceIdentifier = InstanceIdentifier.<PreloadVnfs>builder(PreloadVnfs.class)
                .child(VnfPreloadList.class, new VnfPreloadListKey(preload_name, preload_type)).build();
        ReadOnlyTransaction readTx = dataBroker.newReadOnlyTransaction();
        Optional<VnfPreloadList> data = null;
        try {
            data = (Optional<VnfPreloadList>) readTx.read(type, preloadInstanceIdentifier).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Caught Exception reading MD-SAL ({}) for [{},{}] ", type, preload_name, preload_type, e);
        }

        if (data != null && data.isPresent()) {
            PreloadData preloadData = data.get().getPreloadData();
            if (preloadData != null) {
                log.info("Read MD-SAL ({}) data for [{},{}] PreloadData: {}",
                    type, preload_name, preload_type, preloadData);
                preloadDataBuilder.setVnfTopologyInformation(preloadData.getVnfTopologyInformation());
                preloadDataBuilder.setNetworkTopologyInformation(preloadData.getNetworkTopologyInformation());
                preloadDataBuilder.setOperStatus(preloadData.getOperStatus());
            } else {
                log.info("No preload-data found in MD-SAL ({}) for [{},{}] ", type, preload_name, preload_type);
            }
        } else {
            log.info("No data found in MD-SAL ({}) for [{},{}] ", type, preload_name, preload_type);
        }
    }

    private void saveService(final Service entry, boolean merge, LogicalDatastoreType storeType) throws IllegalStateException {
        // Each entry will be identifiable by a unique key, we have to create that identifier
        InstanceIdentifier.InstanceIdentifierBuilder<Service> serviceBuilder =
                InstanceIdentifier.<Services>builder(Services.class)
                .child(Service.class, entry.getKey());
        InstanceIdentifier<Service> path = serviceBuilder.build();

        int tries = 2;
        while(true) {
            try {
                WriteTransaction tx = dataBroker.newWriteOnlyTransaction();
                if (merge) {
                    tx.merge(storeType, path, entry);
                } else {
                    tx.put(storeType, path, entry);
                }
                tx.submit().checkedGet();
                log.debug("Update DataStore succeeded");
                break;
            } catch (final TransactionCommitFailedException e) {
                if(e instanceof OptimisticLockFailedException) {
                    if(--tries <= 0) {
                        log.debug("Got OptimisticLockFailedException on last try - failing ");
                        throw new IllegalStateException(e);
                    }
                    log.debug("Got OptimisticLockFailedException - trying again ");
                } else {
                    log.debug("Update DataStore failed");
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    private void deleteService(final Service entry, LogicalDatastoreType storeType) throws IllegalStateException {
        // Each entry will be identifiable by a unique key, we have to create
        // that identifier
        InstanceIdentifier.InstanceIdentifierBuilder<Service> serviceListIdBuilder = InstanceIdentifier.<Services> builder(Services.class).child(Service.class, entry.getKey());
        InstanceIdentifier<Service> path = serviceListIdBuilder.build();

        int tries = 2;
        while (true) {
            try {
                WriteTransaction tx = dataBroker.newWriteOnlyTransaction();
                tx.delete(storeType, path);
                tx.submit().checkedGet();
                log.debug("DataStore delete succeeded");
                break;
            } catch (final TransactionCommitFailedException e) {
                if (e instanceof OptimisticLockFailedException) {
                    if (--tries <= 0) {
                        log.debug("Got OptimisticLockFailedException on last try - failing ");
                        throw new IllegalStateException(e);
                    }
                    log.debug("Got OptimisticLockFailedException - trying again ");
                } else {
                    log.debug("Update DataStore failed");
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    private void savePreloadList(final VnfPreloadList entry, boolean merge, LogicalDatastoreType storeType) throws IllegalStateException{

        // Each entry will be identifiable by a unique key, we have to create that identifier
        InstanceIdentifier.InstanceIdentifierBuilder<VnfPreloadList> vnfPreloadListBuilder =
                InstanceIdentifier.<PreloadVnfs>builder(PreloadVnfs.class)
                .child(VnfPreloadList.class, entry.getKey());
        InstanceIdentifier<VnfPreloadList> path = vnfPreloadListBuilder.build();
        int tries = 2;
        while(true) {
            try {
                WriteTransaction tx = dataBroker.newWriteOnlyTransaction();
                if (merge) {
                    tx.merge(storeType, path, entry);
                } else {
                    tx.put(storeType, path, entry);
                }
                tx.submit().checkedGet();
                log.debug("Update DataStore succeeded");
                break;
            } catch (final TransactionCommitFailedException e) {
                if(e instanceof OptimisticLockFailedException) {
                    if(--tries <= 0) {
                        log.debug("Got OptimisticLockFailedException on last try - failing ");
                        throw new IllegalStateException(e);
                    }
                    log.debug("Got OptimisticLockFailedException - trying again ");
                } else {
                    log.debug("Update DataStore failed");
                    throw new IllegalStateException(e);
                }
            }
        }
    }

	@Override
	public Future<RpcResult<ServiceTopologyOperationOutput>> serviceTopologyOperation(
			ServiceTopologyOperationInput input) {

		final String SVC_OPERATION = "service-topology-operation";
		ServiceData serviceData;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info("{} called.",SVC_OPERATION);
		// create a new response object
		ServiceTopologyOperationOutputBuilder responseBuilder = new ServiceTopologyOperationOutputBuilder();

		if(input == null ||
            input.getServiceInformation() == null ||
                input.getServiceInformation().getServiceInstanceId() == null ||
                    input.getServiceInformation().getServiceInstanceId().length() == 0)
        {
			log.debug("exiting {} because of null or empty service-instance-id", SVC_OPERATION);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage("invalid input, null or empty service-instance-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<ServiceTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<ServiceTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Grab the service instance ID from the input buffer
		String siid = input.getServiceInformation().getServiceInstanceId();

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		ServiceDataBuilder serviceDataBuilder = new ServiceDataBuilder();
		getServiceData(siid,serviceDataBuilder);

		ServiceDataBuilder operDataBuilder = new ServiceDataBuilder();
		getServiceData(siid,operDataBuilder, OPERATIONAL );

		// Set the serviceStatus based on input
		setServiceStatus(serviceStatusBuilder, input.getSdncRequestHeader());
		setServiceStatus(serviceStatusBuilder, input.getRequestInformation());

		/*
		// setup a service-data object builder
		// ACTION service-topology-operation
		// INPUT:
		//  USES uses service-operation-information
		// OUTPUT:
		//  uses topology-response-common;
        //  uses service-response-information;
        */

		log.info("Adding INPUT data for {} [{}] input: {}" + input, SVC_OPERATION, siid, input);
		ServiceTopologyOperationInputBuilder inputBuilder = new ServiceTopologyOperationInputBuilder(input);
		GenericResourceApiUtil.toProperties(parms, inputBuilder.build());

		log.info("Adding OPERATIONAL data for {} [{}] operational-data: {}", SVC_OPERATION, siid, operDataBuilder.build());
		GenericResourceApiUtil.toProperties(parms, "operational-data", operDataBuilder);

		// Call SLI sync method
		// Get SvcLogicService reference

		GenericResourceApiSvcLogicServiceClient svcLogicClient = new GenericResourceApiSvcLogicServiceClient();
		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";
        String serviceObjectPath = null;

		try
		{
			if (svcLogicClient.hasGraph(appName, SVC_OPERATION , null, "sync"))
			{

				try
				{
					respProps = svcLogicClient.execute(appName, SVC_OPERATION, null, "sync", serviceDataBuilder, parms);
				}
				catch (Exception e)
				{
					log.error("Caught exception executing service logic for {} ", SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = "No service logic active for "+ appName +": '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		}
		catch (Exception e)
		{
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error("Caught exception looking for service logic", e);
		}


		if (respProps != null)
		{
			errorCode = respProps.getProperty("error-code");
			errorMessage = respProps.getProperty("error-message");
			ackFinal = respProps.getProperty("ack-final", "Y");
            serviceObjectPath = respProps.getProperty("service-object-path");
		}

		setServiceStatus(serviceStatusBuilder,errorCode, errorMessage, ackFinal);
		serviceStatusBuilder.setRequestStatus(RequestStatus.Synccomplete);
		//serviceStatusBuilder.setRpcName(RpcName.ServiceTopologyOperation);
		serviceStatusBuilder.setRpcName(SVC_OPERATION);

		if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {
			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);

			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			try {
				saveService(serviceBuilder.build(), true, CONFIGURATION);
			} catch (Exception e) {
				log.error("Caught Exception updating MD-SAL for {} [{}] \n", SVC_OPERATION, siid, e);
			}
			log.error("Returned FAILED for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());
			RpcResult<ServiceTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<ServiceTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {
			serviceData = serviceDataBuilder.build();
			log.info("Updating MD-SAL for {} [{}] ServiceData: {}", SVC_OPERATION, siid, serviceData);

			// service object
			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceData(serviceData);
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			saveService(serviceBuilder.build(), false, CONFIGURATION);

			if (input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null)
			{
				// Only update operational tree on delete
				if (input.getSdncRequestHeader().getSvcAction().equals(Delete))
				{
					log.info("Delete from both CONFIGURATION and OPERATIONAL tree.");
	                deleteService(serviceBuilder.build(), OPERATIONAL);
	                deleteService(serviceBuilder.build(), CONFIGURATION);
				}
			}

            ServiceResponseInformationBuilder serviceResponseInformationBuilder = new ServiceResponseInformationBuilder();
            serviceResponseInformationBuilder.setInstanceId(siid);
            serviceResponseInformationBuilder.setObjectPath(serviceObjectPath);
            responseBuilder.setServiceResponseInformation(serviceResponseInformationBuilder.build());

		} catch (Exception e) {
			log.error("Caught Exception updating MD-SAL for {} [{}] \n",SVC_OPERATION, siid, e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error("Returned FAILED for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());
			RpcResult<ServiceTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<ServiceTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null)
		{
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info("Updated MD-SAL for {} [{}]", SVC_OPERATION, siid);
		log.info("Returned SUCCESS for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());

		RpcResult<ServiceTopologyOperationOutput> rpcResult =
            RpcResultBuilder.<ServiceTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<VnfTopologyOperationOutput>> vnfTopologyOperation(
			VnfTopologyOperationInput input) {

		final String SVC_OPERATION = "vnf-topology-operation";
		ServiceData serviceData;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info("{} called.", SVC_OPERATION);
		// create a new response object
		VnfTopologyOperationOutputBuilder responseBuilder = new VnfTopologyOperationOutputBuilder();

		if(input == null ||
            input.getServiceInformation() == null ||
                input.getServiceInformation().getServiceInstanceId() == null ||
                    input.getServiceInformation().getServiceInstanceId().length() == 0)
        {
			log.debug("exiting {} because of null or empty service-instance-id", SVC_OPERATION);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage("invalid input, null or empty service-instance-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<VnfTopologyOperationOutput> rpcResult =
					RpcResultBuilder.<VnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Grab the service instance ID from the input buffer
		String siid = input.getServiceInformation().getServiceInstanceId();

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

		if(input.getVnfInformation() == null ||
                input.getVnfInformation().getVnfId() == null ||
                    input.getVnfInformation().getVnfId().length() == 0)
        {
			log.debug("exiting {} because of null or empty vnf-id", SVC_OPERATION);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage("invalid input, null or empty vnf-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<VnfTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<VnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

        /* Mobility wont use vipr intf in 1707

		String preload_name  = null;
		String preload_type = null;
        if (input.getVnfRequestInput() != null &&
                input.getVnfRequestInput().getVnfName() != null && input.getVnfRequestInput().getVnfName().length() != 0) {
            preload_name  = input.getVnfRequestInput().getVnfName();
        }
        if (input.getVnfInformation().getVnfType() != null &&
                input.getVnfInformation().getVnfType().length() != 0) {
		    preload_type = input.getVnfInformation().getVnfType();
        }

		PreloadDataBuilder preloadDataBuilder = new PreloadDataBuilder();
        if (preload_name != null && preload_type != null) {
		    getPreloadData(preload_name, preload_type, preloadDataBuilder);
        }
        else {
            log.info("vnf-name and vnf-type not present in the request");
        }
        */

		ServiceDataBuilder serviceDataBuilder = new ServiceDataBuilder();
		getServiceData(siid,serviceDataBuilder);

		ServiceDataBuilder operDataBuilder = new ServiceDataBuilder();
		getServiceData(siid,operDataBuilder, OPERATIONAL );

		// Set the serviceStatus based on input
		setServiceStatus(serviceStatusBuilder, input.getSdncRequestHeader());
		setServiceStatus(serviceStatusBuilder, input.getRequestInformation());

		//
		// setup a service-data object builder
		// ACTION vnf-topology-operation
		// INPUT:
		//  USES sdnc-request-header;
		//  USES request-information;
		//  USES service-information;
		//  USES vnf-request-information
		// OUTPUT:
		//  USES vnf-topology-response-body;
		//  USES vnf-information
		//  USES service-information
		//
		// container service-data
        //   uses vnf-configuration-information;
        //   uses oper-status;

		log.info("Adding INPUT data for {} [{}] input: {}", SVC_OPERATION, siid, input);
		VnfTopologyOperationInputBuilder inputBuilder = new VnfTopologyOperationInputBuilder(input);
		GenericResourceApiUtil.toProperties(parms, inputBuilder.build());

		log.info("Adding OPERATIONAL data for {} [{}] operational-data: {}", SVC_OPERATION, siid, operDataBuilder.build());
		GenericResourceApiUtil.toProperties(parms, "operational-data", operDataBuilder);

        /*
		log.info("Adding CONFIG data for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] preload-data: " + preloadDataBuilder.build());
		GenericResourceApiUtil.toProperties(parms, "preload-data", preloadDataBuilder);
        */

		// Call SLI sync method
		// Get SvcLogicService reference

		GenericResourceApiSvcLogicServiceClient svcLogicClient = new GenericResourceApiSvcLogicServiceClient();
		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";
        String serviceObjectPath = null;

		try
		{
			if (svcLogicClient.hasGraph(appName, SVC_OPERATION , null, "sync"))
			{

				try
				{
					respProps = svcLogicClient.execute(appName, SVC_OPERATION, null, "sync", serviceDataBuilder, parms);
				}
				catch (Exception e)
				{
					log.error("Caught exception executing service logic for {} ", SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = "No service logic active for "+ appName +": '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		}
		catch (Exception e)
		{
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error("Caught exception looking for service logic", e);
		}

		if (respProps != null)
		{
			errorCode = respProps.getProperty("error-code");
			errorMessage = respProps.getProperty("error-message");
			ackFinal = respProps.getProperty("ack-final", "Y");
            serviceObjectPath = respProps.getProperty("vnf-object-path");
		}

		setServiceStatus(serviceStatusBuilder,errorCode, errorMessage, ackFinal);
		serviceStatusBuilder.setRequestStatus(RequestStatus.Synccomplete);
		//serviceStatusBuilder.setRpcName(RpcName.VnfTopologyOperation);
		serviceStatusBuilder.setRpcName(SVC_OPERATION);

		if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {
			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);

			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			try {
				saveService(serviceBuilder.build(), true, CONFIGURATION);
				if (input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null)
				{
					// Only update operational tree on activate or delete
					if (input.getSdncRequestHeader().getSvcAction().equals(Delete) ||
							input.getSdncRequestHeader().getSvcAction().equals(Activate))
					{
						log.info("Updating OPERATIONAL tree.");
						saveService(serviceBuilder.build(), false, OPERATIONAL);
					}
				}
			} catch (Exception e) {
				log.error("Caught Exception updating MD-SAL for {} [{}] \n", SVC_OPERATION, siid, e);
			}
			log.error("Returned FAILED for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());
			RpcResult<VnfTopologyOperationOutput> rpcResult =
			    RpcResultBuilder.<VnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			// return error
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {
			serviceData = serviceDataBuilder.build();
			log.info("Updating MD-SAL for {} [{}] ServiceData: {}", SVC_OPERATION, siid, serviceData);

			// service object
			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceData(serviceData);
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			saveService(serviceBuilder.build(), false, CONFIGURATION);

			if (input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null)
			{
				// Only update operational tree on Assign
				if (input.getSdncRequestHeader().getSvcAction().equals(Activate))
				{
					log.info("Updating OPERATIONAL tree.");
					saveService(serviceBuilder.build(), false, OPERATIONAL);
				}
			}

            ServiceResponseInformationBuilder serviceResponseInformationBuilder = new ServiceResponseInformationBuilder();
            serviceResponseInformationBuilder.setInstanceId(siid);
            serviceResponseInformationBuilder.setObjectPath(serviceObjectPath);
            responseBuilder.setServiceResponseInformation(serviceResponseInformationBuilder.build());

		} catch (Exception e) {
			log.error("Caught Exception updating MD-SAL for {} [{}] \n", SVC_OPERATION, siid, e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error("Returned FAILED for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());
			RpcResult<VnfTopologyOperationOutput> rpcResult =
			    RpcResultBuilder.<VnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null)
		{
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info("Updated MD-SAL for {} [{}]", SVC_OPERATION, siid);
		log.info("Returned SUCCESS for {} [{}] ", SVC_OPERATION, siid, responseBuilder.build());

		RpcResult<VnfTopologyOperationOutput> rpcResult =
		    RpcResultBuilder.<VnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
		// return success
		return Futures.immediateFuture(rpcResult);
	}

    @Override
    public Future<RpcResult<VfModuleTopologyOperationOutput>> vfModuleTopologyOperation(
            VfModuleTopologyOperationInput input) {

        final String SVC_OPERATION = "vf-module-topology-operation";
        ServiceData serviceData;
        ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
        Properties parms = new Properties();

        log.info("{} called.", SVC_OPERATION);
        // create a new response object
        VfModuleTopologyOperationOutputBuilder responseBuilder = new VfModuleTopologyOperationOutputBuilder();

        if(input == null ||
            input.getServiceInformation() == null ||
                input.getServiceInformation().getServiceInstanceId() == null ||
                    input.getServiceInformation().getServiceInstanceId().length() == 0)
        {
            log.debug("exiting {} because of null or empty service-instance-id", SVC_OPERATION);
            responseBuilder.setResponseCode("403");
            responseBuilder.setResponseMessage("invalid input, null or empty service-instance-id");
            responseBuilder.setAckFinalIndicator("Y");
            RpcResult<VfModuleTopologyOperationOutput> rpcResult =
                    RpcResultBuilder.<VfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
            // return error
            return Futures.immediateFuture(rpcResult);
        }

        if(input.getVnfInformation() == null ||
                input.getVnfInformation().getVnfId() == null ||
                    input.getVnfInformation().getVnfId().length() == 0)
        {
            log.debug("exiting {} because of null or empty vnf-id", SVC_OPERATION);
            responseBuilder.setResponseCode("403");
            responseBuilder.setResponseMessage("invalid input, null or empty vnf-id");
            responseBuilder.setAckFinalIndicator("Y");
            RpcResult<VfModuleTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<VfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
            return Futures.immediateFuture(rpcResult);
        }

        if(input.getVfModuleInformation() == null ||
                input.getVfModuleInformation().getVfModuleId() == null ||
                    input.getVfModuleInformation().getVfModuleId().length() == 0)
        {
            log.debug("exiting {} because of null or empty vf-module-id", SVC_OPERATION);
            responseBuilder.setResponseCode("403");
            responseBuilder.setResponseMessage("invalid input, vf-module-id is null or empty");
            responseBuilder.setAckFinalIndicator("Y");
            RpcResult<VfModuleTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<VfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
            return Futures.immediateFuture(rpcResult);
        }

        // Grab the service instance ID from the input buffer
        String siid = input.getServiceInformation().getServiceInstanceId();

        if (input.getSdncRequestHeader() != null) {
            responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
        }

        /*
        String preload_name  = null;
        String preload_type = null;

        preload_name  = input.getVfModuleRequestInput().getVfModuleName();

        if(input.getVfModuleInformation().getVfModuleType() != null &&
                    input.getVfModuleInformation().getVfModuleType().length() != 0) {
            preload_type = input.getVfModuleInformation().getVfModuleType();
        }

        PreloadDataBuilder preloadDataBuilder = new PreloadDataBuilder();
        if (preload_name != null && preload_type != null) {
            getPreloadData(preload_name, preload_type, preloadDataBuilder);
        }
        else {
            log.debug("vf-module-name and vf-module-type not present in the request.");
        }
        */

        ServiceDataBuilder serviceDataBuilder = new ServiceDataBuilder();
        getServiceData(siid,serviceDataBuilder);

        ServiceDataBuilder operDataBuilder = new ServiceDataBuilder();
        getServiceData(siid,operDataBuilder, OPERATIONAL );

        // Set the serviceStatus based on input
        setServiceStatus(serviceStatusBuilder, input.getSdncRequestHeader());
        setServiceStatus(serviceStatusBuilder, input.getRequestInformation());

        //
        // setup a service-data object builder
        // ACTION vnf-topology-operation
        // INPUT:
        //  USES sdnc-request-header;
        //  USES request-information;
        //  USES service-information;
        //  USES vnf-request-information
        // OUTPUT:
        //  USES vnf-topology-response-body;
        //  USES vnf-information
        //  USES service-information
        //
        // container service-data
        //   uses vnf-configuration-information;
        //   uses oper-status;

        log.info("Adding INPUT data for {} [{}] input: {}", SVC_OPERATION, siid, input);
        VfModuleTopologyOperationInputBuilder inputBuilder = new VfModuleTopologyOperationInputBuilder(input);
        GenericResourceApiUtil.toProperties(parms, inputBuilder.build());

        log.info("Adding OPERATIONAL data for {} [{}] operational-data: {}", SVC_OPERATION, siid, operDataBuilder.build());
        GenericResourceApiUtil.toProperties(parms, "operational-data", operDataBuilder);

        /*
        log.info("Adding CONFIG data for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] preload-data: " +preloadDataBuilder.build());
        GenericResourceApiUtil.toProperties(parms, "preload-data", preloadDataBuilder);
        */

        // Call SLI sync method
        // Get SvcLogicService reference

        GenericResourceApiSvcLogicServiceClient svcLogicClient = new GenericResourceApiSvcLogicServiceClient();
        Properties respProps = null;

        String errorCode = "200";
        String errorMessage = null;
        String ackFinal = "Y";
        String serviceObjectPath = null;

        try
        {
            if (svcLogicClient.hasGraph(appName, SVC_OPERATION , null, "sync"))
            {

                try
                {
                    respProps = svcLogicClient.execute(appName, SVC_OPERATION, null, "sync", serviceDataBuilder, parms);
                }
                catch (Exception e)
                {
                    log.error("Caught exception executing service logic for "+ SVC_OPERATION, e);
                    errorMessage = e.getMessage();
                    errorCode = "500";
                }
            } else {
                errorMessage = "No service logic active for "+ appName +": '" + SVC_OPERATION + "'";
                errorCode = "503";
            }
        }
        catch (Exception e)
        {
            errorCode = "500";
            errorMessage = e.getMessage();
            log.error("Caught exception looking for service logic", e);
        }

        if (respProps != null)
        {
            errorCode = respProps.getProperty("error-code");
            errorMessage = respProps.getProperty("error-message");
            ackFinal = respProps.getProperty("ack-final", "Y");
            serviceObjectPath = respProps.getProperty("vf-module-object-path");
        }

        setServiceStatus(serviceStatusBuilder,errorCode, errorMessage, ackFinal);
        serviceStatusBuilder.setRequestStatus(RequestStatus.Synccomplete);
        serviceStatusBuilder.setRpcName(SVC_OPERATION);

        if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {
            responseBuilder.setResponseCode(errorCode);
            responseBuilder.setResponseMessage(errorMessage);
            responseBuilder.setAckFinalIndicator(ackFinal);

            ServiceBuilder serviceBuilder = new ServiceBuilder();
            serviceBuilder.setServiceInstanceId(siid);
            serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
            try {
                saveService(serviceBuilder.build(), true, CONFIGURATION);
            } catch (Exception e) {
                log.error("Caught Exception updating MD-SAL for {} [{}] \n", SVC_OPERATION, siid, e);
            }
            log.error("Returned FAILED for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());
            RpcResult<VfModuleTopologyOperationOutput> rpcResult =
                    RpcResultBuilder.<VfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
            // return error
            return Futures.immediateFuture(rpcResult);
        }

        // Got success from SLI
        try {
            serviceData = serviceDataBuilder.build();
            log.info("Updating MD-SAL for {} [{}] ServiceData: {}", SVC_OPERATION, siid, serviceData);

            // service object
            ServiceBuilder serviceBuilder = new ServiceBuilder();
            serviceBuilder.setServiceData(serviceData);
            //serviceBuilder.setServiceInstanceId(serviceData.getServiceTopology().getServiceTopologyIdentifier().getServiceInstanceId());
            serviceBuilder.setServiceInstanceId(siid);
            serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
            saveService(serviceBuilder.build(), false, CONFIGURATION);

			if (input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null)
			{
				// Only update operational tree on activate or delete
				if (input.getSdncRequestHeader().getSvcAction().equals(Unassign) ||
						input.getSdncRequestHeader().getSvcAction().equals(Activate))
				{
					log.info("Updating OPERATIONAL tree.");
					saveService(serviceBuilder.build(), false, OPERATIONAL);
				}
			}

            ServiceResponseInformationBuilder serviceResponseInformationBuilder = new ServiceResponseInformationBuilder();
            serviceResponseInformationBuilder.setInstanceId(siid);
            serviceResponseInformationBuilder.setObjectPath(serviceObjectPath);
            responseBuilder.setServiceResponseInformation(serviceResponseInformationBuilder.build());

        } catch (Exception e) {
            log.error("Caught Exception updating MD-SAL for {} [{}] \n", SVC_OPERATION, siid, e);
            responseBuilder.setResponseCode("500");
            responseBuilder.setResponseMessage(e.toString());
            responseBuilder.setAckFinalIndicator("Y");
            log.error("Returned FAILED for {} [{}] ", SVC_OPERATION, siid, responseBuilder.build());
            RpcResult<VfModuleTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<VfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
            return Futures.immediateFuture(rpcResult);
        }

        // Update succeeded
        responseBuilder.setResponseCode(errorCode);
        responseBuilder.setAckFinalIndicator(ackFinal);
        if (errorMessage != null)
        {
            responseBuilder.setResponseMessage(errorMessage);
        }
        log.info("Updated MD-SAL for {} [{}]", SVC_OPERATION, siid);
        log.info("Returned SUCCESS for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());

        RpcResult<VfModuleTopologyOperationOutput> rpcResult =
            RpcResultBuilder.<VfModuleTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
        // return success
        return Futures.immediateFuture(rpcResult);
    }

	@Override
	public Future<RpcResult<NetworkTopologyOperationOutput>> networkTopologyOperation(
			NetworkTopologyOperationInput input) {

		final String SVC_OPERATION = "network-topology-operation";
		ServiceData serviceData;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info("{} called.", SVC_OPERATION );
		// create a new response object
		NetworkTopologyOperationOutputBuilder responseBuilder = new NetworkTopologyOperationOutputBuilder();

		if(input == null ||
            input.getServiceInformation() == null ||
                input.getServiceInformation().getServiceInstanceId() == null ||
                    input.getServiceInformation().getServiceInstanceId().length() == 0)
        {
			log.debug("exiting {} because of null or empty service-instance-id", SVC_OPERATION);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage("invalid input, null or empty service-instance-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<NetworkTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<NetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

        String siid = input.getServiceInformation().getServiceInstanceId();

        // Get the service-instance service data from MD-SAL
        ServiceDataBuilder serviceDataBuilder = new ServiceDataBuilder();
        getServiceData(siid,serviceDataBuilder);

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

        ServiceData sd = serviceDataBuilder.build();
        if (sd == null || sd.getServiceLevelOperStatus() == null)
        {
			log.debug("exiting {} because the service-instance does not have any service data in SDNC", SVC_OPERATION);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage("invalid input: the service-instance does not have any service data in SDNC");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<NetworkTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<NetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}


       /*
		//String preload_name  = null;
		//String preload_type = null;
        // If both network-name and network-type are present in request, get the preload network from MD-SAL
        if (input.getNetworkRequestInput() != null &&
                input.getNetworkRequestInput().getNetworkName() != null &&
                    input.getNetworkRequestInput().getNetworkName().length() != 0) {
            preload_name = input.getNetworkRequestInput().getNetworkName();
        }
        if (input.getNetworkInformation() != null &&
                input.getNetworkInformation().getNetworkType() != null &&
                    input.getNetworkInformation().getNetworkType().length() != 0) {
            preload_type = input.getNetworkInformation().getNetworkType();
        }

		PreloadDataBuilder preloadDataBuilder = new PreloadDataBuilder();
        if (preload_name != null && preload_type != null) {
		    getPreloadData(preload_name, preload_type, preloadDataBuilder);
        }
        else {
			log.debug("network-name and network-type not present in request");
        }
        */

		log.info("Adding INPUT data for {} [{}] input: {}", SVC_OPERATION, siid, input);
		NetworkTopologyOperationInputBuilder inputBuilder = new NetworkTopologyOperationInputBuilder(input);
		GenericResourceApiUtil.toProperties(parms, inputBuilder.build());

		// Call SLI sync method
		// Get SvcLogicService reference

		GenericResourceApiSvcLogicServiceClient svcLogicClient = new GenericResourceApiSvcLogicServiceClient();
		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";
		String networkId = "error";
        String serviceObjectPath = null;
        String networkObjectPath = null;

		try
		{
			if (svcLogicClient.hasGraph(appName, SVC_OPERATION , null, "sync"))
			{

				try
				{
					respProps = svcLogicClient.execute(appName, SVC_OPERATION, null, "sync", serviceDataBuilder, parms);
				}
				catch (Exception e)
				{
					log.error("Caught exception executing service logic for {}", SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = "No service logic active for "+ appName +": '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		}
		catch (Exception e)
		{
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error("Caught exception looking for service logic", e);
		}


		if (respProps != null)
		{
			errorCode = respProps.getProperty("error-code");
			errorMessage = respProps.getProperty("error-message");
			ackFinal = respProps.getProperty("ack-final", "Y");
			networkId = respProps.getProperty("networkId");
			serviceObjectPath = respProps.getProperty("service-object-path");
			networkObjectPath = respProps.getProperty("network-object-path");
		}

		if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {
			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);

			log.error("Returned FAILED for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());

			RpcResult<NetworkTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<NetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {

			serviceData = serviceDataBuilder.build();
			log.info("Updating MD-SAL for {} [{}] ServiceData: {}", SVC_OPERATION, siid, serviceData);

			// service object
			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceData(serviceData);
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			saveService(serviceBuilder.build(), false, CONFIGURATION);

			if (input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null)
			{
				// Only update operational tree on Activate
				if (input.getSdncRequestHeader().getSvcAction().equals(Activate))
                {
					log.info("Updating OPERATIONAL tree.");
					saveService(serviceBuilder.build(), false, OPERATIONAL);
				}
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
			log.error("Caught Exception updating MD-SAL for {} [{}] \n", SVC_OPERATION, siid, e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error("Returned FAILED for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());
			RpcResult<NetworkTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<NetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null)
		{
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info("Updated MD-SAL for {} [{}]", SVC_OPERATION, siid);
		log.info("Returned SUCCESS for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());

		RpcResult<NetworkTopologyOperationOutput> rpcResult =
            RpcResultBuilder.<NetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<ContrailRouteTopologyOperationOutput>> contrailRouteTopologyOperation(
			ContrailRouteTopologyOperationInput input) {

		final String SVC_OPERATION = "contrail-route-topology-operation";
		ServiceData serviceData;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info("{} called.", SVC_OPERATION);
		// create a new response object
		ContrailRouteTopologyOperationOutputBuilder responseBuilder = new ContrailRouteTopologyOperationOutputBuilder();

		if(input == null ||
            input.getServiceInformation() == null ||
                input.getServiceInformation().getServiceInstanceId() == null ||
                    input.getServiceInformation().getServiceInstanceId().length() == 0)
        {
			log.debug("exiting {} because of null or empty service-instance-id", SVC_OPERATION);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage("invalid input, null or empty service-instance-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<ContrailRouteTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<ContrailRouteTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

        String siid = input.getServiceInformation().getServiceInstanceId();

        // Get the service-instance service data from MD-SAL
        ServiceDataBuilder serviceDataBuilder = new ServiceDataBuilder();
        getServiceData(siid,serviceDataBuilder);

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

        ServiceData sd = serviceDataBuilder.build();
        if (sd == null || sd.getServiceLevelOperStatus() == null)
        {
			log.debug("exiting {} because the service-instance does not have any service data in SDNC", SVC_OPERATION);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage("invalid input: the service-instance does not have any service data in SDNC");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<ContrailRouteTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<ContrailRouteTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		log.info("Adding INPUT data for "+SVC_OPERATION+" ["+siid+"] input: " + input);
		ContrailRouteTopologyOperationInputBuilder inputBuilder = new ContrailRouteTopologyOperationInputBuilder(input);
		GenericResourceApiUtil.toProperties(parms, inputBuilder.build());

		// Call SLI sync method
		// Get SvcLogicService reference

		GenericResourceApiSvcLogicServiceClient svcLogicClient = new GenericResourceApiSvcLogicServiceClient();
		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";
		String allottedResourceId = "error";
        String serviceObjectPath = null;
        String contrailRouteObjectPath = null;

		try
		{
			if (svcLogicClient.hasGraph(appName, SVC_OPERATION , null, "sync"))
			{

				try
				{
					respProps = svcLogicClient.execute(appName, SVC_OPERATION, null, "sync", serviceDataBuilder, parms);
				}
				catch (Exception e)
				{
					log.error("Caught exception executing service logic for {}", SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = "No service logic active for "+ appName +": '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		}
		catch (Exception e)
		{
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error("Caught exception looking for service logic", e);
		}


		if (respProps != null)
		{
			errorCode = respProps.getProperty("error-code");
			errorMessage = respProps.getProperty("error-message");
			ackFinal = respProps.getProperty("ack-final", "Y");
			allottedResourceId = respProps.getProperty("allotted-resource-id");
			serviceObjectPath = respProps.getProperty("service-object-path");
			contrailRouteObjectPath = respProps.getProperty("contrail-route-object-path");
		}

		if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {
			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);

			log.error("Returned FAILED for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());

			RpcResult<ContrailRouteTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<ContrailRouteTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {

			serviceData = serviceDataBuilder.build();
			log.info("Updating MD-SAL for {} [{}] ServiceData: {}", SVC_OPERATION, siid, serviceData);

			// service object
			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceData(serviceData);
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			saveService(serviceBuilder.build(), false, CONFIGURATION);

			if (input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null)
			{
				// Only update operational tree on activate or delete
				if (input.getSdncRequestHeader().getSvcAction().equals(Unassign) ||
						input.getSdncRequestHeader().getSvcAction().equals(Activate))
				{
					log.info("Updating OPERATIONAL tree.");
					saveService(serviceBuilder.build(), false, OPERATIONAL);
				}
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
			log.error("Caught Exception updating MD-SAL for {} [{}] \n", SVC_OPERATION, siid, e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error("Returned FAILED for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());
			RpcResult<ContrailRouteTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<ContrailRouteTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null)
		{
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info("Updated MD-SAL for {} [{}]", SVC_OPERATION, siid);
		log.info("Returned SUCCESS for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());

		RpcResult<ContrailRouteTopologyOperationOutput> rpcResult =
            RpcResultBuilder.<ContrailRouteTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}

	@Override
	public Future<RpcResult<SecurityZoneTopologyOperationOutput>> securityZoneTopologyOperation(
			SecurityZoneTopologyOperationInput input) {

		final String SVC_OPERATION = "security-zone-topology-operation";
		ServiceData serviceData;
		ServiceStatusBuilder serviceStatusBuilder = new ServiceStatusBuilder();
		Properties parms = new Properties();

		log.info("{} called.", SVC_OPERATION);
		// create a new response object
		SecurityZoneTopologyOperationOutputBuilder responseBuilder = new SecurityZoneTopologyOperationOutputBuilder();

		if(input == null ||
            input.getServiceInformation() == null ||
                input.getServiceInformation().getServiceInstanceId() == null ||
                    input.getServiceInformation().getServiceInstanceId().length() == 0)
        {
			log.debug("exiting {} because of null or empty service-instance-id", SVC_OPERATION);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage("invalid input, null or empty service-instance-id");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<SecurityZoneTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<SecurityZoneTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

        String siid = input.getServiceInformation().getServiceInstanceId();

        // Get the service-instance service data from MD-SAL
        ServiceDataBuilder serviceDataBuilder = new ServiceDataBuilder();
        getServiceData(siid,serviceDataBuilder);

		if (input.getSdncRequestHeader() != null) {
			responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
		}

        ServiceData sd = serviceDataBuilder.build();
        if (sd == null || sd.getServiceLevelOperStatus() == null)
        {
			log.debug("exiting {} because the service-instance does not have any service data in SDNC", SVC_OPERATION);
			responseBuilder.setResponseCode("404");
			responseBuilder.setResponseMessage("invalid input: the service-instance does not have any service data in SDNC");
			responseBuilder.setAckFinalIndicator("Y");
			RpcResult<SecurityZoneTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<SecurityZoneTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		log.info("Adding INPUT data for {} [{}] input: {}", SVC_OPERATION, siid, input);
		SecurityZoneTopologyOperationInputBuilder inputBuilder = new SecurityZoneTopologyOperationInputBuilder(input);
		GenericResourceApiUtil.toProperties(parms, inputBuilder.build());

		// Call SLI sync method
		// Get SvcLogicService reference

		GenericResourceApiSvcLogicServiceClient svcLogicClient = new GenericResourceApiSvcLogicServiceClient();
		Properties respProps = null;

		String errorCode = "200";
		String errorMessage = null;
		String ackFinal = "Y";
		String allottedResourceId = "error";
        String serviceObjectPath = null;
        String securityZoneObjectPath = null;

		try
		{
			if (svcLogicClient.hasGraph(appName, SVC_OPERATION , null, "sync"))
			{

				try
				{
					respProps = svcLogicClient.execute(appName, SVC_OPERATION, null, "sync", serviceDataBuilder, parms);
				}
				catch (Exception e)
				{
					log.error("Caught exception executing service logic for {}", SVC_OPERATION, e);
					errorMessage = e.getMessage();
					errorCode = "500";
				}
			} else {
				errorMessage = "No service logic active for "+ appName +": '" + SVC_OPERATION + "'";
				errorCode = "503";
			}
		}
		catch (Exception e)
		{
			errorCode = "500";
			errorMessage = e.getMessage();
			log.error("Caught exception looking for service logic", e);
		}


		if (respProps != null)
		{
			errorCode = respProps.getProperty("error-code");
			errorMessage = respProps.getProperty("error-message");
			ackFinal = respProps.getProperty("ack-final", "Y");
			allottedResourceId = respProps.getProperty("allotted-resource-id");
			serviceObjectPath = respProps.getProperty("service-object-path");
			securityZoneObjectPath = respProps.getProperty("security-zone-object-path");
		}

		if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {
			responseBuilder.setResponseCode(errorCode);
			responseBuilder.setResponseMessage(errorMessage);
			responseBuilder.setAckFinalIndicator(ackFinal);

			log.error("Returned FAILED for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());

			RpcResult<SecurityZoneTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<SecurityZoneTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Got success from SLI
		try {

			serviceData = serviceDataBuilder.build();
			log.info("Updating MD-SAL for {} [{}] ServiceData: {}", SVC_OPERATION, siid, serviceData);

			// service object
			ServiceBuilder serviceBuilder = new ServiceBuilder();
			serviceBuilder.setServiceData(serviceData);
			serviceBuilder.setServiceInstanceId(siid);
			serviceBuilder.setServiceStatus(serviceStatusBuilder.build());
			saveService(serviceBuilder.build(), false, CONFIGURATION);

			if (input.getSdncRequestHeader() != null && input.getSdncRequestHeader().getSvcAction() != null)
			{
				// Only update operational tree on activate or delete
				if (input.getSdncRequestHeader().getSvcAction().equals(Unassign) ||
						input.getSdncRequestHeader().getSvcAction().equals(Activate))
				{
					log.info("Updating OPERATIONAL tree.");
					saveService(serviceBuilder.build(), false, OPERATIONAL);
				}
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
			log.error("Caught Exception updating MD-SAL for {} [{}] \n", SVC_OPERATION, siid, e);
			responseBuilder.setResponseCode("500");
			responseBuilder.setResponseMessage(e.toString());
			responseBuilder.setAckFinalIndicator("Y");
			log.error("Returned FAILED for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());
			RpcResult<SecurityZoneTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<SecurityZoneTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
			return Futures.immediateFuture(rpcResult);
		}

		// Update succeeded
		responseBuilder.setResponseCode(errorCode);
		responseBuilder.setAckFinalIndicator(ackFinal);
		if (errorMessage != null)
		{
			responseBuilder.setResponseMessage(errorMessage);
		}
		log.info("Updated MD-SAL for {} [{}]", SVC_OPERATION, siid);
		log.info("Returned SUCCESS for {} [{}] {}", SVC_OPERATION, siid, responseBuilder.build());

		RpcResult<SecurityZoneTopologyOperationOutput> rpcResult =
            RpcResultBuilder.<SecurityZoneTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
		return Futures.immediateFuture(rpcResult);
	}

    @Override
    public Future<RpcResult<PreloadVnfTopologyOperationOutput>> preloadVnfTopologyOperation(
            PreloadVnfTopologyOperationInput input) {

        final String SVC_OPERATION = "preload-vnf-topology-operation";
        PreloadData preloadData;
        Properties parms = new Properties();

        log.info("{} called.", SVC_OPERATION);
        // create a new response object
        PreloadVnfTopologyOperationOutputBuilder responseBuilder = new PreloadVnfTopologyOperationOutputBuilder();

        // Result from savePreloadData
        final SettableFuture<RpcResult<Void>> futureResult = SettableFuture.create();

        if(input == null || input.getVnfTopologyInformation() == null ||
            input.getVnfTopologyInformation().getVnfTopologyIdentifier() == null) {
                log.debug("exiting {} because of null input", SVC_OPERATION);
                responseBuilder.setResponseCode("403");
                responseBuilder.setResponseMessage("invalid input: input is null");
                responseBuilder.setAckFinalIndicator("Y");
                RpcResult<PreloadVnfTopologyOperationOutput> rpcResult =
                    RpcResultBuilder.<PreloadVnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
                return Futures.immediateFuture(rpcResult);
        }

        // Grab the name and type from the input buffer
        String preload_name = input.getVnfTopologyInformation().getVnfTopologyIdentifier().getVnfName();
        String preload_type = input.getVnfTopologyInformation().getVnfTopologyIdentifier().getVnfType();

        // Make sure we have a preload_name and preload_type
        if(preload_name == null || preload_name.length() == 0 || preload_type == null || preload_type.length() == 0 ) {
            log.debug("exiting {} vnf-name or vnf-type is null or empty", SVC_OPERATION);
            responseBuilder.setResponseCode("403");
            responseBuilder.setResponseMessage("invalid input: vnf-name or vnf-type is null or empty");
            responseBuilder.setAckFinalIndicator("Y");
            RpcResult<PreloadVnfTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<PreloadVnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
            return Futures.immediateFuture(rpcResult);
        }

        if (input.getSdncRequestHeader() != null) {
            responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
        }

        PreloadDataBuilder preloadDataBuilder = new PreloadDataBuilder();
        getPreloadData(preload_name, preload_type, preloadDataBuilder);

        PreloadDataBuilder operDataBuilder = new PreloadDataBuilder();
        getPreloadData(preload_name, preload_type, operDataBuilder, OPERATIONAL );

        // setup a preload-data object builder
        // ACTION preload-vnf-topology-operation
        // INPUT:
        //  USES sdnc-request-header;
        //  USES request-information;
        //  uses vnf-topology-information;
        // OUTPUT:
        //  USES vnf-topology-response-body;
        //
        // container preload-data
        //   uses vnf-topology-information;
        //   uses network-topology-information;
        //   uses oper-status;

        log.info("Adding INPUT data for {} [{},{}] input: {}", SVC_OPERATION, preload_name, preload_type, input);
        PreloadVnfTopologyOperationInputBuilder inputBuilder = new PreloadVnfTopologyOperationInputBuilder(input);
        GenericResourceApiUtil.toProperties(parms, inputBuilder.build());
        log.info("Adding OPERATIONAL data for {} [{},{}] operational-data: {}",
            SVC_OPERATION, preload_name, preload_type, operDataBuilder.build());
        GenericResourceApiUtil.toProperties(parms, "operational-data", operDataBuilder);

        // Call SLI sync method
        // Get SvcLogicService reference

        GenericResourceApiSvcLogicServiceClient svcLogicClient = new GenericResourceApiSvcLogicServiceClient();
        Properties respProps = null;

        String errorCode = "200";
        String errorMessage = null;
        String ackFinal = "Y";


        try
        {
            if (svcLogicClient.hasGraph(appName, SVC_OPERATION , null, "sync"))
            {

                try
                {
                    respProps = svcLogicClient.execute(appName, SVC_OPERATION, null, "sync", preloadDataBuilder, parms);
                }
                catch (Exception e)
                {
                    log.error("Caught exception executing service logic for {}", SVC_OPERATION, e);
                    errorMessage = e.getMessage();
                    errorCode = "500";
                }
            } else {
                errorMessage = "No service logic active for "+ appName + ": '" + SVC_OPERATION + "'";
                errorCode = "503";
            }
        }
        catch (Exception e)
        {
            errorCode = "500";
            errorMessage = e.getMessage();
            log.error("Caught exception looking for service logic", e);
        }


        if (respProps != null)
        {
            errorCode = respProps.getProperty("error-code");
            errorMessage = respProps.getProperty("error-message");
            ackFinal = respProps.getProperty("ack-final", "Y");
            // internalError = respProps.getProperty("internal-error", "false");
        }

        if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {

            responseBuilder.setResponseCode(errorCode);
            responseBuilder.setResponseMessage(errorMessage);
            responseBuilder.setAckFinalIndicator(ackFinal);

            VnfPreloadListBuilder preloadVnfListBuilder = new VnfPreloadListBuilder();
            preloadVnfListBuilder.setVnfName(preload_name);
            preloadVnfListBuilder.setVnfType(preload_type);
            preloadVnfListBuilder.setPreloadData(preloadDataBuilder.build());
            log.error("Returned FAILED for {} [{},{}] error code: '{}', Reason: '{}'",
                SVC_OPERATION, preload_name, preload_type, errorCode, errorMessage);
            try {
                savePreloadList(preloadVnfListBuilder.build(), true, CONFIGURATION);
            } catch (Exception e) {
                log.error("Caught Exception updating MD-SAL for {} [{},{}] \n",
                    SVC_OPERATION, preload_name, preload_type, e);
            }
            log.debug("Sending Success rpc result due to external error");
            RpcResult<PreloadVnfTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<PreloadVnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
            return Futures.immediateFuture(rpcResult);
        }

        // Got success from SLI
        try {
            preloadData = preloadDataBuilder.build();
            log.info("Updating MD-SAL for {} [{},{}] preloadData: {}",
                SVC_OPERATION, preload_name, preload_type, preloadData);
            // svc-configuration-list
            VnfPreloadListBuilder preloadVnfListBuilder = new VnfPreloadListBuilder();
            preloadVnfListBuilder.setVnfName(preload_name);
            preloadVnfListBuilder.setVnfType(preload_type);
            preloadVnfListBuilder.setPreloadData(preloadData);

            // merge flag sets to false to allow it to be overwritten (not appended)
            savePreloadList(preloadVnfListBuilder.build(), false, CONFIGURATION);
            log.info("Updating OPERATIONAL tree.");
            savePreloadList(preloadVnfListBuilder.build(), false, OPERATIONAL);
        } catch (Exception e) {
            log.error("Caught Exception updating MD-SAL for {} [{},{}] \n",
                SVC_OPERATION, preload_name, preload_type, e);
            responseBuilder.setResponseCode("500");
            responseBuilder.setResponseMessage(e.toString());
            responseBuilder.setAckFinalIndicator("Y");
            log.error("Returned FAILED for {} [{},{}] {}",
                SVC_OPERATION, preload_name, preload_type, responseBuilder.build());
            RpcResult<PreloadVnfTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<PreloadVnfTopologyOperationOutput> status(false).withResult(responseBuilder.build()).build();
            return Futures.immediateFuture(rpcResult);
        }

        // Update succeeded
        responseBuilder.setResponseCode(errorCode);
        responseBuilder.setAckFinalIndicator(ackFinal);
        if (errorMessage != null)
        {
            responseBuilder.setResponseMessage(errorMessage);
        }
        log.info("Updated MD-SAL for {} [{},{}]", SVC_OPERATION, preload_name, preload_type);
        log.info("Returned SUCCESS for {} [{},{}] {}", SVC_OPERATION, preload_name, preload_type, responseBuilder.build());

        RpcResult<PreloadVnfTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<PreloadVnfTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
        return Futures.immediateFuture(rpcResult);
    }


    @Override
    public Future<RpcResult<PreloadNetworkTopologyOperationOutput>> preloadNetworkTopologyOperation(
            PreloadNetworkTopologyOperationInput input) {

        final String SVC_OPERATION = "preload-network-topology-operation";
        PreloadData preloadData;
        Properties parms = new Properties();

        log.info("{} called.", SVC_OPERATION);
        // create a new response object
        PreloadNetworkTopologyOperationOutputBuilder responseBuilder = new PreloadNetworkTopologyOperationOutputBuilder();

        // Result from savePreloadData
        final SettableFuture<RpcResult<Void>> futureResult = SettableFuture.create();

        if(input == null || input.getNetworkTopologyInformation() == null ||
            input.getNetworkTopologyInformation().getNetworkTopologyIdentifier() == null) {

            log.debug("exiting {} because of null input", SVC_OPERATION);
            responseBuilder.setResponseCode("403");
            responseBuilder.setResponseMessage("input is null");
            responseBuilder.setAckFinalIndicator("Y");
            RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<PreloadNetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
            return Futures.immediateFuture(rpcResult);
        }

        // Grab the name and type from the input buffer
        String preload_name = input.getNetworkTopologyInformation().getNetworkTopologyIdentifier().getNetworkName();
        String preload_type = input.getNetworkTopologyInformation().getNetworkTopologyIdentifier().getNetworkType();

        // Make sure we have a preload_name and preload_type
        if(preload_name == null || preload_name.length() == 0 || preload_type == null || preload_type.length() == 0 ) {
            log.debug("exiting {} because of invalid preload-name", SVC_OPERATION);
            responseBuilder.setResponseCode("403");
            responseBuilder.setResponseMessage("input, invalid preload-name");
            responseBuilder.setAckFinalIndicator("Y");
            RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<PreloadNetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
            return Futures.immediateFuture(rpcResult);
        }

        if (input.getSdncRequestHeader() != null) {
            responseBuilder.setSvcRequestId(input.getSdncRequestHeader().getSvcRequestId());
        }

        PreloadDataBuilder preloadDataBuilder = new PreloadDataBuilder();
        getPreloadData(preload_name, preload_type, preloadDataBuilder);

        PreloadDataBuilder operDataBuilder = new PreloadDataBuilder();
        getPreloadData(preload_name, preload_type, operDataBuilder, OPERATIONAL );

        //
        // setup a preload-data object builder
        // ACTION preload-network-topology-operation
        // INPUT:
        //  USES sdnc-request-header;
        //  USES request-information;
        //  uses network-topology-information;
        // OUTPUT:
        //  USES vnf-topology-response-body;
        //
        // container preload-data
        //   uses vnf-topology-information;
        //   uses network-topology-information;
        //   uses oper-status;


        log.info("Adding INPUT data for {} [{},{}] input: {}", SVC_OPERATION, preload_name, preload_type, input);
        PreloadNetworkTopologyOperationInputBuilder inputBuilder = new PreloadNetworkTopologyOperationInputBuilder(input);
        GenericResourceApiUtil.toProperties(parms, inputBuilder.build());
        log.info("Adding OPERATIONAL data for {} [{},{}] operational-data: {}",
            SVC_OPERATION, preload_name, preload_type, operDataBuilder.build());
        GenericResourceApiUtil.toProperties(parms, "operational-data", operDataBuilder);

        // Call SLI sync method
        // Get SvcLogicService reference

        GenericResourceApiSvcLogicServiceClient svcLogicClient = new GenericResourceApiSvcLogicServiceClient();
        Properties respProps = null;

        String errorCode = "200";
        String errorMessage = null;
        String ackFinal = "Y";


        try
        {
            if (svcLogicClient.hasGraph(appName, SVC_OPERATION , null, "sync"))
            {

                try
                {
                    respProps = svcLogicClient.execute(appName, SVC_OPERATION, null, "sync", preloadDataBuilder, parms);
                }
                catch (Exception e)
                {
                    log.error("Caught exception executing service logic for {}", SVC_OPERATION, e);
                    errorMessage = e.getMessage();
                    errorCode = "500";
                }
            } else {
                errorMessage = "No service logic active for "+ appName +": '" + SVC_OPERATION + "'";
                errorCode = "503";
            }
        }
        catch (Exception e)
        {
            errorCode = "500";
            errorMessage = e.getMessage();
            log.error("Caught exception looking for service logic", e);
        }


        if (respProps != null)
        {
            errorCode = respProps.getProperty("error-code");
            errorMessage = respProps.getProperty("error-message");
            ackFinal = respProps.getProperty("ack-final", "Y");
            // internalError = respProps.getProperty("internal-error", "false");
        }

        if ( errorCode != null && errorCode.length() != 0 && !( errorCode.equals("0")|| errorCode.equals("200"))) {

            responseBuilder.setResponseCode(errorCode);
            responseBuilder.setResponseMessage(errorMessage);
            responseBuilder.setAckFinalIndicator(ackFinal);

            VnfPreloadListBuilder preloadVnfListBuilder = new VnfPreloadListBuilder();
            preloadVnfListBuilder.setVnfName(preload_name);
            preloadVnfListBuilder.setVnfType(preload_type);
            preloadVnfListBuilder.setPreloadData(preloadDataBuilder.build());
            log.error("Returned FAILED for {} [{},{}] error code: '{}', Reason: '{}'",
                SVC_OPERATION, preload_name, preload_type, errorCode, errorMessage);
            try {
                savePreloadList(preloadVnfListBuilder.build(),true, CONFIGURATION);
            } catch (Exception e) {
                log.error("Caught Exception updating MD-SAL for {} [{},{}] \n",
                    SVC_OPERATION, preload_name, preload_type, e);

            }
            log.debug("Sending Success rpc result due to external error");
            RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<PreloadNetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
            return Futures.immediateFuture(rpcResult);
        }

        // Got success from SLI
        try {
            preloadData = preloadDataBuilder.build();
            log.info("Updating MD-SAL for {} [{},{}] preloadData: {}",
                SVC_OPERATION, preload_name, preload_type, preloadData);
            // svc-configuration-list
            VnfPreloadListBuilder preloadVnfListBuilder = new VnfPreloadListBuilder();
            preloadVnfListBuilder.setVnfName(preload_name);
            preloadVnfListBuilder.setVnfType(preload_type);
            preloadVnfListBuilder.setPreloadData(preloadData);

            // merge flag sets to false to allow it to be overwritten (not appended)
            savePreloadList(preloadVnfListBuilder.build(), false, CONFIGURATION);
            log.info("Updating OPERATIONAL tree.");
            savePreloadList(preloadVnfListBuilder.build(), false, OPERATIONAL);
        } catch (Exception e) {
            log.error("Caught Exception updating MD-SAL for "+SVC_OPERATION+" ["+preload_name+","+preload_type+"] \n",e);
            responseBuilder.setResponseCode("500");
            responseBuilder.setResponseMessage(e.toString());
            responseBuilder.setAckFinalIndicator("Y");
            log.error("Returned FAILED for {} [{},{}] {}",
                SVC_OPERATION, preload_name, preload_type, responseBuilder.build());
            RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<PreloadNetworkTopologyOperationOutput> status(false).withResult(responseBuilder.build()).build();
            return Futures.immediateFuture(rpcResult);
        }

        // Update succeeded
        responseBuilder.setResponseCode(errorCode);
        responseBuilder.setAckFinalIndicator(ackFinal);
        if (errorMessage != null)
        {
            responseBuilder.setResponseMessage(errorMessage);
        }
        log.info("Updated MD-SAL for {} [{},{}]", SVC_OPERATION, preload_name, preload_type);
        log.info("Returned SUCCESS for {} [{},{}] {}",
            SVC_OPERATION, preload_name, preload_type, responseBuilder.build());

        RpcResult<PreloadNetworkTopologyOperationOutput> rpcResult =
                RpcResultBuilder.<PreloadNetworkTopologyOperationOutput> status(true).withResult(responseBuilder.build()).build();
        return Futures.immediateFuture(rpcResult);
    }
}
