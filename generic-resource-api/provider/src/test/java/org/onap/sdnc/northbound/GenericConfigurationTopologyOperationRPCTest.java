package org.onap.sdnc.northbound;

import static org.junit.Assert.assertEquals;
import static org.onap.sdnc.northbound.util.MDSALUtil.GenericConfigurationTopologyOperationInput;
import static org.onap.sdnc.northbound.util.MDSALUtil.build;
import static org.onap.sdnc.northbound.util.MDSALUtil.exec;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.GenericConfigurationTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.GenericConfigurationTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.information.ServiceInformationBuilder;
import org.opendaylight.yangtools.yang.common.RpcResult;

@RunWith(MockitoJUnitRunner.class)
public class GenericConfigurationTopologyOperationRPCTest extends GenericResourceApiProviderTest {

    private static final String SVC_OPERATION = "generic-configuration-topology-operation";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        svcClient.setScvOperation(SVC_OPERATION);
    }

    @Test
    public void should_fail_when_invalid_vnf_topology() throws Exception {

        GenericConfigurationTopologyOperationInput input = build(GenericConfigurationTopologyOperationInput());

        GenericConfigurationTopologyOperationOutput output =
                exec(genericResourceApiProvider::genericConfigurationTopologyOperation, input, RpcResult::getResult);

        assertEquals("404", output.getResponseCode());
        assertEquals("invalid input, null or empty service-instance-id", output.getResponseMessage());
        assertEquals("Y", output.getAckFinalIndicator());
    }

    @Test
    public void should_fail_when_valid_vnf_topology() throws Exception {

        GenericConfigurationTopologyOperationInput input = build(GenericConfigurationTopologyOperationInput()
                .setServiceInformation(new ServiceInformationBuilder().setServiceInstanceId("ServiceInsatnceId").build()));

        GenericConfigurationTopologyOperationOutput output =
                exec(genericResourceApiProvider::genericConfigurationTopologyOperation, input, RpcResult::getResult);

        assertEquals("404", output.getResponseCode());
        assertEquals("invalid input, null or empty configuration-id or configuration-type", output.getResponseMessage());
        assertEquals("Y", output.getAckFinalIndicator());
    }

}

