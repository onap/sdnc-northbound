/*-
 * ============LICENSE_START=======================================================
 * ONAP : SDN-C
 * ================================================================================
 * Copyright (C) 2019-2020 Fujitsu Limited Intellectual Property. All rights
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

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

import java.io.Writer;
import java.io.StringWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Collectors;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true, property = {"alias=/asyncNotification", "servlet-name=AsyncNotification"})
public class AsyncNotificationHandling extends HttpServlet implements Servlet {

    private static final long serialVersionUID = 1L;

    private final Logger LOG = LoggerFactory.getLogger(AsyncNotificationHandling.class);

    private static final String PARAMETER_NAME = "parameter-name";
    private static final String STRING_VALUE = "string-value";
    private static final String REQUEST_ID = "request-id";
    private static final String SVC_REQUEST_ID = "svc-request-id";
    private static final String RESPONSE_CODE = "response-code";
    private static final String ACK_FINAL_INDICATOR = "ack-final-indicator";
    private static final String CONFIGURATION_RESPONSE = "configuration-response-common";
    private static final String PROPERTIES_PATH = "/opt/onap/sdnc/data/properties/";
    private static final String TEMPLATE_NAME = "rpc-message-sliapi-execute-async.vt";
    private static final String UTF_8 = "UTF-8";

    /**
     * Handles async request for different domain controllers
     */

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.info("Reached async servlet");
        BufferedReader requestInput = null;
        try {
            requestInput = request.getReader();
        } catch (Exception e) {
            LOG.error("Unable to read input from request", e);
        }

        if (requestInput == null) {
            return;
        }
        String json = requestInput.lines().collect(Collectors.joining());
        LOG.info("Async Request payload {}", json);
        if (json == null) {
            return;
        }

        JSONObject jsonObj = new JSONObject(json);
        JSONObject input = jsonObj.getJSONObject("input");

        if (input.has(CONFIGURATION_RESPONSE)) {
            JSONObject payloadString = null;

            try {
                payloadString = input.getJSONObject(CONFIGURATION_RESPONSE);
                String rpcMessageBody = buildAsyncNotifRPCMsgRoadm(payloadString);
                LOG.debug("rpc message body {}", rpcMessageBody);
                invokeRPC(rpcMessageBody);
            } catch (Exception e) {
                LOG.error("Unable to build rpc message body::", e);
            }
        } else {
            LOG.info("Async request received for wrong domain");
        }

    }

    private void invokeRPC(String rpcMsgbody) {
        try {
	    Properties baseProperties = new Properties(); 
            baseProperties.load(new FileInputStream(new File("/opt/onap/sdnc/data/properties/optical-service-dg.properties")));
            String odlUrlBase = baseProperties.getProperty("odlUrlBase");
            String odlUser = baseProperties.getProperty("controller.user");
            String odlPassword = baseProperties.getProperty("controller.pwd");
            String sdncEndpoint = baseProperties.getProperty("sdncEndpoint");

            if ((odlUrlBase != null) && (odlUrlBase.length() > 0)) {
                SdncOdlConnection conn =
                        SdncOdlConnection.newInstance(odlUrlBase + "/" + sdncEndpoint, odlUser, odlPassword);

                conn.send("POST", "application/json", rpcMsgbody);
            } else {
                LOG.info("POST message body would be:\n {}", rpcMsgbody);
            }
        } catch (Exception e) {
            LOG.error("Unable to process message", e);
        }

    }

    private String buildAsyncNotifRPCMsgRoadm(JSONObject payloadString) throws IOException {
        VelocityEngine velocityEngine = new VelocityEngine();

        Properties props = new Properties();
        props.put("file.resource.loader.path", PROPERTIES_PATH);
        velocityEngine.init(props);

        String sliParameters = "sli_parameters";

        JSONArray sliParametersArray = new JSONArray();

        VelocityContext context = new VelocityContext();

        context.put("rpc_name", "handle-async-notif");
        String requestId = payloadString.getString(SVC_REQUEST_ID);
        String responseCode = payloadString.getString(RESPONSE_CODE);
        String ackIndicator = payloadString.getString(ACK_FINAL_INDICATOR);

        sliParametersArray.put(new JSONObject().put(PARAMETER_NAME, REQUEST_ID).put(STRING_VALUE, requestId));
        sliParametersArray.put(new JSONObject().put(PARAMETER_NAME, RESPONSE_CODE).put(STRING_VALUE, responseCode));
        sliParametersArray
                .put(new JSONObject().put(PARAMETER_NAME, ACK_FINAL_INDICATOR).put(STRING_VALUE, ackIndicator));

        context.put(sliParameters, sliParametersArray);

        Writer writer = new StringWriter();
        velocityEngine.mergeTemplate(TEMPLATE_NAME, UTF_8, context, writer);
        writer.flush();

        return writer.toString();

    }

}
