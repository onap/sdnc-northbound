package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.binding.Augmentable;

/**
 * <p>This class represents the following YANG schema fragment defined in module <b>CAPACITY-API</b>
 * <pre>
 * container output {
 *     leaf response-code {
 *         type string;
 *     }
 *     leaf response-message {
 *         type string;
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>CAPACITY-API/service-capacity-release-operation/output</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationOutputBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationOutputBuilder
 *
 */
public interface ServiceCapacityReleaseOperationOutput
    extends
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationOutput>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:openecomp:sdnctl:capacity",
        "2017-04-11", "output").intern();

    /**
     * 200 for no error, &amp;gt;=400 on error
     *
     *
     *
     * @return <code>java.lang.String</code> <code>responseCode</code>, or <code>null</code> if not present
     */
    java.lang.String getResponseCode();
    
    /**
     * Error message in case of an error
     *
     *
     *
     * @return <code>java.lang.String</code> <code>responseMessage</code>, or <code>null</code> if not present
     */
    java.lang.String getResponseMessage();

}

