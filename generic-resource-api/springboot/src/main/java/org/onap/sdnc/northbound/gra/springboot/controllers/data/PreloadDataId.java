/*-
 * ============LICENSE_START=======================================================
 * ONAP - SDNC
 * ================================================================================
 * Copyright (C) 2020 AT&T Intellectual Property. All rights reserved.
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
