package org.onap.sdnc.northbound.gra.springboot.controllers.data;

import afu.org.checkerframework.checker.oigj.qual.O;

import java.io.Serializable;

public class PreloadDataId implements Serializable {

    private String preloadId;
    private String preloadType;

    public PreloadDataId() {
        preloadId = "";
        preloadType = "";
    }

    public PreloadDataId(String preloadId, String preloadType) {
        this.preloadId = preloadId;
        this.preloadType = preloadType;
    }

    @Override
    public int hashCode() {
        return preloadId.hashCode() + preloadType.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PreloadDataId) {
            PreloadDataId that = (PreloadDataId) obj;
            return(this.preloadId.equals(that.preloadId) && that.preloadType.equals(this.preloadType));
        } else {
            return false;
        }
    }
}
