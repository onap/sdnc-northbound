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

import com.google.common.util.concurrent.CheckedFuture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mockito;
import org.onap.ccsdk.sli.core.sli.SvcLogicException;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.binding.impl.BindingDOMDataBrokerAdapter;
import org.opendaylight.controller.md.sal.binding.impl.BindingToNormalizedNodeCodec;
import org.opendaylight.controller.md.sal.binding.test.AbstractDataBrokerTestCustomizer;
import org.opendaylight.controller.md.sal.binding.test.ConcurrentDataBrokerTestCustomizer;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.controller.md.sal.dom.api.DOMDataBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.yang.gen.v1.org.onap.sdnctl.vnf.rev150720.VnfInstanceTopologyOperationInput;
import org.opendaylight.yang.gen.v1.org.onap.sdnctl.vnf.rev150720.VnfInstanceTopologyOperationOutput;
import org.opendaylight.yang.gen.v1.org.onap.sdnctl.vnf.rev150720.VnfInstanceTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnctl.vnf.rev150720.vnf.instance.request.information.VnfInstanceRequestInformation;
import org.opendaylight.yang.gen.v1.org.onap.sdnctl.vnf.rev150720.vnf.instance.request.information.VnfInstanceRequestInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnctl.vnf.rev150720.vnf.instance.service.data.VnfInstanceServiceDataBuilder;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

class ExceptionThrowerConfigurator {
    private boolean shouldThrowExceptionDuringTransactionPut;

    public ExceptionThrowerConfigurator() {
        this.shouldThrowExceptionDuringTransactionPut = true;
    }

    public boolean shouldThrowExceptionDuringTransactionPut() {
        return shouldThrowExceptionDuringTransactionPut;
    }

    public void setShouldThrowExceptionDuringTransactionPut(boolean shouldThrowExceptionDuringTransactionPut) {
        this.shouldThrowExceptionDuringTransactionPut = shouldThrowExceptionDuringTransactionPut;
    }
}

class DataBrokerErrorMsgConfigurator {
    static public String JAVA_LANG_RUNTIME_EXCEPTION = "java.lang.RuntimeException: ";
    static public String TRANSACTION_WRITE_ERROR = "transaction-write-error";
}

class DataBrokerStab extends BindingDOMDataBrokerAdapter {
    ExceptionThrowerConfigurator exceptionThrowerConfigurator;

    public DataBrokerStab(final DOMDataBroker domDataBroker,
        final BindingToNormalizedNodeCodec codec, ExceptionThrowerConfigurator exceptionThrowerConfigurator) {
        super(domDataBroker, codec);
        this.exceptionThrowerConfigurator = exceptionThrowerConfigurator;

    }

    @Override
    public WriteTransaction newWriteOnlyTransaction() {
        if (exceptionThrowerConfigurator.shouldThrowExceptionDuringTransactionPut()) {
            return newThrowingExceptionWriteOnlyTransaction();
        }
        return newNotThrowingExceptionWriteOnlyTransaction();
    }

    private WriteTransaction newThrowingExceptionWriteOnlyTransaction() {
        WriteTransaction mockWriteTransaction = Mockito.mock(WriteTransaction.class);
        Mockito.doThrow(new RuntimeException(DataBrokerErrorMsgConfigurator.TRANSACTION_WRITE_ERROR))
                .when(mockWriteTransaction).put(Mockito.any(), Mockito.any(), Mockito.any());
        return mockWriteTransaction;
    }

    private WriteTransaction newNotThrowingExceptionWriteOnlyTransaction() {
         WriteTransaction mockWriteTransaction = Mockito.mock(WriteTransaction.class);
        CheckedFuture<Void, TransactionCommitFailedException> mockCheckedFuture =
                Mockito.mock(CheckedFuture.class);
        Mockito.doNothing().when(mockWriteTransaction).put(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.doReturn(mockCheckedFuture).when(mockWriteTransaction).submit();
        return mockWriteTransaction;
    }
}

class VnfApiProviderDataBrokerTestCustomizer  extends ConcurrentDataBrokerTestCustomizer {
    ExceptionThrowerConfigurator exceptionThrowerConfigurator;

    public VnfApiProviderDataBrokerTestCustomizer(ExceptionThrowerConfigurator exceptionThrowerConfigurator) {
        super(false);
        this.exceptionThrowerConfigurator = exceptionThrowerConfigurator;
    }

    public DataBroker createDataBroker() {
        return new DataBrokerStab(createDOMDataBroker(),
            super.getBindingToNormalized(), this.exceptionThrowerConfigurator);
    }
}

@RunWith(MockitoJUnitRunner.class)
public class VnfInstanceTopologyOperationTest extends VnfApiProviderTest {
    protected static final Logger LOG = LoggerFactory.getLogger(VnfInstanceTopologyOperationTest.class);

