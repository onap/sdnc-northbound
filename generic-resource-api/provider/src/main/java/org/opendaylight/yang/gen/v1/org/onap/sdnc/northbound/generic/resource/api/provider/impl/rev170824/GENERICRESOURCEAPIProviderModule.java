package org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.api.provider.impl.rev170824;

import org.onap.sdnc.northbound.GenericResourceApiProvider;

public class GENERICRESOURCEAPIProviderModule extends org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.api.provider.impl.rev170824.AbstractGENERICRESOURCEAPIProviderModule {
    public GENERICRESOURCEAPIProviderModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public GENERICRESOURCEAPIProviderModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.api.provider.impl.rev170824.GENERICRESOURCEAPIProviderModule oldModule, java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
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
