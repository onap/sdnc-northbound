package org.onap.sdnc.vnfapi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class VnfSdnUtilTest {

    private VnfSdnUtil vnfSdnUtil;

    @Before public void setUp() throws Exception {
        vnfSdnUtil = new VnfSdnUtil();
    }

    @Test public void loadProperties() throws Exception {
        vnfSdnUtil.loadProperties();
        Object properties = Whitebox.getInternalState(VnfSdnUtil.class, "properties");
        Assert.assertNotNull(properties);
    }
}