    private static final String INVALID_INPUT = "invalid input, null or empty vnf-instance-id";
    private static final String NO_SERVICE_LOGIC = "No service logic active for VNF-API: \'vnf-instance-topology-operation\'";
    private static final String VIID = "viid";
    private static final String PRELOAD_NAME = "preloadName";
    private static final String PRELOAD_TYPE = "preloadType";
    private static final String ERROR_CODE = "error-code";
    private static final String ERROR_MESSAGE = "error-message";
    private static final String ACK_FINAL = "ack-final";
    private static final String SVC_OPERATION = "vnf-topology-operation";

    private ExceptionThrowerConfigurator exceptionThrowerConfigurator;

    @Override
    protected AbstractDataBrokerTestCustomizer createDataBrokerTestCustomizer() {
        this.exceptionThrowerConfigurator = new ExceptionThrowerConfigurator();
        return new VnfApiProviderDataBrokerTestCustomizer(this.exceptionThrowerConfigurator);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        svcClient.setScvOperation(SVC_OPERATION);
    }

    @Test
    public void vnfInstanceTopologyOperationInputIsNull() throws Exception {
        VnfInstanceTopologyOperationInput input = null;
        checkVnfInstanceTopologyOperation(input, "403", INVALID_INPUT);
    }

    @Test
    public void vnfInstanceTopologyOperationInput_VnfInstanceRequestInformationIsNull() throws Exception {
        VnfInstanceTopologyOperationInputBuilder builder = new VnfInstanceTopologyOperationInputBuilder();
        builder.setVnfInstanceRequestInformation(null);
        VnfInstanceTopologyOperationInput input = builder.build();
        checkVnfInstanceTopologyOperation(input, "403", INVALID_INPUT);
    }

    @Test
    public void vnfInstanceTopologyOperationInput_getVnfInstanceRequestInformationVnfInstanceIdIsNull() throws Exception {
        VnfInstanceRequestInformation vnfInstanceRequestInformation = createVnfInstanceRequestInformation(null);
        VnfInstanceTopologyOperationInputBuilder builder = createVnfInstanceTopologyOperationInputBuilder(vnfInstanceRequestInformation);
        VnfInstanceTopologyOperationInput input = builder.build();
        checkVnfInstanceTopologyOperation(input, "403", INVALID_INPUT);
    }

    @Test
    public void vnfInstanceTopologyOperationInput_VnfInstanceRequestInformationVnfInstanceIdIsZero() throws Exception {
        VnfInstanceTopologyOperationInputBuilder builder = createVnfInstanceTopologyOperationInputBuilder(createVnfInstanceRequestInformation(""));
        VnfInstanceTopologyOperationInput input = builder.build();
        checkVnfInstanceTopologyOperation(input, "403", INVALID_INPUT);
    }

    @Test
    public void vnfInstanceTopologyOperationInput_svcLogicClientHasGrapheReturnFalse() throws Exception {
        VnfInstanceTopologyOperationInputBuilder builder = createVnfInstanceTopologyOperationInputBuilder(createVnfInstanceRequestInformation(VIID));
        VnfInstanceTopologyOperationInput input = builder.build();
        setReturnForSvcLogicServiceClientHasGraph(false);
        checkVnfInstanceTopologyOperation(input, "503", NO_SERVICE_LOGIC);
    }

    @Test
    public void vnfInstanceTopologyOperationInput_svcLogicClientExecuteThrowsSvcLogicException() throws Exception {
        VnfInstanceTopologyOperationInputBuilder builder = createVnfInstanceTopologyOperationInputBuilder(createVnfInstanceRequestInformation(VIID));
        VnfInstanceTopologyOperationInput input = builder.build();
        setReturnForSvcLogicServiceClientHasGraph(true);
        setMockVNFSDNSvcLogicServiceClientToThrowException(SvcLogicException.class);
        checkVnfInstanceTopologyOperation(input, "500", null);
    }

    @Test
    public void vnfInstanceTopologyOperationInput_svcLogicClientExecuteThrowsException() throws Exception {
        VnfInstanceTopologyOperationInputBuilder builder = createVnfInstanceTopologyOperationInputBuilder(createVnfInstanceRequestInformation(VIID));
        VnfInstanceTopologyOperationInput input = builder.build();

        setReturnForSvcLogicServiceClientHasGraph(true);
        setMockVNFSDNSvcLogicServiceClientToThrowException(Exception.class);
        checkVnfInstanceTopologyOperation(input, "500", null);
    }

    @Test
    public void vnfInstanceTopologyOperationInput_svcLogicClientExecuteReturnsNotNull() throws Exception {
        VnfInstanceTopologyOperationInputBuilder builder = createVnfInstanceTopologyOperationInputBuilder(createVnfInstanceRequestInformation(VIID));
        VnfInstanceTopologyOperationInput input = builder.build();

        Properties properties = prop().set(ERROR_CODE, "500")
                .set(ERROR_MESSAGE, ERROR_MESSAGE)
                .set(ACK_FINAL, "Y")
                .build();

        setReturnForSvcLogicServiceClientHasGraph(true);
        setReturnForSvcLogicServiceClientExecute(properties);
        checkVnfInstanceTopologyOperation(input, "500", ERROR_MESSAGE);
    }

