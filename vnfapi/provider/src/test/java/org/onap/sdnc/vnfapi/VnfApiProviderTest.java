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

package org.onap.sdnc.vnfapi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.onap.sdnc.vnfapi.util.DataBrokerUtil;
import org.onap.sdnc.vnfapi.util.PropBuilder;
import org.onap.sdnc.vnfapi.util.VNFSDNSvcLogicServiceClientMockUtil;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.md.sal.binding.test.AbstractConcurrentDataBrokerTest;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yang.gen.v1.org.onap.sdnctl.vnf.rev150720.VnfInstanceTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnctl.vnf.rev150720.VnfInstanceTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnctl.vnf.rev150720.VnfInstanceTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnctl.vnf.rev150720.vnf.instance.request.information.VnfInstanceRequestInformation;
import org.opendaylight.yang.gen.v1.org.onap.sdnctl.vnf.rev150720.vnf.instance.request.information.VnfInstanceRequestInformationBuilder;

import java.util.concurrent.Future;


public class VnfApiProviderTest extends AbstractConcurrentDataBrokerTest {

    protected VnfApiProvider vnfapiProvider;
    protected DataBroker dataBroker;
    protected @Mock NotificationPublishService mockNotificationPublishService;
    protected @Mock RpcProviderRegistry mockRpcProviderRegistry;
    protected @Mock VNFSDNSvcLogicServiceClient mockVNFSDNSvcLogicServiceClient;
    protected static final Logger LOG = LoggerFactory.getLogger(VnfApiProvider.class);

    protected DataBrokerUtil db;
    protected VNFSDNSvcLogicServiceClientMockUtil svcClient;


    @Before
    public void setUp() throws Exception {
        svcClient = new VNFSDNSvcLogicServiceClientMockUtil(mockVNFSDNSvcLogicServiceClient);
        dataBroker = getDataBroker();
        db = new DataBrokerUtil(dataBroker);
         try {
            vnfapiProvider = new VnfApiProvider(
                    dataBroker,
                    mockNotificationPublishService,
                    mockRpcProviderRegistry,
                    mockVNFSDNSvcLogicServiceClient
            );
        } catch (Exception e) {
            LOG.error("Caught exception on setUp", e);
            throw e;
        }
    }


    public static PropBuilder prop(){
        return (new PropBuilder());
    }

    @Test
    public void vnfInstanceTopologyOperationInputIsNull() throws Exception {
        VnfInstanceTopologyOperationInput input = null;
        VnfInstanceTopologyOperationOutput result = vnfapiProvider
                .vnfInstanceTopologyOperation(input)
                .get()
                .getResult();

        checkVnfInstanceTopologyOperationOutput(result);
    }


    @Test
    public void vnfInstanceTopologyOperationInput_VnfInstanceRequestInformationIsNull() throws Exception {
        VnfInstanceTopologyOperationInputBuilder builder = new VnfInstanceTopologyOperationInputBuilder();
        builder.setVnfInstanceRequestInformation(null);

        VnfInstanceTopologyOperationInput input = builder.build();
        VnfInstanceTopologyOperationOutput result = vnfapiProvider
                .vnfInstanceTopologyOperation(input)
                .get()
                .getResult();

        checkVnfInstanceTopologyOperationOutput(result);
    }

    @Test
    public void vnfInstanceTopologyOperationInput_getVnfInstanceRequestInformationVnfInstanceIdIsNull() throws Exception {
        VnfInstanceTopologyOperationInputBuilder builder = new VnfInstanceTopologyOperationInputBuilder();
        builder.setVnfInstanceRequestInformation(new VnfInstanceRequestInformationBuilder()
            .setVnfInstanceId(null)
             .build());

        VnfInstanceTopologyOperationInput input = builder.build();
        VnfInstanceTopologyOperationOutput result = vnfapiProvider
                .vnfInstanceTopologyOperation(input)
                .get()
                .getResult();

        checkVnfInstanceTopologyOperationOutput(result);
    }

    private void checkVnfInstanceTopologyOperationOutput(VnfInstanceTopologyOperationOutput result) {
        String expectedResponseCode = "403";
        String expectedResponseMessage = "invalid input, null or empty vnf-instance-id";
        String expectedAckFinalIndicator = "Y";

        Assert.assertEquals(result.getResponseCode(), expectedResponseCode );
        Assert.assertEquals(result.getResponseMessage(), expectedResponseMessage);
        Assert.assertEquals(result.getAckFinalIndicator(), expectedAckFinalIndicator);
    }
 }
