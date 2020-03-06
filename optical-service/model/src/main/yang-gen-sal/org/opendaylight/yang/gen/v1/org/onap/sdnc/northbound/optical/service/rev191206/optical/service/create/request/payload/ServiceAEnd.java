package org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.create.request.payload;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.$YangModuleInfoImpl;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.ServiceEndG;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.create.request.Payload;
import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.common.QName;

/**
 *
 * <p>
 * This class represents the following YANG schema fragment defined in module <b>opticalservice</b>
 * <pre>
 * container service-a-end {
 *   uses service-end-g;
 * }
 * </pre>The schema path to identify an instance is
 * <i>opticalservice/optical-service-create-request/payload/service-a-end</i>
 *
 * <p>To create instances of this class use {@link ServiceAEndBuilder}.
 * @see ServiceAEndBuilder
 *
 */
public interface ServiceAEnd
    extends
    ChildOf<Payload>,
    Augmentable<ServiceAEnd>,
    ServiceEndG
{



    public static final QName QNAME = $YangModuleInfoImpl.qnameOf("service-a-end");


}

