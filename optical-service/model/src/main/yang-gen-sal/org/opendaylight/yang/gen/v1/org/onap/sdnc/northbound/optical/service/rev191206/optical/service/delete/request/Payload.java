package org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.delete.request;
import java.lang.String;
import org.eclipse.jdt.annotation.Nullable;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.$YangModuleInfoImpl;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteRequest;
import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.common.QName;

/**
 *
 * <p>
 * This class represents the following YANG schema fragment defined in module <b>opticalservice</b>
 * <pre>
 * container payload {
 *   leaf service-name {
 *     type string;
 *   }
 * }
 * </pre>The schema path to identify an instance is
 * <i>opticalservice/optical-service-delete-request/payload</i>
 *
 * <p>To create instances of this class use {@link PayloadBuilder}.
 * @see PayloadBuilder
 *
 */
public interface Payload
    extends
    ChildOf<OpticalServiceDeleteRequest>,
    Augmentable<Payload>
{



    public static final QName QNAME = $YangModuleInfoImpl.qnameOf("payload");

    /**
     * @return <code>java.lang.String</code> <code>serviceName</code>, or <code>null</code> if not present
     */
    @Nullable String getServiceName();

}

