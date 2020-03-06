package org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206;
import org.eclipse.jdt.annotation.Nullable;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.delete.request.Payload;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;

/**
 *
 * <p>
 * This class represents the following YANG schema fragment defined in module <b>opticalservice</b>
 * <pre>
 * grouping optical-service-delete-request {
 *   uses request-source-g;
 *   container payload {
 *     leaf service-name {
 *       type string;
 *     }
 *   }
 * }
 * </pre>The schema path to identify an instance is
 * <i>opticalservice/optical-service-delete-request</i>
 *
 */
public interface OpticalServiceDeleteRequest
    extends
    DataObject,
    RequestSourceG
{



    public static final QName QNAME = $YangModuleInfoImpl.qnameOf("optical-service-delete-request");

    /**
     * @return <code>org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.delete.request.Payload</code> <code>payload</code>, or <code>null</code> if not present
     */
    @Nullable Payload getPayload();

}

