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
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ServiceTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ServiceTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.request.information.RequestInformation;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.request.information.RequestInformation.RequestAction;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.sdnc.request.header.SdncRequestHeader;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.sdnc.request.header.SdncRequestHeader.SvcAction;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.information.ServiceInformation;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.model.infrastructure.Service;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.status.ServiceStatus;
import org.opendaylight.yangtools.yang.common.RpcResult;

import static org.junit.Assert.assertEquals;
import static org.onap.sdnc.northbound.util.MDSALUtil.build;
import static org.onap.sdnc.northbound.util.MDSALUtil.read;
import static org.onap.sdnc.northbound.util.MDSALUtil.requestInformation;
import static org.onap.sdnc.northbound.util.MDSALUtil.rpc;
import static org.onap.sdnc.northbound.util.MDSALUtil.sdncRequestHeader;
import static org.onap.sdnc.northbound.util.MDSALUtil.service;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceData;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceInformationBuilder;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceResponseInformation;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceStatus;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceTopologyOperationInput;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceTopologyOperationOutput;


/**
 * This class test the ServiceTopologyOperation mdsal RPC.
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceTopologyOperationRPCTest extends GenericResourceApiProviderTest {


    final String SVC_OPERATION = "service-topology-operation";


    @Before
    public void setUp() throws Exception {
        super.setUp();
        svcClient.setScvOperation(SVC_OPERATION);
    }


    /**
     * Verify  ServiceTopologyOperation RPC executes a DG then produces the expected
     * {@link ServiceTopologyOperationOutput} and persisted the expected {@link Service} in the {@link DataBroker}
     */
    @Test
    public void testServiceTopologyOperationRPC_ExecuteDG_Success() throws Exception {


        //mock svcClient to perform a successful execution with the expected parameters
        svcClient.mockHasGraph(true);
        PropBuilder svcResultProp = svcClient.createExecuteOKResult();
        svcClient.mockExecute(svcResultProp);

        // create the ServiceTopologyOperationInput from the template
        ServiceTopologyOperationInput serviceTopologyOperationInput = createSTOI(RequestAction.CreateServiceInstance);

        //execute the mdsal rpc
        ServiceTopologyOperationOutput actualServiceTopologyOperationOutput = rpc(
                genericResourceApiProvider::serviceTopologyOperation
                , RpcResult::getResult
                , serviceTopologyOperationInput
        );


        //verify the returned ServiceTopologyOperationOutput
        ServiceTopologyOperationOutput expectedServiceTopologyOperationOutput = createExpectedSTOO(svcResultProp,serviceTopologyOperationInput);
        assertEquals(expectedServiceTopologyOperationOutput,actualServiceTopologyOperationOutput);


        //verify the persisted Service
        Service actualService = read(dataBroker,serviceTopologyOperationInput.getServiceInformation().getServiceInstanceId(), LogicalDatastoreType.CONFIGURATION);
        Service expectedService = createExpectedService(
                expectedServiceTopologyOperationOutput,
                serviceTopologyOperationInput,
                actualService);
        assertEquals(expectedService,actualService);

        LOG.debug("done");
    }



    private ServiceTopologyOperationInput createSTOI(RequestAction requestAction)
    {

        return build(
                serviceTopologyOperationInput()
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
        );
    }


    private ServiceTopologyOperationOutput createExpectedSTOO(PropBuilder expectedSvcResultProp,ServiceTopologyOperationInput expectedServiceTopologyOperationInput){
        return build(
                serviceTopologyOperationOutput()
                        .setSvcRequestId(expectedServiceTopologyOperationInput.getSdncRequestHeader().getSvcRequestId())
                        .setResponseCode(expectedSvcResultProp.get(svcClient.errorCode))
                        .setAckFinalIndicator(expectedSvcResultProp.get(svcClient.ackFinal))
                        .setResponseMessage(expectedSvcResultProp.get(svcClient.errorMessage))
                        .setServiceResponseInformation(build(serviceResponseInformation()
                                .setInstanceId(expectedServiceTopologyOperationInput.getServiceInformation().getServiceInstanceId())
                                .setObjectPath(expectedSvcResultProp.get(svcClient.serviceObjectPath))
                        ))
        );
    }

    private Service createExpectedService(
            ServiceTopologyOperationOutput expectedServiceTopologyOperationOutput,
            ServiceTopologyOperationInput expectedServiceTopologyOperationInput,
            Service actualService
    ){


        //We cannot predict the timeStamp value so just steal it from the actual
        //we need this to prevent the equals method from returning false as a result of the timestamp
        String responseTimeStamp = actualService == null || actualService.getServiceStatus() == null?
                null : actualService.getServiceStatus().getResponseTimestamp();

        SdncRequestHeader expectedSdncRequestHeader = expectedServiceTopologyOperationInput.getSdncRequestHeader();
        ServiceInformation expectedServiceInformation = expectedServiceTopologyOperationInput.getServiceInformation();
        RequestInformation expectedRequestInformation = expectedServiceTopologyOperationInput.getRequestInformation();

        return build(
               service()
                .setServiceInstanceId(expectedServiceInformation.getServiceInstanceId())
                .setServiceData(build(serviceData()))
                .setServiceStatus(
                        build(
                             serviceStatus()
                                .setAction(expectedRequestInformation.getRequestAction().name())
                                .setFinalIndicator(expectedServiceTopologyOperationOutput.getAckFinalIndicator())
                                .setResponseCode(expectedServiceTopologyOperationOutput.getResponseCode())
                                .setResponseMessage(expectedServiceTopologyOperationOutput.getResponseMessage())
                                .setRpcAction(toRpcAction(expectedSdncRequestHeader.getSvcAction()))
                                .setRpcName(SVC_OPERATION)
                                .setRequestStatus(ServiceStatus.RequestStatus.Synccomplete)
                                .setResponseTimestamp(responseTimeStamp)
                        )
                )
        );

    }

    public ServiceStatus.RpcAction toRpcAction(SvcAction fromEnum){
        return fromEnum == null? null : ServiceStatus.RpcAction.valueOf(fromEnum.name());
    }


}
