package org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206;
import java.lang.String;
import org.eclipse.jdt.annotation.Nullable;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;

/**
 *
 * <p>
 * This class represents the following YANG schema fragment defined in module <b>opticalservice</b>
 * <pre>
 * grouping optical-service-response {
 *   leaf response-code {
 *     type string;
 *   }
 *   leaf response-message {
 *     type string;
 *   }
 *   leaf ack-final-indicator {
 *     type string;
 *   }
 * }
 * </pre>The schema path to identify an instance is
 * <i>opticalservice/optical-service-response</i>
 *
 */
public interface OpticalServiceResponse
    extends
    DataObject
{



    public static final QName QNAME = $YangModuleInfoImpl.qnameOf("optical-service-response");

    /**
     * @return <code>java.lang.String</code> <code>responseCode</code>, or <code>null</code> if not present
     */
    @Nullable String getResponseCode();
    
    /**
     * @return <code>java.lang.String</code> <code>responseMessage</code>, or <code>null</code> if not present
     */
    @Nullable String getResponseMessage();
    
    /**
     * @return <code>java.lang.String</code> <code>ackFinalIndicator</code>, or <code>null</code> if not present
     */
    @Nullable String getAckFinalIndicator();

}

