/*-
 * ============LICENSE_START=======================================================
 * ONAP : SDN-C
 * ================================================================================
 * Copyright (C) 2020 FUJITSU Intellectual Property. All rights
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.onap.ccsdk.sli.core.sli.SvcLogicException;
import org.onap.ccsdk.sli.core.sli.provider.MdsalHelper;
import org.onap.ccsdk.sli.core.sli.provider.SvcLogicService;
import org.onap.sdnc.northbound.OpticalServiceClient;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteOutputBuilder;

public class OpticalServiceClientTest {

	SvcLogicService mockSvcLogicService;
	String module = "test-module";
	String rpc = "test-rpc";
	String version = "test-version";
	String mode = "test-mode";
	Properties localProp = new Properties();

	@Before
	public void setUp() throws Exception {
		mockSvcLogicService = mock(SvcLogicService.class);
		when(mockSvcLogicService.hasGraph(module, rpc, version, mode)).thenReturn(true);
	}

	@Test
	public void testOpticalServiceClientConstructor() {
		OpticalServiceClient opticalServiceClient = new OpticalServiceClient(mockSvcLogicService);
		assertNotNull(opticalServiceClient);
	}

	@Test
	public void testHasGraph() throws SvcLogicException {
		OpticalServiceClient opticalServiceClient = new OpticalServiceClient(mockSvcLogicService);
		boolean result = opticalServiceClient.hasGraph(module, rpc, version, mode);
		assertTrue(result);
	}

	@Test
	public void testExecuteSvcLogicStatusFailureCreate() throws SvcLogicException {
		OpticalServiceCreateOutputBuilder serviceData = mock(OpticalServiceCreateOutputBuilder.class);
		Properties parms = mock(Properties.class);
		SvcLogicService svcLogicService = mock(SvcLogicService.class);
		Properties properties = new Properties();
		properties.setProperty("SvcLogic.status", "failure");
		when(svcLogicService.execute(module, rpc, version, mode, properties)).thenReturn(properties);
		OpticalServiceClient sliClient = new OpticalServiceClient(svcLogicService);
		Properties prop = sliClient.execute(module, rpc, version, mode, serviceData, properties);
		assertTrue(prop != null);
	}

	@Test
	public void testExecuteSvcLogicStatusFailureDelete() throws SvcLogicException {
		OpticalServiceDeleteOutputBuilder serviceData = mock(OpticalServiceDeleteOutputBuilder.class);
		Properties parms = mock(Properties.class);
		SvcLogicService svcLogicService = mock(SvcLogicService.class);
		Properties properties = new Properties();
		properties.setProperty("SvcLogic.status", "failure");
		when(svcLogicService.execute(module, rpc, version, mode, properties)).thenReturn(properties);
		OpticalServiceClient sliClient = new OpticalServiceClient(svcLogicService);
		Properties prop = sliClient.execute(module, rpc, version, mode, serviceData, properties);
		assertTrue(prop != null);
	}
}