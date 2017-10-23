/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights
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
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.onap.sdnc.northbound.util.PropBuilder;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.NetworkTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.NetworkTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.request.information.RequestInformation;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.sdnc.request.header.SdncRequestHeader;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.sdnc.request.header.SdncRequestHeader.SvcAction;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.data.ServiceData;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.information.ServiceInformation;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.model.infrastructure.Service;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.status.ServiceStatus;
import org.opendaylight.yangtools.yang.common.RpcResult;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.onap.sdnc.northbound.util.MDSALUtil.build;
import static org.onap.sdnc.northbound.util.MDSALUtil.exec;
import static org.onap.sdnc.northbound.util.MDSALUtil.networkInformation;
import static org.onap.sdnc.northbound.util.MDSALUtil.networkResponseInformation;
import static org.onap.sdnc.northbound.util.MDSALUtil.networkTopologyOperationInput;
import static org.onap.sdnc.northbound.util.MDSALUtil.networkTopologyOperationOutput;
import static org.onap.sdnc.northbound.util.MDSALUtil.requestInformation;
import static org.onap.sdnc.northbound.util.MDSALUtil.sdncRequestHeader;
import static org.onap.sdnc.northbound.util.MDSALUtil.service;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceData;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceInformationBuilder;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceLevelOperStatus;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceResponseInformation;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceStatus;
import static org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.OperStatusData.LastAction;
import static org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.OperStatusData.LastOrderStatus;
import static org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.OperStatusData.LastRpcAction;
import static org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.OperStatusData.OrderStatus;


/**
 * This class test the NetworkTopologyOperation mdsal RPC.
 */
@RunWith(MockitoJUnitRunner.class)
public class NetworkTopologyOperationRPCTest extends GenericResourceApiProviderTest {


    final String SVC_OPERATION = "network-topology-operation";


    @Before
    public void setUp() throws Exception {
        super.setUp();
        svcClient.setScvOperation(SVC_OPERATION);
    }


    /**
     * Verify  ServiceTopologyOperation RPC executes a DG then produces the expected
     * {@link NetworkTopologyOperationOutput} and persisted the expected {@link Service} in the {@link DataBroker}
     */
    @Test
    public void testNetworkTopologyOperation() throws Exception {

        //mock svcClient to perform a successful execution with the expected parameters
        svcClient.mockHasGraph(true);
        PropBuilder svcResultProp = svcClient.createExecuteOKResult();
        svcClient.mockExecute(svcResultProp);


        //construct the input parameter for the NetworkTopologyOperation
        NetworkTopologyOperationInput networkTopologyOperationInput = createNTOI();


        //pre-populate the DataBroke with the required ServiceData.
        Service service = persistServiceInDataBroker(networkTopologyOperationInput);



        //execute the mdsal exec
        NetworkTopologyOperationOutput actualNetworkTopologyOperationOutput = exec(
                genericResourceApiProvider::networkTopologyOperation
                , networkTopologyOperationInput
                , RpcResult::getResult
        );

        //verify the returned NetworkTopologyOperationOutput
        NetworkTopologyOperationOutput expectedNetworkTopologyOperationOutput
                = createExpectedNTOO(svcResultProp,networkTopologyOperationInput);
        assertEquals(expectedNetworkTopologyOperationOutput,actualNetworkTopologyOperationOutput);


        //verify the persisted Service
        Service actualService = db.read(
                networkTopologyOperationInput.getServiceInformation().getServiceInstanceId(),
                LogicalDatastoreType.CONFIGURATION
        );
        Service expectedService = createExpectedService(
                expectedNetworkTopologyOperationOutput,
                networkTopologyOperationInput,
                service.getServiceData(),
                actualService);
        assertEquals(expectedService,actualService);

    }


