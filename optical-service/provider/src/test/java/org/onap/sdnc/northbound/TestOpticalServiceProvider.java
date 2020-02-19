/*-
 * ============LICENSE_START=======================================================
 * ONAP : SDN-C
 * ================================================================================
 * Copyright (C) 2019-2020 Fujitsu Limited Intellectual Property. All rights
 *                             reserved.
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

import org.junit.Before;
import org.junit.Test;
import org.onap.sdnc.northbound.OpticalServiceProvider;
import org.onap.sdnc.northbound.OpticalServiceClient;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.md.sal.binding.test.AbstractConcurrentDataBrokerTest;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.create.request.PayloadBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteOutput;

import org.opendaylight.yangtools.yang.common.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestOpticalServiceProvider extends AbstractConcurrentDataBrokerTest {

    private OpticalServiceProvider opticalServiceProvider;
    private static final Logger LOG = LoggerFactory.getLogger(OpticalServiceProvider.class);
    String module = "OpticalService"; 
    String rpc = "optical-service-create"; 
    String version = null; 
    String mode = "sync"; 

    @Before
    public void setUp() throws Exception {
        if (null == opticalServiceProvider) {
            DataBroker dataBroker = getDataBroker();
            NotificationPublishService mockNotification = mock(NotificationPublishService.class);
            RpcProviderRegistry mockRpcRegistry = mock(RpcProviderRegistry.class);
            OpticalServiceClient mockSliClient = mock(OpticalServiceClient.class);
            when(mockSliClient.hasGraph(module, rpc, version, mode)).thenReturn(false); 
            opticalServiceProvider = new OpticalServiceProvider(dataBroker, mockNotification, mockRpcRegistry, mockSliClient);
        }
    }

    //Should return error 503 when No service logic active for optical-service.
    @Test
    public void testOpticalServiceCreate() {

        OpticalServiceCreateInputBuilder inputBuilder = new OpticalServiceCreateInputBuilder();

        java.util.concurrent.Future<RpcResult<OpticalServiceCreateOutput>> future = opticalServiceProvider
                                                                          .opticalServiceCreate(inputBuilder.build());
        RpcResult<OpticalServiceCreateOutput> rpcResult = null;
        try {
            rpcResult = future.get();
        } catch (Exception e) {
            fail("Error : " + e);
        }
        assertEquals("503", rpcResult.getResult().getResponseCode());
    }
    
    @Test
    public void testOpticalServiceDelete() {

        OpticalServiceDeleteInputBuilder inputBuilder = new OpticalServiceDeleteInputBuilder();
        java.util.concurrent.Future<RpcResult<OpticalServiceDeleteOutput>> future = opticalServiceProvider
                                                                          .opticalServiceDelete(inputBuilder.build());
        RpcResult<OpticalServiceDeleteOutput> rpcResult = null;
        try {
            rpcResult = future.get();
        } catch (Exception e) {
            fail("Error : " + e);
        }
        assertEquals("503", rpcResult.getResult().getResponseCode());
    }
    
    
    

    //Input parameter validation
    @Test
    public void testOpticalServiceCreateInputValidation() {

        java.util.concurrent.Future<RpcResult<OpticalServiceCreateOutput>> future = opticalServiceProvider
                                                                                      .opticalServiceCreate(null);
        RpcResult<OpticalServiceCreateOutput> rpcResult = null;
        try {
            rpcResult = future.get();
        } catch (Exception e) {
            fail("Error : " + e);
        }
        assertEquals("403", rpcResult.getResult().getResponseCode());
    }
    
}
