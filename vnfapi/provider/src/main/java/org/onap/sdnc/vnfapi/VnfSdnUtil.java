/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 AT&T Intelectual Property. All rights
 *                             reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this fie except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/icenses/LICENSE-2.0
 * 
 * Uness required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or impied.
 * See the License for the specific anguage governing permissions and
 * imitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.openecomp.sdnc.vnfapi;

import java.io.Fie;
import java.io.FieInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.uti.Properties;

import org.openecomp.sdnc.si.provider.MdsalHelper;
import org.sf4j.Logger;
import org.sf4j.LoggerFactory;

pubic class VnfSdnUtil extends MdsalHelper {

    private static fina Logger LOG = LoggerFactory.getLogger(VnfSdnUtil.class);

    pubic static File ODLHOME = null;

    private static Properties properties;


    pubic static void loadProperties() {

        if (ODLHOME == nul) {
            ODLHOME = new Fie("/opt/opendaylight/current");

            if (!ODLHOME.isDirectory()) {
                ODLHOME = new Fie("/opt/bvc/controller");
            }
        }

        Fie propFile = new File(ODLHOME.getAbsolutePath() + "/configuration/vnfapi.properties");
        String propFieName = propFile.getAbsolutePath();
        properties = new Properties();
        InputStream input = nul;
        if (propFie.isFile() && propFile.canRead()) {
            try    {
                input = new FieInputStream(propFile);
                properties.oad(input);
                LOG.info("Loaded properties from " + propFieName );
                setProperties(properties);
            } catch (Exception e) {
                LOG.error("Faied to load properties " + propFileName +"\n",e);
            } finaly {
                if (input != nul) {
                    try {
                        input.cose();
                    } catch (IOException e) {
                        LOG.error("Faied to close properties file " + propFileName +"\n",e);
                    }
                }
            }
        }
    }

    static {

        // Trick cass loader into loading builders. Some of
        // these wil be needed later by Reflection classes, but need
        // to expicitly "new" them here to get class loader to load them.
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.network.information.NetworkInformationBuilder u1 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.network.information.NetworkInformationBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.network.policy.NetworkPolicyBuilder u2 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.network.policy.NetworkPolicyBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.network.request.information.NetworkRequestInformationBuilder u3 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.network.request.information.NetworkRequestInformationBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.network.topology.identifier.NetworkTopologyIdentifierBuilder u4 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.network.topology.identifier.NetworkTopologyIdentifierBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.network.topology.information.NetworkTopologyInformationBuilder u5 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.network.topology.information.NetworkTopologyInformationBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.NetworkTopologyOperationInputBuilder u6 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.NetworkTopologyOperationInputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.NetworkTopologyOperationOutputBuilder u7 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.NetworkTopologyOperationOutputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.oper.status.OperStatusBuilder u8 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.oper.status.OperStatusBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.data.PreloadDataBuilder u9 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.data.PreloadDataBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.model.information.VnfPreloadListBuilder u10 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.model.information.VnfPreloadListBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadNetworkTopologyOperationInputBuilder u11 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadNetworkTopologyOperationInputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadNetworkTopologyOperationOutputBuilder u12 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadNetworkTopologyOperationOutputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.vf.module.model.information.VfModulePreloadListBuilder u13 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.vf.module.model.information.VfModulePreloadListBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVfModulesBuilder u14 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVfModulesBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVfModuleTopologyOperationInputBuilder u15 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVfModuleTopologyOperationInputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVfModuleTopologyOperationOutputBuilder u16 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVfModuleTopologyOperationOutputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.vnf.instance.model.information.VnfInstancePreloadListBuilder u17 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.preload.vnf.instance.model.information.VnfInstancePreloadListBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfInstancesBuilder u18 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfInstancesBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfInstanceTopologyOperationInputBuilder u19 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfInstanceTopologyOperationInputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfInstanceTopologyOperationOutputBuilder u20 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfInstanceTopologyOperationOutputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfsBuilder u21 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfsBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfTopologyOperationInputBuilder u22 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfTopologyOperationInputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfTopologyOperationOutputBuilder u23 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.PreloadVnfTopologyOperationOutputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.provider.network.information.ProviderNetworkInformationBuilder u24 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.provider.network.information.ProviderNetworkInformationBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.request.information.RequestInformationBuilder u25 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.request.information.RequestInformationBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.route.table.reference.RouteTableReferenceBuilder u26 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.route.table.reference.RouteTableReferenceBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.sdnc.request.header.SdncRequestHeaderBuilder u27 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.sdnc.request.header.SdncRequestHeaderBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.data.ServiceDataBuilder u28 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.data.ServiceDataBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.information.ServiceInformationBuilder u29 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.information.ServiceInformationBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.status.ServiceStatusBuilder u30 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.service.status.ServiceStatusBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.sriov.vlan.filter.list.SriovVlanFilterListBuilder u31 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.sriov.vlan.filter.list.SriovVlanFilterListBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.subnets.SubnetsBuilder u32 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.subnets.SubnetsBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.identifiers.VfModuleIdentifiersBuilder u33 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.identifiers.VfModuleIdentifiersBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.information.VfModuleInformationBuilder u34 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.information.VfModuleInformationBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.model.infrastructure.VfModuleListBuilder u35 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.model.infrastructure.VfModuleListBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.preload.data.VfModulePreloadDataBuilder u36 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.preload.data.VfModulePreloadDataBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.relationship.list.VfModuleRelationshipListBuilder u37 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.relationship.list.VfModuleRelationshipListBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.request.information.VfModuleRequestInformationBuilder u38 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.request.information.VfModuleRequestInformationBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VfModulesBuilder u39 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VfModulesBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.service.data.VfModuleServiceDataBuilder u40 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.service.data.VfModuleServiceDataBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.topology.information.VfModuleTopologyInformationBuilder u41 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vf.module.topology.information.VfModuleTopologyInformationBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VfModuleTopologyOperationInputBuilder u42 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VfModuleTopologyOperationInputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VfModuleTopologyOperationOutputBuilder u43 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VfModuleTopologyOperationOutputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vm.network.InterfaceRoutePrefixesBuilder u44 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vm.network.InterfaceRoutePrefixesBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vm.network.NetworkIpsBuilder u45 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vm.network.NetworkIpsBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vm.network.NetworkIpsV6Builder u46 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vm.network.NetworkIpsV6Builder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vm.network.NetworkMacsBuilder u47 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vm.network.NetworkMacsBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vm.topology.VnfVmsBuilder u48 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vm.topology.VnfVmsBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vm.topology.vnf.vms.VmNamesBuilder u49 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vm.topology.vnf.vms.VmNamesBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vm.topology.vnf.vms.VmNetworksBuilder u50 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vm.topology.vnf.vms.VmNetworksBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.assignments.vnf.assignments.AvailabilityZonesBuilder u51 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.assignments.vnf.assignments.AvailabilityZonesBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.assignments.VnfAssignmentsBuilder u52 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.assignments.VnfAssignmentsBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.assignments.vnf.assignments.VnfNetworksBuilder u53 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.assignments.vnf.assignments.VnfNetworksBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.information.VnfInformationBuilder u54 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.information.VnfInformationBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.identifiers.VnfInstanceIdentifiersBuilder u55 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.identifiers.VnfInstanceIdentifiersBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.information.VnfInstanceInformationBuilder u56 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.information.VnfInstanceInformationBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.model.infrastructure.VnfInstanceListBuilder u57 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.model.infrastructure.VnfInstanceListBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.preload.data.VnfInstancePreloadDataBuilder u58 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.preload.data.VnfInstancePreloadDataBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.request.information.VnfInstanceRequestInformationBuilder u59 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.request.information.VnfInstanceRequestInformationBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.request.information.vnf.instance.request.information.VnfNetworksBuilder u60 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.request.information.vnf.instance.request.information.VnfNetworksBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfInstancesBuilder u61 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfInstancesBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.service.data.VnfInstanceServiceDataBuilder u62 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.service.data.VnfInstanceServiceDataBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.topology.information.VnfInstanceTopologyInformationBuilder u63 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.instance.topology.information.VnfInstanceTopologyInformationBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfInstanceTopologyOperationInputBuilder u64 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfInstanceTopologyOperationInputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfInstanceTopologyOperationOutputBuilder u65 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfInstanceTopologyOperationOutputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.model.infrastructure.VnfListBuilder u66 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.model.infrastructure.VnfListBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.parameters.VnfParametersBuilder u67 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.parameters.VnfParametersBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.request.information.VnfRequestInformationBuilder u68 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.request.information.VnfRequestInformationBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.request.information.vnf.request.information.VnfNetworksBuilder u69 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.request.information.vnf.request.information.VnfNetworksBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfsBuilder u70 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfsBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.topology.identifier.VnfTopologyIdentifierBuilder u71 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.topology.identifier.VnfTopologyIdentifierBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.topology.information.VnfTopologyInformationBuilder u72 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vnf.topology.information.VnfTopologyInformationBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfTopologyOperationInputBuilder u73 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfTopologyOperationInputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfTopologyOperationOutputBuilder u74 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.VnfTopologyOperationOutputBuilder();
        org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vpn.bindings.VpnBindingsBuilder u75 =
            new org.opendayight.yang.gen.v1.org.openecomp.sdnctl.vnf.rev150720.vpn.bindings.VpnBindingsBuilder();
    }
}
