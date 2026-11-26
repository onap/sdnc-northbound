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
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import org.junit.Test;
import org.onap.sdnc.northbound.GenericResourceApiProvider.Iso8601Util;

public class Iso8601UtilTest {

    @Test
    public void should_format_in_utc_with_milliseconds() {
        assertEquals("2026-11-26T09:05:03.007Z", Iso8601Util.format(Instant.parse("2026-11-26T09:05:03.007999Z")));
        assertEquals("1970-01-01T00:00:00.000Z", Iso8601Util.format(Instant.EPOCH));
    }

    @Test
    public void should_format_now() {
        assertTrue(Iso8601Util.now().matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
    }
}
