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
 * grouping request-source-g {
 *   leaf request-id {
 *     type string;
 *   }
 *   leaf source {
 *     type string;
 *   }
 * }
 * </pre>The schema path to identify an instance is
 * <i>opticalservice/request-source-g</i>
 *
 */
public interface RequestSourceG
    extends
    DataObject
{



    public static final QName QNAME = $YangModuleInfoImpl.qnameOf("request-source-g");

    /**
     * @return <code>java.lang.String</code> <code>requestId</code>, or <code>null</code> if not present
     */
    @Nullable String getRequestId();
    
    /**
     * @return <code>java.lang.String</code> <code>source</code>, or <code>null</code> if not present
     */
    @Nullable String getSource();

}

