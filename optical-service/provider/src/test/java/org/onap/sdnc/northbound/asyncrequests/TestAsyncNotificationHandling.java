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
package org.onap.sdnc.northbound.asyncrequests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.Writer;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import org.onap.sdnc.northbound.asyncrequests.AsyncNotificationHandling;

@RunWith(MockitoJUnitRunner.class)
public class TestAsyncNotificationHandling {

	@Test
	public void testServlet() throws Exception {

		String payloadContent = "{\r\n" + "   \"input\":{\r\n" + "      \"configuration-response-common\":{\r\n"
				+ "         \"svc-request-id\":\"c5868dab-c569-464c-b8bf-7e5addbbec75\",\r\n"
				+ "         \"response-code\":\"200\",\r\n" + "         \"ack-final-indicator\":\"Y\"\r\n"
				+ "      },\r\n" + "      \"service-identifiers\":{\r\n"
				+ "         \"service-name\":\"demoservice1\",\r\n"
				+ "         \"common-id\":\"4430ab31-abeb-4d78-8f51-6e7aba8ee779\"\r\n" + "      }\r\n" + "   }\r\n"
				+ "}";
		HttpServletRequest request = mock(HttpServletRequest.class);
		Mockito.when(request.getReader()).thenReturn(new BufferedReader(new StringReader(payloadContent)));

		HttpServletResponse response = mock(HttpServletResponse.class);
		AsyncNotificationHandling asyncNotification = new AsyncNotificationHandling();
		asyncNotification.doPost(request, response);

	}

}
