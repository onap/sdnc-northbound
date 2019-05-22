package org.onap.sdnc.vnfapi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Iso8601Util {

    private DateFormat df;
    private static Iso8601Util instance = new Iso8601Util();

    public static Iso8601Util getInstance() {
        return instance;
    }

    private Iso8601Util() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
    }

    public String now() {
        return df.format(new Date());
    }
}
