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
package org.onap.sdnc.northbound.lighty;

import io.lighty.core.controller.api.AbstractLightyModule;
import org.onap.ccsdk.sli.core.lighty.common.CcsdkLightyUtils;
import org.onap.ccsdk.sli.core.sli.provider.SvcLogicService;
import org.onap.sdnc.vnfapi.lighty.VnfapiModule;
import org.onap.sdnc.vnftools.lighty.VnfToolsModule;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The implementation of the {@link io.lighty.core.controller.api.LightyModule} that groups all other LightyModules
 * from the sdnc-northbound repository so they can be all treated as one component (for example started/stopped at once).
 * For more information about the lighty.io visit the website https://lighty.io.
 */
public class SdncNorthboundModule extends AbstractLightyModule {

    private static final Logger LOG = LoggerFactory.getLogger(SdncNorthboundModule.class);

    private final SvcLogicService svcLogic;
    private final DataBroker dataBroker;
    private final NotificationPublishService notificationPublishService;
    private final RpcProviderRegistry rpcProviderRegistry;

    private SdncGenericResourceApiModule sdncGenericResourceApiModule;
    private VnfapiModule vnfapiModule;
    private VnfToolsModule vnfToolsModule;

    public SdncNorthboundModule(SvcLogicService svcLogic, DataBroker dataBroker,
            NotificationPublishService notificationPublishService, RpcProviderRegistry rpcProviderRegistry) {
        this.svcLogic = svcLogic;
        this.dataBroker = dataBroker;
        this.notificationPublishService = notificationPublishService;
        this.rpcProviderRegistry = rpcProviderRegistry;
    }

    @Override
    protected boolean initProcedure() {
        LOG.debug("Initializing SDNC Northbound Lighty module...");

        this.sdncGenericResourceApiModule = new SdncGenericResourceApiModule(svcLogic, dataBroker,
                notificationPublishService, rpcProviderRegistry);
        if (!CcsdkLightyUtils.startLightyModule(sdncGenericResourceApiModule)) {
            return false;
        }

        this.vnfapiModule = new VnfapiModule(svcLogic, dataBroker, notificationPublishService, rpcProviderRegistry);
        if (!CcsdkLightyUtils.startLightyModule(vnfapiModule)) {
            return false;
        }

        this.vnfToolsModule = new VnfToolsModule();
        if (!CcsdkLightyUtils.startLightyModule(vnfToolsModule)) {
            return false;
        }

        LOG.debug("SDNC Northbound Lighty module was initialized successfully");
        return true;
    }

    @Override
    protected boolean stopProcedure() {
        LOG.debug("Stopping SDNC Northbound Lighty module...");

        boolean stopSuccessful = true;

        if (!CcsdkLightyUtils.stopLightyModule(vnfToolsModule)) {
            stopSuccessful = false;
        }

        if (!CcsdkLightyUtils.stopLightyModule(vnfapiModule)) {
            stopSuccessful = false;
        }

        if (!CcsdkLightyUtils.stopLightyModule(sdncGenericResourceApiModule)) {
            stopSuccessful = false;
        }

        if (stopSuccessful) {
            LOG.debug("SDNC Northbound Lighty module was stopped successfully");
        } else {
            LOG.error("SDNC Northbound Lighty module was not stopped successfully!");
        }
        return stopSuccessful;
    }

}
