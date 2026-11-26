/*-
 * ============LICENSE_START=======================================================
 * ONAP : SDN-C
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.BrgTopologyOperation;
import org.opendaylight.yangtools.binding.Rpc;
import org.opendaylight.yangtools.binding.runtime.api.BindingRuntimeContext;
import org.opendaylight.yangtools.binding.runtime.spi.BindingRuntimeHelpers;
import org.opendaylight.yangtools.yang.common.QNameModule;

@RunWith(MockitoJUnitRunner.class)
public class RpcRegistrationTest extends GenericResourceApiProviderTest {

    @Test
    public void should_register_every_rpc_of_the_yang_module() throws Exception {
        BindingRuntimeContext context = BindingRuntimeHelpers.createRuntimeContext(BrgTopologyOperation.class);
        QNameModule module = BrgTopologyOperation.QNAME.getModule();

        Set<String> expected = context.modelContext().findModule(module).orElseThrow().getRpcs().stream()
            .map(rpc -> context.getTypes().schemaTreeChild(rpc.getQName()).javaType().getName())
            .collect(Collectors.toSet());

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Rpc<?, ?>[]> captor = ArgumentCaptor.forClass(Rpc[].class);
        verify(mockRpcProviderService).registerRpcImplementations(captor.capture());
        Set<String> registered = Arrays.stream(captor.getValue())
            .map(rpc -> rpc.implementedInterface().getSimpleName())
            .collect(Collectors.toSet());

        assertEquals(19, expected.size());
        assertEquals("RPCs not registered", Set.of(), Sets.difference(expected, registered));
        assertEquals(expected, registered);
    }
}