    private NetworkTopologyOperationInput createNTOI()
    {

        return build(
                networkTopologyOperationInput()
                        .setSdncRequestHeader(build(sdncRequestHeader()
                                .setSvcRequestId("svc-request-id: xyz")
                                .setSvcAction(SvcAction.Assign)
                        ))
                        .setRequestInformation(build(requestInformation()
                                .setRequestId("request-id: xyz")
                                .setRequestAction(RequestInformation.RequestAction.CreateServiceInstance)
                        ))
                        .setServiceInformation(build(serviceInformationBuilder()
                                .setServiceInstanceId("service-instance-id: xyz")
                        ))
                        .setNetworkInformation(build(
                                networkInformation()
                        ))
        );
    }

    private Service persistServiceInDataBroker(
            NetworkTopologyOperationInput networkTopologyOperationInput
    ) throws Exception{
        Service service = build(
                service()
                        .setServiceInstanceId(
                                networkTopologyOperationInput.getServiceInformation().getServiceInstanceId()
                        )
                        .setServiceData(build(
                                serviceData()
                                        .setServiceLevelOperStatus(build(
                                                serviceLevelOperStatus()
                                                        .setOrderStatus(OrderStatus.Created)
                                                        .setModifyTimestamp(Instant.now().toString())
                                                        .setLastSvcRequestId("svc-request-id: abc")
                                                        .setLastRpcAction(LastRpcAction.Activate)
                                                        .setLastOrderStatus(LastOrderStatus.PendingAssignment)
                                                        .setLastAction(LastAction.ActivateNetworkInstance)
                                                        .setCreateTimestamp(Instant.now().toString())
                                        ))
                        ))

        );
        db.write(true,service, LogicalDatastoreType.CONFIGURATION);
        return service;
    }






    private NetworkTopologyOperationOutput createExpectedNTOO(
            PropBuilder expectedSvcResultProp,
            NetworkTopologyOperationInput expectedNetworkTopologyOperationInput){
        return build(
                networkTopologyOperationOutput()
                        .setSvcRequestId(expectedNetworkTopologyOperationInput.getSdncRequestHeader().getSvcRequestId())
                        .setResponseCode(expectedSvcResultProp.get(svcClient.errorCode))
                        .setAckFinalIndicator(expectedSvcResultProp.get(svcClient.ackFinal))
                        .setResponseMessage(expectedSvcResultProp.get(svcClient.errorMessage))
                        .setServiceResponseInformation(build(serviceResponseInformation()
                                .setInstanceId(expectedNetworkTopologyOperationInput.getServiceInformation().getServiceInstanceId())
                                .setObjectPath(expectedSvcResultProp.get(svcClient.serviceObjectPath))
                        ))
                        .setNetworkResponseInformation(build(
                                networkResponseInformation()
                                .setInstanceId(expectedSvcResultProp.get(svcClient.networkId))
                                .setObjectPath(expectedSvcResultProp.get(svcClient.networkObjectPath))
                        ))
        );
    }

    private Service createExpectedService(
            NetworkTopologyOperationOutput expectedNetworkTopologyOperationOutput,
            NetworkTopologyOperationInput expectedNetworkTopologyOperationInput,
            ServiceData expectedServiceData,
            Service actualService
    ){


        //We cannot predict the timeStamp value so just steal it from the actual
        //we need this to prevent the equals method from returning false as a result of the timestamp
        String responseTimeStamp = actualService == null || actualService.getServiceStatus() == null?
                null : actualService.getServiceStatus().getResponseTimestamp();

        SdncRequestHeader expectedSdncRequestHeader = expectedNetworkTopologyOperationInput.getSdncRequestHeader();
        ServiceInformation expectedServiceInformation = expectedNetworkTopologyOperationInput.getServiceInformation();
        RequestInformation expectedRequestInformation = expectedNetworkTopologyOperationInput.getRequestInformation();

        return build(
                service()
                        .setServiceInstanceId(expectedServiceInformation.getServiceInstanceId())
                        .setServiceData(build(serviceData()))
                        .setServiceData(expectedServiceData)
                        .setServiceStatus(
                                build(
                                        serviceStatus()
                                )
                        )
        );

    }

    public ServiceStatus.RpcAction toRpcAction(SvcAction fromEnum){
        return fromEnum == null? null : ServiceStatus.RpcAction.valueOf(fromEnum.name());
    }




}
