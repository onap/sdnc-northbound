package org.opendaylight.yang.gen.v1.brocade.training.generic.resource.api.provider.impl.rev140523;

import com.att.sdnctl.app.GenericResourceApiProvider;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker.DataChangeScope;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.GENERICRESOURCEAPIService;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GENERICRESOURCEAPIProviderModule extends org.opendaylight.yang.gen.v1.brocade.training.generic.resource.api.provider.impl.rev140523.AbstractGENERICRESOURCEAPIProviderModule {
    public GENERICRESOURCEAPIProviderModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public GENERICRESOURCEAPIProviderModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.yang.gen.v1.brocade.training.generic.resource.api.provider.impl.rev140523.GENERICRESOURCEAPIProviderModule oldModule, java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
        // TODO:implement
/*
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
*/
        //throw new java.lang.UnsupportedOperationException();
      final GenericResourceApiProvider provider = new GenericResourceApiProvider(getDataBrokerDependency()
                , getNotificationServiceDependency()
                , getRpcRegistryDependency());

        return new AutoCloseable() {

           @Override
           public void close() throws Exception {
               provider.close();
           }
       };
    }

}
