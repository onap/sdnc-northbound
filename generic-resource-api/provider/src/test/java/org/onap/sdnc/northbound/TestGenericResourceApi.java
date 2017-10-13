/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights
 * 							reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.onap.sdnc.northbound;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.md.sal.binding.test.AbstractConcurrentDataBrokerTest;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.NetworkTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ServiceTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.NetworkTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ServiceTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.network.information.NetworkInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.request.information.RequestInformation.RequestAction;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.request.information.RequestInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.sdnc.request.header.SdncRequestHeader.SvcAction;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.sdnc.request.header.SdncRequestHeaderBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.information.ServiceInformationBuilder;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestGenericResourceApi extends AbstractConcurrentDataBrokerTest {

    private GenericResourceApiProvider genericResourceApiProvider;
    private static final Logger LOG = LoggerFactory.getLogger(GenericResourceApiProvider.class);

    @Before
    public void setUp() throws Exception {
        if (null == genericResourceApiProvider) {
            DataBroker dataBroker = getDataBroker();
            NotificationPublishService mockNotification = mock(NotificationPublishService.class);
            RpcProviderRegistry mockRpcRegistry = mock(RpcProviderRegistry.class);
            GenericResourceApiSvcLogicServiceClient client = mock(GenericResourceApiSvcLogicServiceClient.class);
            try {
            	genericResourceApiProvider = new GenericResourceApiProvider(dataBroker, mockNotification, mockRpcRegistry, client);
            } catch (Exception e) {
            		LOG.error("Caught exception on setUp", e);
            		throw e;
            }
        }
    }

    @Test
    public void testServiceTopologyOperation() {

        ServiceTopologyOperationInputBuilder inputBuilder = new ServiceTopologyOperationInputBuilder();

        SdncRequestHeaderBuilder sdncRequestHeaderBuilder = new SdncRequestHeaderBuilder();
        sdncRequestHeaderBuilder.setSvcRequestId("1111");
        sdncRequestHeaderBuilder.setSvcAction(SvcAction.Assign);
        inputBuilder.setSdncRequestHeader(sdncRequestHeaderBuilder.build());

        RequestInformationBuilder requestInformationBuilder = new RequestInformationBuilder();
        requestInformationBuilder.setRequestId("1111");
        requestInformationBuilder.setRequestAction(RequestAction.CreateServiceInstance);
        inputBuilder.setRequestInformation(requestInformationBuilder.build());

        ServiceInformationBuilder serviceInformationBuilder = new ServiceInformationBuilder();
        serviceInformationBuilder.setServiceInstanceId("1111");
        inputBuilder.setServiceInformation(serviceInformationBuilder.build());

        // TODO: currently initialize GenericResourceApiSvcLogicServiceClient is failing, need to fix
        java.util.concurrent.Future<RpcResult<ServiceTopologyOperationOutput>> future = genericResourceApiProvider
        .serviceTopologyOperation(inputBuilder.build());
        RpcResult<ServiceTopologyOperationOutput> rpcResult = null;
        try {
            rpcResult = future.get();
        } catch (Exception e) {
            fail("Error : " + e);
        }
        LOG.info("result: {}", rpcResult);
        assertEquals("1111", rpcResult.getResult().getSvcRequestId());
    }

    @Test
    public void testNetworkTopologyOperation() {

        NetworkTopologyOperationInputBuilder inputBuilder = new NetworkTopologyOperationInputBuilder();

        SdncRequestHeaderBuilder sdncRequestHeaderBuilder = new SdncRequestHeaderBuilder();
        sdncRequestHeaderBuilder.setSvcRequestId("1111");
        sdncRequestHeaderBuilder.setSvcAction(SvcAction.Create);
        inputBuilder.setSdncRequestHeader(sdncRequestHeaderBuilder.build());

        RequestInformationBuilder requestInformationBuilder = new RequestInformationBuilder();
        requestInformationBuilder.setRequestId("1111");
        requestInformationBuilder.setRequestAction(RequestAction.CreateNetworkInstance);
        inputBuilder.setRequestInformation(requestInformationBuilder.build());

        ServiceInformationBuilder serviceInformationBuilder = new ServiceInformationBuilder();
        serviceInformationBuilder.setServiceInstanceId("1111");
        inputBuilder.setServiceInformation(serviceInformationBuilder.build());

        NetworkInformationBuilder networkInformationBuilder = new NetworkInformationBuilder();
        inputBuilder.setNetworkInformation(networkInformationBuilder.build());

        java.util.concurrent.Future<RpcResult<NetworkTopologyOperationOutput>> future = genericResourceApiProvider
            .networkTopologyOperation(inputBuilder.build());
        RpcResult<NetworkTopologyOperationOutput> rpcResult = null;
        try {
            rpcResult = future.get();
        } catch (Exception e) {
            fail("Error : " + e);
        }
        LOG.info("result: {}", rpcResult);
        assertEquals("1111", rpcResult.getResult().getSvcRequestId());
    }
}
