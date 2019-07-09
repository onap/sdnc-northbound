/*
 * ============LICENSE_START==========================================
 * Copyright (c) 2019 PANTHEON.tech s.r.o.
 * ===================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END============================================
 *
 */
package org.onap.sdnc.vnfapi.lighty;

import io.lighty.core.controller.api.AbstractLightyModule;
import org.onap.ccsdk.sli.core.sli.provider.SvcLogicService;
import org.onap.sdnc.vnfapi.VNFSDNSvcLogicServiceClient;
import org.onap.sdnc.vnfapi.VnfApiProvider;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;

/**
 * The implementation of the {@link io.lighty.core.controller.api.LightyModule} that manages and provides services from
 * the vnfapi-provider artifact.
 */
public class VnfapiModule extends AbstractLightyModule {

    private final SvcLogicService svcLogic;
    private final DataBroker dataBroker;
    private final NotificationPublishService notificationPublishService;
    private final RpcProviderRegistry rpcProviderRegistry;

    private VNFSDNSvcLogicServiceClient vnfsdnSvcLogicServiceClient;
    private VnfApiProvider vnfApiProvider;

    public VnfapiModule(SvcLogicService svcLogic, DataBroker dataBroker,
            NotificationPublishService notificationPublishService, RpcProviderRegistry rpcProviderRegistry) {
        this.svcLogic = svcLogic;
        this.dataBroker = dataBroker;
        this.notificationPublishService = notificationPublishService;
        this.rpcProviderRegistry = rpcProviderRegistry;
    }

    @Override
    protected boolean initProcedure() {
        vnfsdnSvcLogicServiceClient = new VNFSDNSvcLogicServiceClient(svcLogic);
        vnfApiProvider = new VnfApiProvider(dataBroker, notificationPublishService, rpcProviderRegistry,
                vnfsdnSvcLogicServiceClient);
        return true;
    }

    @Override
    protected boolean stopProcedure() {
        return true;
    }

}
