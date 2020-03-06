package org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206;
import java.lang.String;
import org.eclipse.jdt.annotation.Nullable;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.create.request.Payload;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;

/**
 *
 * <p>
 * This class represents the following YANG schema fragment defined in module <b>opticalservice</b>
 * <pre>
 * grouping optical-service-create-request {
 *   uses request-source-g;
 *   leaf global-customer-id {
 *     type string;
 *   }
 *   leaf service-type {
 *     type string;
 *   }
 *   leaf service-id {
 *     type string;
 *   }
 *   leaf notification-url {
 *     type string;
 *   }
 *   container payload {
 *     leaf domain-type {
 *       type string;
 *     }
 *     leaf service-name {
 *       type string;
 *     }
 *     leaf service-rate {
 *       type string;
 *     }
 *     leaf service-protocol {
 *       type string;
 *     }
 *     leaf coding-func {
 *       type string;
 *     }
 *     container service-a-end {
 *       uses service-end-g;
 *     }
 *     container service-z-end {
 *       uses service-end-g;
 *     }
 *     leaf service-layer {
 *       type string;
 *     }
 *   }
 * }
 * </pre>The schema path to identify an instance is
 * <i>opticalservice/optical-service-create-request</i>
 *
 */
public interface OpticalServiceCreateRequest
    extends
    DataObject,
    RequestSourceG
{



    public static final QName QNAME = $YangModuleInfoImpl.qnameOf("optical-service-create-request");

    /**
     * @return <code>java.lang.String</code> <code>globalCustomerId</code>, or <code>null</code> if not present
     */
    @Nullable String getGlobalCustomerId();
    
    /**
     * @return <code>java.lang.String</code> <code>serviceType</code>, or <code>null</code> if not present
     */
    @Nullable String getServiceType();
    
    /**
     * @return <code>java.lang.String</code> <code>serviceId</code>, or <code>null</code> if not present
     */
    @Nullable String getServiceId();
    
    /**
     * @return <code>java.lang.String</code> <code>notificationUrl</code>, or <code>null</code> if not present
     */
    @Nullable String getNotificationUrl();
    
    /**
     * @return <code>org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.create.request.Payload</code> <code>payload</code>, or <code>null</code> if not present
     */
    @Nullable Payload getPayload();

}

