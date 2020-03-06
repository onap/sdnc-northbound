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
 * grouping service-end-g {
 *   leaf port-id {
 *     type string;
 *   }
 *   leaf port-name {
 *     type string;
 *   }
 * }
 * </pre>The schema path to identify an instance is
 * <i>opticalservice/service-end-g</i>
 *
 */
public interface ServiceEndG
    extends
    DataObject
{



    public static final QName QNAME = $YangModuleInfoImpl.qnameOf("service-end-g");

    /**
     * @return <code>java.lang.String</code> <code>portId</code>, or <code>null</code> if not present
     */
    @Nullable String getPortId();
    
    /**
     * @return <code>java.lang.String</code> <code>portName</code>, or <code>null</code> if not present
     */
    @Nullable String getPortName();

}