    @Test
    public void vnfInstanceTopologyOperationInput_svcLogicClientExecuteReturnsNull() throws Exception {
        VnfInstanceTopologyOperationInputBuilder builder = createVnfInstanceTopologyOperationInputBuilder(createVnfInstanceRequestInformation(VIID));
        VnfInstanceTopologyOperationInput input = builder.build();

        setReturnForSvcLogicServiceClientHasGraph(true);
        setReturnForSvcLogicServiceClientExecute(null);
        String expectedErrorMsg = DataBrokerErrorMsgConfigurator.JAVA_LANG_RUNTIME_EXCEPTION
                + DataBrokerErrorMsgConfigurator.TRANSACTION_WRITE_ERROR;
        checkVnfInstanceTopologyOperation(input, "500", expectedErrorMsg);
    }

    @Test
    public void vnfInstanceTopologyOperationInput_NoErrorDuringTransactionWriting() throws Exception {
        VnfInstanceTopologyOperationInputBuilder builder = createVnfInstanceTopologyOperationInputBuilder(createVnfInstanceRequestInformation(VIID));
        VnfInstanceTopologyOperationInput input = builder.build();

        setReturnForSvcLogicServiceClientHasGraph(true);
        setReturnForSvcLogicServiceClientExecute(null);
        exceptionThrowerConfigurator.setShouldThrowExceptionDuringTransactionPut(false);
        checkVnfInstanceTopologyOperation(input, "200", null);
    }

    private void checkVnfInstanceTopologyOperation(VnfInstanceTopologyOperationInput input,
        String expectedResponseCode, String expectedResponseMessage) throws ExecutionException, InterruptedException {

        VnfInstanceTopologyOperationOutput output = executeVnfInstanceTopologyOperation(input);
        checkVnfInstanceTopologyOperationOutput(output, expectedResponseCode, expectedResponseMessage);
    }

    private VnfInstanceTopologyOperationOutput executeVnfInstanceTopologyOperation(
        VnfInstanceTopologyOperationInput input) throws ExecutionException, InterruptedException {
        return vnfapiProvider
                .vnfInstanceTopologyOperation(input)
                .get()
                .getResult();
    }

    private void checkVnfInstanceTopologyOperationOutput(VnfInstanceTopologyOperationOutput result,
        String expectedResponseCode, String expectedResponseMessage) {

        String expectedAckFinalIndicator = "Y";

        Assert.assertEquals(expectedResponseCode , result.getResponseCode());
        Assert.assertEquals(expectedResponseMessage, result.getResponseMessage());
        Assert.assertEquals(expectedAckFinalIndicator, result.getAckFinalIndicator());
    }

    private void setReturnForSvcLogicServiceClientHasGraph(Boolean returnValue) throws Exception{
        Mockito.when(mockVNFSDNSvcLogicServiceClient
                .hasGraph(Mockito.any(),Mockito.any(), Mockito.any(),Mockito.any()))
                .thenReturn(returnValue);
    }

    private void setReturnForSvcLogicServiceClientExecute(Properties properties) throws Exception{
        Mockito.when(mockVNFSDNSvcLogicServiceClient
                .execute(Mockito.any(),
                        Mockito.any(),
                        Mockito.any(),
                        Mockito.any(),
                        Mockito.any(VnfInstanceServiceDataBuilder.class),
                        Mockito.any()))
                .thenReturn(properties);
    }

    private void setMockVNFSDNSvcLogicServiceClientToThrowException(Class exceptionClass) throws Exception {
        Mockito.when(mockVNFSDNSvcLogicServiceClient
                .execute(Mockito.any(),
                        Mockito.any(),
                        Mockito.any(),
                        Mockito.any(),
                        Mockito.any(VnfInstanceServiceDataBuilder.class),
                        Mockito.any()))
                .thenThrow(exceptionClass.asSubclass(Throwable.class));
    }

    private VnfInstanceTopologyOperationInputBuilder createVnfInstanceTopologyOperationInputBuilder(VnfInstanceRequestInformation vnfInstanceRequestInformation) {
        VnfInstanceTopologyOperationInputBuilder builder = new VnfInstanceTopologyOperationInputBuilder();
        builder.setVnfInstanceRequestInformation(vnfInstanceRequestInformation);
        return builder;
    }



    private VnfInstanceRequestInformation createVnfInstanceRequestInformation(String vnfInstanceId) {
        return new VnfInstanceRequestInformationBuilder()
                .setVnfInstanceId(vnfInstanceId)
                .setVnfInstanceName(PRELOAD_NAME)
                .setVnfModelId(PRELOAD_TYPE)
                .build();
    }
}