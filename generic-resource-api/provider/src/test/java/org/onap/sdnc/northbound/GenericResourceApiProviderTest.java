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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.onap.sdnc.northbound.util.DataBrokerUtil;
import org.onap.sdnc.northbound.util.GenericResourceApiSvcLogicServiceClientMockUtil;
import org.onap.sdnc.northbound.util.PropBuilder;
import org.opendaylight.mdsal.binding.api.DataBroker;
import org.opendaylight.mdsal.binding.api.NotificationPublishService;
import org.opendaylight.mdsal.binding.api.RpcProviderService;
import org.opendaylight.mdsal.binding.testutils.DataBrokerTestModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GenericResourceApiProviderTest {

    protected static final Logger LOG = LoggerFactory.getLogger(GenericResourceApiProvider.class);
    protected GenericResourceApiProvider genericResourceApiProvider;
    protected DataBroker dataBroker;
    protected @Mock NotificationPublishService mockNotificationPublishService;
    protected @Mock RpcProviderService mockRpcProviderService;
    protected @Mock GenericResourceApiSvcLogicServiceClient mockGenericResourceApiSvcLogicServiceClient;



    protected DataBrokerUtil db;
    protected GenericResourceApiSvcLogicServiceClientMockUtil svcClient;


    @Before
    public void setUp() throws Exception {
        svcClient = new GenericResourceApiSvcLogicServiceClientMockUtil(mockGenericResourceApiSvcLogicServiceClient);
        dataBroker = getDataBroker();
        db = new DataBrokerUtil(dataBroker);
         try {
            genericResourceApiProvider = new GenericResourceApiProvider(
                    dataBroker,
                    mockNotificationPublishService,
                    mockRpcProviderService,
                    mockGenericResourceApiSvcLogicServiceClient
            );
        } catch (Exception e) {
            LOG.error("Caught exception on setUp", e);
            throw e;
        }
    }

    public DataBroker getDataBroker() {
        return DataBrokerTestModule.dataBroker();
    }

    public static PropBuilder prop(){
        return (new PropBuilder());
    }
}
