package org.onap.sdnc.northbound.gra.springboot.controllers.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(PreloadDataId.class)
@Table(name="PRELOAD_DATA")
public class PreloadData {
    @Id
    private String preloadId;

    @Id
    private String preloadType;

    private String preloadData;

    public PreloadData() {
        this.preloadId = "";
        this.preloadType = "";
        this.preloadData = "";
    }

    public PreloadData(String preloadId, String preloadType, String preloadData) {
        this.preloadId = preloadId;
        this.preloadType = preloadType;
        this.preloadData = preloadData;
    }

    public String getPreloadId() {
        return preloadId;
    }

    public void setPreloadId(String preloadId) {
        this.preloadId = preloadId;
    }

    public String getPreloadType() {
        return preloadType;
    }

    public void setPreloadType(String preloadType) {
        this.preloadType = preloadType;
    }

    public String getPreloadData() {
        return preloadData;
    }

    public void setPreloadData(String preloadData) {
        this.preloadData = preloadData;
    }
}
