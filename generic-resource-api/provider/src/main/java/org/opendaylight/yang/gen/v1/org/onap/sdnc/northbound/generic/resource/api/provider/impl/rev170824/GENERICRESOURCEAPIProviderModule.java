package org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.api.provider.impl.rev170824;

import org.onap.sdnc.northbound.GenericResourceApiProvider;
import org.opendaylight.controller.config.api.DependencyResolver;
import org.opendaylight.controller.config.api.ModuleIdentifier;

public class GENERICRESOURCEAPIProviderModule extends AbstractGENERICRESOURCEAPIProviderModule {
    public GENERICRESOURCEAPIProviderModule(ModuleIdentifier identifier, DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public GENERICRESOURCEAPIProviderModule(ModuleIdentifier identifier, DependencyResolver dependencyResolver, GENERICRESOURCEAPIProviderModule oldModule, AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public AutoCloseable createInstance() {
        final GenericResourceApiProvider provider = new GenericResourceApiProvider(getDataBrokerDependency()
                , getNotificationPublishAdapterDependency()
                , getRpcRegistryDependency());

        return new AutoCloseable() {

           @Override
           public void close() throws Exception {
               provider.close();
           }
       };
    }


}
