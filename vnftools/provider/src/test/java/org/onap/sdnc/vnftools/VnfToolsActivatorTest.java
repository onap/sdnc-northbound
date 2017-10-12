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
package org.onap.sdnc.vnftools;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@SuppressWarnings("unchecked")
public class VnfToolsActivatorTest {
    private BundleContext mockContext = mock(BundleContext.class);
    private ServiceRegistration mockSR = mock(ServiceRegistration.class);

    private VnfToolsActivator activator;

    @Before
    public void setUp() throws Exception {
        Mockito.doReturn(mockSR).when(mockContext).registerService((String[]) any(), anyString(), any());

        activator = new VnfToolsActivator();
    }

    @Test
    public void testStartWithDefaultPropDir() throws Exception {
        activator.start(mockContext);
        List<ServiceRegistration> internalList =
                (List<ServiceRegistration>) Whitebox.getInternalState(activator, "registrations");
        Assert.assertTrue("Should have one entry in registrations list", internalList.size() == 1);
    }

    @Test
    public void testStartWithDefaultPropFile() throws Exception {
        createPropFile();
        activator.start(mockContext);
        List<ServiceRegistration> internalList =
                (List<ServiceRegistration>) Whitebox.getInternalState(activator, "registrations");
        Assert.assertTrue("Should have one entry in registrations list", internalList.size() == 1);
    }

    private void createPropFile() throws IOException {
        // create the directory path
        String[] directories = VnfToolsActivator.DEFAULT_PROP_DIR.split("/");
        StringBuilder pathBuilder = new StringBuilder();
        for (String aDir : directories) {
            pathBuilder.append(aDir).append("/");
            String path = pathBuilder.toString();
            File dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdir()) {
                    Assert.fail(String.format("Cannot create path %s", path));
                }
            }
        }
        // create the property file
        String filename = String.format("%s%s",
                VnfToolsActivator.DEFAULT_PROP_DIR, VnfToolsActivator.VNFTOOLS_PROP_VAR);
        File file = new File(filename);
        if (!file.canRead()) {
            if (!file.setReadable(true, true)) {
                Assert.fail(String.format("Cannot enabled read for file %s", filename));
            }
        }
    }

    @Test
    public void testStop() throws Exception {
        ServiceRegistration mockSR2 = mock(ServiceRegistration.class);
        List<ServiceRegistration> srList = new LinkedList<>();
        srList.add(mockSR);
        srList.add(mockSR2);
        Whitebox.setInternalState(activator, "registrations", srList);

        activator.stop(mockContext);

        Mockito.verify(mockSR, times(1)).unregister();
        Mockito.verify(mockSR2, times(1)).unregister();
        List<ServiceRegistration> internalList =
                (List<ServiceRegistration>) Whitebox.getInternalState(activator, "registrations");
        Assert.assertTrue("Should have empty registrations list", internalList.isEmpty());
    }

}