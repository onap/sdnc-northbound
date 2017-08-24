package com.att.sdnctl.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openecomp.sdnc.sli.provider.MdsalHelper;
import com.google.common.base.CaseFormat;

public class GenericResourceApiUtil extends MdsalHelper {

	private static final Logger LOG = LoggerFactory.getLogger(GenericResourceApiUtil.class);
	
	public static final String PROPERTIES_FILE="/opt/bvc/controller/configuration/generic-resource-api.properties";
	private static Properties properties;
	

	public static void loadProperties() {

		File file = new File(PROPERTIES_FILE); 
		properties = new Properties();
		InputStream input = null;
		if (file.isFile() && file.canRead()) {
			try	{
				input = new FileInputStream(file);
				properties.load(input);
				LOG.info("Loaded properties from " + PROPERTIES_FILE );
				setProperties(properties);
			} catch (Exception e) {
				LOG.error("Failed to load properties " + PROPERTIES_FILE +"\n",e);
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						LOG.error("Failed to close properties file " + PROPERTIES_FILE +"\n",e);
					}
				}
			}
		}
	}

	static {

		// Trick class loader into loading builders. Some of
		// these will be needed later by Reflection classes, but need
		// to explicitly "new" them here to get class loader to load them.
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.ecomp.model.information.EcompModelInformationBuilder u1 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.ecomp.model.information.EcompModelInformationBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.assignments.NetworkAssignmentsBuilder u2 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.assignments.NetworkAssignmentsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.information.NetworkInformationBuilder u3 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.information.NetworkInformationBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.level.oper.status.NetworkLevelOperStatusBuilder u4 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.level.oper.status.NetworkLevelOperStatusBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.parameters.NetworkParametersBuilder u5 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.parameters.NetworkParametersBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.parameters.network.parameters.NetworkParameterBuilder u6 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.parameters.network.parameters.NetworkParameterBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.policy.NetworkPolicyBuilder u7 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.policy.NetworkPolicyBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.request.input.NetworkRequestInputBuilder u8 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.request.input.NetworkRequestInputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.request.input.network.request.input.NetworkInputParametersBuilder u9 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.request.input.network.request.input.NetworkInputParametersBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.response.information.NetworkResponseInformationBuilder u10 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.response.information.NetworkResponseInformationBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.topology.identifier.NetworkTopologyIdentifierBuilder u11 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.topology.identifier.NetworkTopologyIdentifierBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.topology.identifier.structure.NetworkTopologyIdentifierStructureBuilder u12 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.topology.identifier.structure.NetworkTopologyIdentifierStructureBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.topology.information.NetworkTopologyInformationBuilder u13 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.topology.information.NetworkTopologyInformationBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.topology.NetworkTopologyBuilder u14 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.network.topology.NetworkTopologyBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.NetworkTopologyOperationInputBuilder u15 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.NetworkTopologyOperationInputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.NetworkTopologyOperationOutputBuilder u16 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.NetworkTopologyOperationOutputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.oper.status.OperStatusBuilder u17 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.oper.status.OperStatusBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.param.ParamBuilder u18 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.param.ParamBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.preload.data.PreloadDataBuilder u19 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.preload.data.PreloadDataBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.preload.model.information.VnfPreloadListBuilder u20 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.preload.model.information.VnfPreloadListBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.PreloadNetworkTopologyOperationInputBuilder u21 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.PreloadNetworkTopologyOperationInputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.PreloadNetworkTopologyOperationOutputBuilder u22 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.PreloadNetworkTopologyOperationOutputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.PreloadVnfsBuilder u23 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.PreloadVnfsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.PreloadVnfTopologyOperationInputBuilder u24 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.PreloadVnfTopologyOperationInputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.PreloadVnfTopologyOperationOutputBuilder u25 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.PreloadVnfTopologyOperationOutputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.request.information.RequestInformationBuilder u26 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.request.information.RequestInformationBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.route.table.reference.RouteTableReferenceBuilder u27 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.route.table.reference.RouteTableReferenceBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.sdnc.request.header.SdncRequestHeaderBuilder u28 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.sdnc.request.header.SdncRequestHeaderBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.assignments.ServiceAssignmentsBuilder u29 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.assignments.ServiceAssignmentsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.ServiceDataBuilder u30 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.ServiceDataBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.NetworksBuilder u31 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.NetworksBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.networks.NetworkBuilder u32 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.networks.NetworkBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.networks.network.NetworkDataBuilder u33 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.networks.network.NetworkDataBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.VnfsBuilder u34 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.VnfsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.vnfs.VnfBuilder u35 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.vnfs.VnfBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.vnfs.vnf.VnfDataBuilder u36 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.vnfs.vnf.VnfDataBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.vnfs.vnf.vnf.data.VfModulesBuilder u37 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.vnfs.vnf.vnf.data.VfModulesBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.vnfs.vnf.vnf.data.vf.modules.VfModuleBuilder u38 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.vnfs.vnf.vnf.data.vf.modules.VfModuleBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.vnfs.vnf.vnf.data.vf.modules.vf.module.VfModuleDataBuilder u39 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.service.data.vnfs.vnf.vnf.data.vf.modules.vf.module.VfModuleDataBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.information.ServiceInformationBuilder u40 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.information.ServiceInformationBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.level.oper.status.ServiceLevelOperStatusBuilder u41 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.level.oper.status.ServiceLevelOperStatusBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.model.infrastructure.ServiceBuilder u42 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.model.infrastructure.ServiceBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.parameters.ServiceParametersBuilder u43 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.parameters.ServiceParametersBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.parameters.service.parameters.ServiceParameterBuilder u44 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.parameters.service.parameters.ServiceParameterBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.request.input.ServiceRequestInputBuilder u45 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.request.input.ServiceRequestInputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.request.input.service.request.input.ServiceInputParametersBuilder u46 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.request.input.service.request.input.ServiceInputParametersBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.response.information.ServiceResponseInformationBuilder u47 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.response.information.ServiceResponseInformationBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.ServicesBuilder u48 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.ServicesBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.status.ServiceStatusBuilder u49 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.status.ServiceStatusBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.topology.identifier.ServiceTopologyIdentifierBuilder u50 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.topology.identifier.ServiceTopologyIdentifierBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.ServiceTopologyOperationInputBuilder u51 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.ServiceTopologyOperationInputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.ServiceTopologyOperationOutputBuilder u52 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.ServiceTopologyOperationOutputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.topology.ServiceTopologyBuilder u53 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.topology.ServiceTopologyBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.sriov.vlan.filter.list.SriovVlanFilterListBuilder u54 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.sriov.vlan.filter.list.SriovVlanFilterListBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.subnets.SubnetsBuilder u55 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.subnets.SubnetsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.assignments.VfModuleAssignmentsBuilder u56 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.assignments.VfModuleAssignmentsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.assignments.vf.module.assignments.VmsBuilder u57 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.assignments.vf.module.assignments.VmsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.assignments.vf.module.assignments.vms.VmBuilder u58 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.assignments.vf.module.assignments.vms.VmBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.information.VfModuleInformationBuilder u59 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.information.VfModuleInformationBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.level.oper.status.VfModuleLevelOperStatusBuilder u60 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.level.oper.status.VfModuleLevelOperStatusBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.request.input.VfModuleRequestInputBuilder u61 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.request.input.VfModuleRequestInputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.request.input.vf.module.request.input.VfModuleInputParametersBuilder u62 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.request.input.vf.module.request.input.VfModuleInputParametersBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.response.information.VfModuleResponseInformationBuilder u63 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.response.information.VfModuleResponseInformationBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.topology.identifier.VfModuleTopologyIdentifierBuilder u64 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.topology.identifier.VfModuleTopologyIdentifierBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.VfModuleTopologyOperationInputBuilder u65 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.VfModuleTopologyOperationInputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.VfModuleTopologyOperationOutputBuilder u66 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.VfModuleTopologyOperationOutputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.topology.VfModuleTopologyBuilder u67 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.topology.VfModuleTopologyBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.topology.vf.module.topology.VfModuleParametersBuilder u68 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vf.module.topology.vf.module.topology.VfModuleParametersBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.FloatingIpsBuilder u69 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.FloatingIpsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.InterfaceRoutePrefixesBuilder u70 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.InterfaceRoutePrefixesBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.MacAddressesBuilder u71 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.MacAddressesBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.NetworkInformationItemsBuilder u72 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.NetworkInformationItemsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.network.information.items.NetworkInformationItemBuilder u73 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.network.information.items.NetworkInformationItemBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.network.information.items.network.information.item.NetworkIpsBuilder u74 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.network.information.items.network.information.item.NetworkIpsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.sriov.parameters.ApplicationTagsBuilder u75 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.sriov.parameters.ApplicationTagsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.sriov.parameters.application.tags.CTagsBuilder u76 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.sriov.parameters.application.tags.CTagsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.sriov.parameters.application.tags.STagsBuilder u77 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.sriov.parameters.application.tags.STagsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.SriovParametersBuilder u78 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.SriovParametersBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.sriov.parameters.HeatVlanFiltersBuilder u79 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.data.sriov.parameters.HeatVlanFiltersBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.InterfaceRoutePrefixesBuilder u80 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.InterfaceRoutePrefixesBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.NetworkIpsBuilder u81 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.NetworkIpsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.NetworkIpsV6Builder u82 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.NetworkIpsV6Builder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.NetworkMacsBuilder u83 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.network.NetworkMacsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.topology.data.VmNamesBuilder u84 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.topology.data.VmNamesBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.topology.data.VmNetworksBuilder u85 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.topology.data.VmNetworksBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.topology.data.vm.networks.VmNetworkBuilder u86 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.topology.data.vm.networks.VmNetworkBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.topology.VnfVmsBuilder u87 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.topology.VnfVmsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.topology.vnf.vms.VmNamesBuilder u88 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.topology.vnf.vms.VmNamesBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.topology.vnf.vms.VmNetworksBuilder u89 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vm.topology.vnf.vms.VmNetworksBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.assignments.vnf.assignments.AvailabilityZonesBuilder u90 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.assignments.vnf.assignments.AvailabilityZonesBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.assignments.VnfAssignmentsBuilder u91 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.assignments.VnfAssignmentsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.assignments.vnf.assignments.VnfNetworksBuilder u92 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.assignments.vnf.assignments.VnfNetworksBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.information.VnfInformationBuilder u93 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.information.VnfInformationBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.level.oper.status.VnfLevelOperStatusBuilder u94 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.level.oper.status.VnfLevelOperStatusBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.network.data.SubnetsDataBuilder u95 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.network.data.SubnetsDataBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.network.data.subnets.data.SubnetDataBuilder u96 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.network.data.subnets.data.SubnetDataBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.parameters.VnfParametersBuilder u97 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.parameters.VnfParametersBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.request.input.VnfRequestInputBuilder u98 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.request.input.VnfRequestInputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.request.input.vnf.request.input.VnfInputParametersBuilder u99 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.request.input.vnf.request.input.VnfInputParametersBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.request.input.vnf.request.input.VnfNetworksBuilder u100 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.request.input.vnf.request.input.VnfNetworksBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.request.input.vnf.request.input.vnf.networks.VnfNetworkBuilder u101 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.request.input.vnf.request.input.vnf.networks.VnfNetworkBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.resource.assignments.vnf.resource.assignments.AvailabilityZonesBuilder u102 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.resource.assignments.vnf.resource.assignments.AvailabilityZonesBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.resource.assignments.VnfResourceAssignmentsBuilder u103 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.resource.assignments.VnfResourceAssignmentsBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.resource.assignments.vnf.resource.assignments.VnfNetworksBuilder u104 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.resource.assignments.vnf.resource.assignments.VnfNetworksBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.resource.assignments.vnf.resource.assignments.vnf.networks.VnfNetworkBuilder u105 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.resource.assignments.vnf.resource.assignments.vnf.networks.VnfNetworkBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.response.information.VnfResponseInformationBuilder u106 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.response.information.VnfResponseInformationBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.topology.identifier.structure.VnfTopologyIdentifierStructureBuilder u107 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.topology.identifier.structure.VnfTopologyIdentifierStructureBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.topology.identifier.VnfTopologyIdentifierBuilder u108 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.topology.identifier.VnfTopologyIdentifierBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.topology.information.VnfTopologyInformationBuilder u109 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.topology.information.VnfTopologyInformationBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.VnfTopologyOperationInputBuilder u110 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.VnfTopologyOperationInputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.VnfTopologyOperationOutputBuilder u111 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.VnfTopologyOperationOutputBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.topology.VnfTopologyBuilder u112 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.topology.VnfTopologyBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.topology.vnf.topology.VnfParametersDataBuilder u113 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vnf.topology.vnf.topology.VnfParametersDataBuilder();
		org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vpn.bindings.VpnBindingsBuilder u114 = 
			new org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.vpn.bindings.VpnBindingsBuilder();
    }
}
