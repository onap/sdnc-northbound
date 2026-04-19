/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2026 Deutsche Telekom AG. All rights reserved.
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.junit.Test;
import org.mockito.Mockito;
import org.opendaylight.mdsal.binding.api.RpcProviderService;
import org.opendaylight.mdsal.binding.testutils.DataBrokerTestModule;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.*;
import org.opendaylight.yangtools.binding.Rpc;
import org.opendaylight.yangtools.concepts.Registration;

public class GenericResourceApiProviderRpcRegistrationTest {

    /** Every RPC type that GENERIC-RESOURCE-API exposes. */
    private static final Set<Class<? extends Rpc<?, ?>>> EXPECTED_RPC_TYPES = Set.of(
            BrgTopologyOperation.class,
            CollectPerformanceData.class,
            ConnectionAttachmentTopologyOperation.class,
            ContrailRouteTopologyOperation.class,
            GenericConfigurationNotification.class,
            GenericConfigurationTopologyOperation.class,
            GetpathsegmentTopologyOperation.class,
            NetworkTopologyOperation.class,
            PnfTopologyOperation.class,
            PolicyUpdateNotifyOperation.class,
            PortMirrorTopologyOperation.class,
            PreloadNetworkTopologyOperation.class,
            PreloadVfModuleTopologyOperation.class,
            SecurityZoneTopologyOperation.class,
            ServiceTopologyOperation.class,
            TunnelxconnTopologyOperation.class,
            VfModuleTopologyOperation.class,
            VnfTopologyOperation.class
    );

    @Test
    public void providerShouldRegisterAllRpcsOnConstruction() {
        Registration mockRegistration = mock(Registration.class);
        AtomicReference<Rpc<?, ?>[]> captured = new AtomicReference<>();

        // Use defaultAnswer to let the default varargs method run, but intercept
        // the abstract ClassToInstanceMap overload it delegates to.
        RpcProviderService rpcProviderService = mock(RpcProviderService.class,
                withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS));

        // Stub the abstract ClassToInstanceMap overload (which the default varargs
        // method delegates to) to capture the RPCs and return a mock Registration.
        Mockito.doAnswer(invocation -> {
            com.google.common.collect.ClassToInstanceMap<?> map = invocation.getArgument(0);
            @SuppressWarnings("unchecked")
            Rpc<?, ?>[] rpcs = map.values().toArray(new Rpc<?, ?>[0]);
            captured.set(rpcs);
            return mockRegistration;
        }).when(rpcProviderService).registerRpcImplementations(
                Mockito.any(com.google.common.collect.ClassToInstanceMap.class));

        GenericResourceApiSvcLogicServiceClient svcLogicClient =
                mock(GenericResourceApiSvcLogicServiceClient.class);

        GenericResourceApiProvider provider = new GenericResourceApiProvider(
                DataBrokerTestModule.dataBroker(),
                rpcProviderService,
                svcLogicClient);

        Rpc<?, ?>[] registeredRpcs = captured.get();
        assertNotNull("registerRpcImplementations was never called — "
                + "the provider failed to register its RPCs", registeredRpcs);

        Set<Class<?>> registeredTypes = Arrays.stream(registeredRpcs)
                .map(Rpc::implementedInterface)
                .collect(Collectors.toSet());

        for (Class<? extends Rpc<?, ?>> expectedType : EXPECTED_RPC_TYPES) {
            assertTrue(
                    "RPC " + expectedType.getSimpleName() + " was not registered. "
                    + "Ensure GenericResourceApiProvider.initialize() registers it "
                    + "via rpcService.registerRpcImplementations().",
                    registeredTypes.contains(expectedType));
        }

        try {
            provider.close();
        } catch (Exception e) {
            // ignore cleanup errors in test
        }
    }
}
