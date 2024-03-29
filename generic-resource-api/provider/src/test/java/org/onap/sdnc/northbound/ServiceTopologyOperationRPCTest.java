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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.sdnc.northbound.GenericResourceApiProvider.APP_NAME;
import static org.onap.sdnc.northbound.GenericResourceApiProvider.NO_SERVICE_LOGIC_ACTIVE;
import static org.onap.sdnc.northbound.GenericResourceApiProvider.NULL_OR_EMPTY_ERROR_PARAM;
import static org.onap.sdnc.northbound.util.MDSALUtil.exec;
import static org.onap.sdnc.northbound.util.MDSALUtil.requestInformation;
import static org.onap.sdnc.northbound.util.MDSALUtil.sdncRequestHeader;
import static org.onap.sdnc.northbound.util.MDSALUtil.service;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceData;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceInformationBuilder;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceResponseInformation;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceStatus;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceTopologyOperationInput;
import static org.onap.sdnc.northbound.util.MDSALUtil.serviceTopologyOperationOutput;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.onap.sdnc.northbound.util.PropBuilder;
import org.opendaylight.mdsal.binding.api.DataBroker;
import org.opendaylight.mdsal.binding.api.TransactionChainClosedException;
import org.opendaylight.mdsal.binding.api.WriteTransaction;
import org.opendaylight.mdsal.common.api.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ServiceTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ServiceTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.request.information.RequestInformation;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.sdnc.request.header.SdncRequestHeader;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.sdnc.request.header.SdncRequestHeader.SvcAction;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.information.ServiceInformation;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.model.infrastructure.Service;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.status.ServiceStatus;
import org.opendaylight.yangtools.yang.common.RpcResult;


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
     * Verify  ServiceTopologyOperation RPC executes a DG then produces the expected {@link
     * ServiceTopologyOperationOutput} and persisted the expected {@link Service} in the {@link DataBroker}
     */
    @Test
    public void testServiceTopologyOperationRPC_ExecuteDG_Success() throws Exception {

        //mock svcClient to perform a successful execution with the expected parameters
        svcClient.mockHasGraph(true);
        PropBuilder svcResultProp = svcClient.createExecuteOKResult();
        svcClient.mockExecute(svcResultProp);

        // create the ServiceTopologyOperationInput from the template
        ServiceTopologyOperationInput input = createSTOI();

        //execute the mdsal exec
        ServiceTopologyOperationOutput output = exec(
            genericResourceApiProvider::serviceTopologyOperation
            , input
            , RpcResult::getResult
        );

        assertEquals("200", output.getResponseCode());
        assertEquals("OK", output.getResponseMessage());
        assertEquals("Y", output.getAckFinalIndicator());

        //verify the returned ServiceTopologyOperationOutput
        ServiceTopologyOperationOutput expectedServiceTopologyOperationOutput = createExpectedSTOO(svcResultProp,
            input);
        assertEquals(expectedServiceTopologyOperationOutput, output);

        //verify the persisted Service
        Service actualService = db.read(input.getServiceInformation().getServiceInstanceId(),
            LogicalDatastoreType.CONFIGURATION);
        Service expectedService = createExpectedService(
            expectedServiceTopologyOperationOutput,
            input,
            actualService);
        assertEquals(expectedService, actualService);

        LOG.debug("done");
    }

    @Test
    public void should_fail_when_service_info_not_present() throws Exception {
        // create the ServiceTopologyOperationInput from the template
        ServiceTopologyOperationInput input =
            serviceTopologyOperationInput()
                .setSdncRequestHeader(sdncRequestHeader()
                    .setSvcRequestId("svc-request-id: xyz")
                    .setSvcAction(SvcAction.Assign).build()
                )
                .setRequestInformation(requestInformation()
                    .setRequestId("request-id: xyz")
                    .setRequestAction(RequestInformation.RequestAction.CreateServiceInstance).build()
                ).build();

        //execute the mdsal exec
        ServiceTopologyOperationOutput output = exec(
            genericResourceApiProvider::serviceTopologyOperation
            , input
            , RpcResult::getResult
        );

        assertEquals("404", output.getResponseCode());
        assertEquals(NULL_OR_EMPTY_ERROR_PARAM, output.getResponseMessage());
        assertEquals("Y", output.getAckFinalIndicator());
    }


    @Test
    public void should_fail_when_client_execution_failed() throws Exception {

        svcClient.mockHasGraph(true);
        svcClient.mockExecute(new RuntimeException("test exception"));

        ServiceTopologyOperationInput input = createSTOI();

        //execute the mdsal exec
        ServiceTopologyOperationOutput output = exec(
            genericResourceApiProvider::serviceTopologyOperation
            , input
            , RpcResult::getResult
        );
        
        assertEquals("500", output.getResponseCode());
        assertEquals("test exception", output.getResponseMessage());
        assertEquals("Y", output.getAckFinalIndicator());
    }

    @Test
    public void delete_fail_when_client_execution_failed() throws Exception {

        //mock svcClient to perform a successful execution with the expected parameters
        svcClient.mockHasGraph(true);
        PropBuilder svcResultProp = svcClient.createExecuteOKResult();
        svcClient.mockExecute(svcResultProp);

        ServiceTopologyOperationInput input = deleteSTOI();

        //execute the mdsal exec
        ServiceTopologyOperationOutput output = exec(
                genericResourceApiProvider::serviceTopologyOperation
                , input
                , RpcResult::getResult
        );

        assertEquals("200", output.getResponseCode());
        assertEquals("OK", output.getResponseMessage());
        assertEquals("Y", output.getAckFinalIndicator());
    }

    @Test
    public void delete_service_fail_when_client_execution_failed() throws Exception {

        //mock svcClient to perform a successful execution with the expected parameters
        svcClient.mockHasGraph(true);
        PropBuilder svcResultProp = svcClient.createExecuteOKResult();
        svcClient.mockExecute(svcResultProp);

        ServiceTopologyOperationInput input = deleteServiceSTOI();

        //execute the mdsal exec
        ServiceTopologyOperationOutput output = exec(
                genericResourceApiProvider::serviceTopologyOperation
                , input
                , RpcResult::getResult
        );

        assertEquals("200", output.getResponseCode());
        assertEquals("Y", output.getAckFinalIndicator());
    }

    @Test
    public void should_fail_when_client_has_no_graph() throws Exception {
        svcClient.mockHasGraph(false);

        ServiceTopologyOperationInput input = createSTOI();

        //execute the mdsal exec
        ServiceTopologyOperationOutput output = exec(
            genericResourceApiProvider::serviceTopologyOperation
            , input
            , RpcResult::getResult
        );

        assertEquals("503", output.getResponseCode());
        assertEquals(NO_SERVICE_LOGIC_ACTIVE + APP_NAME + ": '" + SVC_OPERATION + "'", output.getResponseMessage());
        assertEquals("Y", output.getAckFinalIndicator());
    }


    @Test
    public void should_fail_when_failed_to_update_mdsal() throws Exception {

        svcClient.mockHasGraph(true);
        WriteTransaction mockWriteTransaction = mock(WriteTransaction.class);
        when(mockWriteTransaction.commit()).thenThrow(new TransactionChainClosedException("test exception"));

        DataBroker spyDataBroker = Mockito.spy(dataBroker);
        when(spyDataBroker.newWriteOnlyTransaction()).thenReturn(mockWriteTransaction);
        genericResourceApiProvider.setDataBroker(spyDataBroker);

        ServiceTopologyOperationInput input = createSTOI();

        //execute the mdsal exec
        ServiceTopologyOperationOutput output = exec(
            genericResourceApiProvider::serviceTopologyOperation
            , input
            , RpcResult::getResult
        );

        assertEquals("500", output.getResponseCode());
        assertEquals("test exception", output.getResponseMessage());
        assertEquals("Y", output.getAckFinalIndicator());
    }

    private ServiceTopologyOperationInput createSTOI() {

        return
            serviceTopologyOperationInput()
                .setSdncRequestHeader(sdncRequestHeader()
                    .setSvcRequestId("svc-request-id: xyz")
                    .setSvcAction(SvcAction.Assign).build()
                )
                .setRequestInformation(requestInformation()
                    .setRequestId("request-id: xyz")
                    .setRequestAction(RequestInformation.RequestAction.CreateServiceInstance).build()
                )
                .setServiceInformation(serviceInformationBuilder()
                    .setServiceInstanceId("service-instance-id: xyz").build()
                ).build();
    }

    private ServiceTopologyOperationInput deleteSTOI() {

        return
                serviceTopologyOperationInput()
                        .setSdncRequestHeader(sdncRequestHeader()
                                .setSvcRequestId("svc-request-id: xyz")
                                .setSvcAction(SvcAction.Unassign).build()
                        )
                        .setRequestInformation(requestInformation()
                                .setRequestId("request-id: xyz")
                                .setRequestAction(RequestInformation.RequestAction.DeleteServiceInstance).build()
                        )
                        .setServiceInformation(serviceInformationBuilder()
                                .setServiceInstanceId("service-instance-id: xyz").build()
                        ).build();
    }

    private ServiceTopologyOperationInput deleteServiceSTOI() {

        return
                serviceTopologyOperationInput()
                        .setSdncRequestHeader(sdncRequestHeader()
                                .setSvcRequestId("svc-request-id: xyz")
                                .setSvcAction(SvcAction.Delete).build()
                        )
                        .setRequestInformation(requestInformation()
                                .setRequestId("request-id: xyz")
                                .setRequestAction(RequestInformation.RequestAction.DeleteServiceInstance).build()
                        )
                        .setServiceInformation(serviceInformationBuilder()
                                .setServiceInstanceId("service-instance-id: xyz").build()
                        ).build();
    }


    private ServiceTopologyOperationOutput createExpectedSTOO(PropBuilder expectedSvcResultProp,
        ServiceTopologyOperationInput expectedServiceTopologyOperationInput) {
        return
            serviceTopologyOperationOutput()
                .setSvcRequestId(expectedServiceTopologyOperationInput.getSdncRequestHeader().getSvcRequestId())
                .setResponseCode(expectedSvcResultProp.get(svcClient.errorCode))
                .setAckFinalIndicator(expectedSvcResultProp.get(svcClient.ackFinal))
                .setResponseMessage(expectedSvcResultProp.get(svcClient.errorMessage))
                .setServiceResponseInformation(serviceResponseInformation()
                    .setInstanceId(expectedServiceTopologyOperationInput.getServiceInformation().getServiceInstanceId())
                    .setObjectPath(expectedSvcResultProp.get(svcClient.serviceObjectPath)).build()
                ).build();
    }

    private Service createExpectedService(
        ServiceTopologyOperationOutput expectedServiceTopologyOperationOutput,
        ServiceTopologyOperationInput expectedServiceTopologyOperationInput,
        Service actualService
    ) {

        //We cannot predict the timeStamp value so just steal it from the actual
        //we need this to prevent the equals method from returning false as a result of the timestamp
        String responseTimeStamp = actualService == null || actualService.getServiceStatus() == null ?
            null : actualService.getServiceStatus().getResponseTimestamp();

        SdncRequestHeader expectedSdncRequestHeader = expectedServiceTopologyOperationInput.getSdncRequestHeader();
        ServiceInformation expectedServiceInformation = expectedServiceTopologyOperationInput.getServiceInformation();
        RequestInformation expectedRequestInformation = expectedServiceTopologyOperationInput.getRequestInformation();

        return
            service()
                .setServiceInstanceId(expectedServiceInformation.getServiceInstanceId())
                .setServiceData(serviceData().build())
                .setServiceStatus(
                        serviceStatus()
                            .setAction(expectedRequestInformation.getRequestAction().name())
                            .setFinalIndicator(expectedServiceTopologyOperationOutput.getAckFinalIndicator())
                            .setResponseCode(expectedServiceTopologyOperationOutput.getResponseCode())
                            .setResponseMessage(expectedServiceTopologyOperationOutput.getResponseMessage())
                            .setRpcAction(toRpcAction(expectedSdncRequestHeader.getSvcAction()))
                            .setRpcName(SVC_OPERATION)
                            .setRequestStatus(ServiceStatus.RequestStatus.Synccomplete)
                            .setResponseTimestamp(responseTimeStamp).build()
                ).build();

    }

    public ServiceStatus.RpcAction toRpcAction(SvcAction fromEnum) {
        return fromEnum == null ? null : ServiceStatus.RpcAction.valueOf(fromEnum.name());
    }


}
