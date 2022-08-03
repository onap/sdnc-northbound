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
import static org.onap.sdnc.northbound.GenericResourceApiProvider.INVALID_INPUT_ERROR_MESSAGE;
import static org.onap.sdnc.northbound.GenericResourceApiProvider.NO_SERVICE_LOGIC_ACTIVE;
import static org.onap.sdnc.northbound.GenericResourceApiProvider.NULL_OR_EMPTY_ERROR_PARAM;
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

import java.time.Instant;

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
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.NetworkTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.NetworkTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.OperStatusData.LastAction;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.OperStatusData.LastOrderStatus;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.OperStatusData.LastRpcAction;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.OperStatusData.OrderStatus;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.request.information.RequestInformation;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.sdnc.request.header.SdncRequestHeader;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.sdnc.request.header.SdncRequestHeader.SvcAction;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.data.ServiceData;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.information.ServiceInformation;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.model.infrastructure.Service;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.status.ServiceStatus;
import org.opendaylight.yangtools.yang.common.RpcResult;


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

    @Test
    public void should_fail_when_service_instance_id_not_present() throws Exception {

        NetworkTopologyOperationInput input = networkTopologyOperationInput().build();

        NetworkTopologyOperationOutput output =
            exec(genericResourceApiProvider::networkTopologyOperation, input, RpcResult::getResult);

        assertEquals("404", output.getResponseCode());
        assertEquals(NULL_OR_EMPTY_ERROR_PARAM, output.getResponseMessage());
        assertEquals("Y", output.getAckFinalIndicator());
    }


    @Test
    public void should_fail_when_invalid_service_data() throws Exception {

        NetworkTopologyOperationInput input = networkTopologyOperationInput()
            .setServiceInformation(serviceInformationBuilder()
                .setServiceInstanceId("test-service-instance-id").build()
            ).build();

        NetworkTopologyOperationOutput output =
            exec(genericResourceApiProvider::networkTopologyOperation, input, RpcResult::getResult);

        assertEquals("404", output.getResponseCode());
        assertEquals(INVALID_INPUT_ERROR_MESSAGE, output.getResponseMessage());
        assertEquals("Y", output.getAckFinalIndicator());
    }


    @Test
    public void should_fail_when_client_execution_failed() throws Exception {

        svcClient.mockHasGraph(true);
        svcClient.mockExecute(new RuntimeException("test exception"));

        NetworkTopologyOperationInput input = networkTopologyOperationInput()
            .setServiceInformation(serviceInformationBuilder()
                .setServiceInstanceId("test-service-instance-id").build()
            ).build();

        persistServiceInDataBroker(input);

        NetworkTopologyOperationOutput output =
            exec(genericResourceApiProvider::networkTopologyOperation, input, RpcResult::getResult);

        assertEquals("500", output.getResponseCode());
        assertEquals("test exception", output.getResponseMessage());
        assertEquals("Y", output.getAckFinalIndicator());
    }

    @Test
    public void should_fail_when_client_has_no_graph() throws Exception {

        svcClient.mockHasGraph(false);

        NetworkTopologyOperationInput input = networkTopologyOperationInput()
            .setServiceInformation(serviceInformationBuilder()
                .setServiceInstanceId("test-service-instance-id").build()
            ).build();

        persistServiceInDataBroker(input);

        NetworkTopologyOperationOutput output =
            exec(genericResourceApiProvider::networkTopologyOperation, input, RpcResult::getResult);

        assertEquals("503", output.getResponseCode());
        assertEquals(NO_SERVICE_LOGIC_ACTIVE + APP_NAME + ": '" + SVC_OPERATION + "'", output.getResponseMessage());
        assertEquals("Y", output.getAckFinalIndicator());
    }

    @Test
    public void should_fail_when_failed_to_update_mdsal() throws Exception {

        PropBuilder svcResultProp = svcClient.createExecuteOKResult();
        svcClient.mockExecute(svcResultProp);
        svcClient.mockHasGraph(true);
        WriteTransaction mockWriteTransaction = mock(WriteTransaction.class);
        when(mockWriteTransaction.commit()).thenThrow(new TransactionChainClosedException("test exception"));

        DataBroker spyDataBroker = Mockito.spy(dataBroker);
        when(spyDataBroker.newWriteOnlyTransaction()).thenReturn(mockWriteTransaction);
        genericResourceApiProvider.setDataBroker(spyDataBroker);

        NetworkTopologyOperationInput input = networkTopologyOperationInput()
            .setServiceInformation(serviceInformationBuilder()
                .setServiceInstanceId("test-service-instance-id").build()
            ).build();

        persistServiceInDataBroker(input);
        NetworkTopologyOperationOutput output =
            exec(genericResourceApiProvider::networkTopologyOperation, input, RpcResult::getResult);

        assertEquals("500", output.getResponseCode());
        assertEquals("test exception", output.getResponseMessage());
        assertEquals("Y", output.getAckFinalIndicator());
    }

    /**
     * Verify  ServiceTopologyOperation RPC executes a DG then produces the expected {@link
     * NetworkTopologyOperationOutput} and persisted the expected {@link Service} in the {@link DataBroker}
     */
    @Test
    public void should_success_when_no_errors_encountered() throws Exception {

        //mock svcClient to perform a successful execution with the expected parameters
        svcClient.mockHasGraph(true);
        PropBuilder svcResultProp = svcClient.createExecuteOKResult();
        svcClient.mockExecute(svcResultProp);

        //construct the input parameter for the NetworkTopologyOperation
        NetworkTopologyOperationInput input = createNTOI();

        //pre-populate the DataBroke with the required ServiceData.
        Service service = persistServiceInDataBroker(input);

        //execute the mdsal exec
        NetworkTopologyOperationOutput output = exec(
            genericResourceApiProvider::networkTopologyOperation
            , input
            , RpcResult::getResult
        );

        assertEquals("200", output.getResponseCode());
        assertEquals("OK", output.getResponseMessage());
        assertEquals("Y", output.getAckFinalIndicator());

        //verify the returned NetworkTopologyOperationOutput
        NetworkTopologyOperationOutput expectedNetworkTopologyOperationOutput
            = createExpectedNTOO(svcResultProp, input);
        assertEquals(expectedNetworkTopologyOperationOutput, output);

        //verify the persisted Service
        Service actualService = db.read(
            input.getServiceInformation().getServiceInstanceId(),
            LogicalDatastoreType.CONFIGURATION
        );
        Service expectedService = createExpectedService(
            expectedNetworkTopologyOperationOutput,
            input,
            service.getServiceData(),
            actualService);
        assertEquals(expectedService, actualService);

    }


    private NetworkTopologyOperationInput createNTOI() {

        return
            networkTopologyOperationInput()
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
                )
                .setNetworkInformation(networkInformation().build()
                ).build();
    }

    private Service persistServiceInDataBroker(
        NetworkTopologyOperationInput networkTopologyOperationInput
    ) throws Exception {
        Service service =
            service()
                .setServiceInstanceId(
                    networkTopologyOperationInput.getServiceInformation().getServiceInstanceId()
                )
                .setServiceData(
                    serviceData()
                        .setServiceLevelOperStatus(
                            serviceLevelOperStatus()
                                .setOrderStatus(OrderStatus.Created)
                                .setModifyTimestamp(Instant.now().toString())
                                .setLastSvcRequestId("svc-request-id: abc")
                                .setLastRpcAction(LastRpcAction.Activate)
                                .setLastOrderStatus(LastOrderStatus.PendingAssignment)
                                .setLastAction(LastAction.ActivateNetworkInstance)
                                .setCreateTimestamp(Instant.now().toString()).build()
                        ).build()
                ).build();

        db.write(true, service, LogicalDatastoreType.CONFIGURATION);
        return service;
    }


    private NetworkTopologyOperationOutput createExpectedNTOO(
        PropBuilder expectedSvcResultProp,
        NetworkTopologyOperationInput expectedNetworkTopologyOperationInput) {
        return
            networkTopologyOperationOutput()
                .setSvcRequestId(expectedNetworkTopologyOperationInput.getSdncRequestHeader().getSvcRequestId())
                .setResponseCode(expectedSvcResultProp.get(svcClient.errorCode))
                .setAckFinalIndicator(expectedSvcResultProp.get(svcClient.ackFinal))
                .setResponseMessage(expectedSvcResultProp.get(svcClient.errorMessage))
                .setServiceResponseInformation(serviceResponseInformation()
                    .setInstanceId(expectedNetworkTopologyOperationInput.getServiceInformation().getServiceInstanceId())
                    .setObjectPath(expectedSvcResultProp.get(svcClient.serviceObjectPath)).build()
                )
                .setNetworkResponseInformation(
                    networkResponseInformation()
                        .setInstanceId(expectedSvcResultProp.get(svcClient.networkId))
                        .setObjectPath(expectedSvcResultProp.get(svcClient.networkObjectPath)).build()
                ).build();
    }

    private Service createExpectedService(
        NetworkTopologyOperationOutput expectedNetworkTopologyOperationOutput,
        NetworkTopologyOperationInput expectedNetworkTopologyOperationInput,
        ServiceData expectedServiceData,
        Service actualService
    ) {

        //We cannot predict the timeStamp value so just steal it from the actual
        //we need this to prevent the equals method from returning false as a result of the timestamp
        String responseTimeStamp = actualService == null || actualService.getServiceStatus() == null ?
            null : actualService.getServiceStatus().getResponseTimestamp();

        SdncRequestHeader expectedSdncRequestHeader = expectedNetworkTopologyOperationInput.getSdncRequestHeader();
        ServiceInformation expectedServiceInformation = expectedNetworkTopologyOperationInput.getServiceInformation();
        RequestInformation expectedRequestInformation = expectedNetworkTopologyOperationInput.getRequestInformation();

        return
            service()
                .setServiceInstanceId(expectedServiceInformation.getServiceInstanceId())
                .setServiceData(serviceData().build())
                .setServiceData(expectedServiceData)
                .setServiceStatus(
                        serviceStatus().build()
                ).build();


    }

    public ServiceStatus.RpcAction toRpcAction(SvcAction fromEnum) {
        return fromEnum == null ? null : ServiceStatus.RpcAction.valueOf(fromEnum.name());
    }


}
