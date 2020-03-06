package org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.create.request;
import java.lang.String;
import org.eclipse.jdt.annotation.Nullable;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.$YangModuleInfoImpl;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateRequest;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.create.request.payload.ServiceAEnd;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.create.request.payload.ServiceZEnd;
import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.common.QName;

/**
 *
 * <p>
 * This class represents the following YANG schema fragment defined in module <b>opticalservice</b>
 * <pre>
 * container payload {
 *   leaf domain-type {
 *     type string;
 *   }
 *   leaf service-name {
 *     type string;
 *   }
 *   leaf service-rate {
 *     type string;
 *   }
 *   leaf service-protocol {
 *     type string;
 *   }
 *   leaf coding-func {
 *     type string;
 *   }
 *   container service-a-end {
 *     uses service-end-g;
 *   }
 *   container service-z-end {
 *     uses service-end-g;
 *   }
 *   leaf service-layer {
 *     type string;
 *   }
 * }
 * </pre>The schema path to identify an instance is
 * <i>opticalservice/optical-service-create-request/payload</i>
 *
 * <p>To create instances of this class use {@link PayloadBuilder}.
 * @see PayloadBuilder
 *
 */
public interface Payload
    extends
    ChildOf<OpticalServiceCreateRequest>,
    Augmentable<Payload>
{



    public static final QName QNAME = $YangModuleInfoImpl.qnameOf("payload");

    /**
     * @return <code>java.lang.String</code> <code>domainType</code>, or <code>null</code> if not present
     */
    @Nullable String getDomainType();
    
    /**
     * @return <code>java.lang.String</code> <code>serviceName</code>, or <code>null</code> if not present
     */
    @Nullable String getServiceName();
    
    /**
     * @return <code>java.lang.String</code> <code>serviceRate</code>, or <code>null</code> if not present
     */
    @Nullable String getServiceRate();
    
    /**
     * @return <code>java.lang.String</code> <code>serviceProtocol</code>, or <code>null</code> if not present
     */
    @Nullable String getServiceProtocol();
    
    /**
     * @return <code>java.lang.String</code> <code>codingFunc</code>, or <code>null</code> if not present
     */
    @Nullable String getCodingFunc();
    
    /**
     * @return <code>org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.create.request.payload.ServiceAEnd</code> <code>serviceAEnd</code>, or <code>null</code> if not present
     */
    @Nullable ServiceAEnd getServiceAEnd();
    
    /**
     * @return <code>org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.create.request.payload.ServiceZEnd</code> <code>serviceZEnd</code>, or <code>null</code> if not present
     */
    @Nullable ServiceZEnd getServiceZEnd();
    
    /**
     * @return <code>java.lang.String</code> <code>serviceLayer</code>, or <code>null</code> if not present
     */
    @Nullable String getServiceLayer();

}

