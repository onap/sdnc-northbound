package org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206;
import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yangtools.yang.binding.RpcInput;
import org.opendaylight.yangtools.yang.common.QName;

/**
 *
 * <p>
 * This class represents the following YANG schema fragment defined in module <b>opticalservice</b>
 * <pre>
 * input {
 *   uses optical-service-create-request;
 * }
 * </pre>The schema path to identify an instance is
 * <i>opticalservice/optical-service-create/input</i>
 *
 * <p>To create instances of this class use {@link OpticalServiceCreateInputBuilder}.
 * @see OpticalServiceCreateInputBuilder
 *
 */
public interface OpticalServiceCreateInput
    extends
    OpticalServiceCreateRequest,
    RpcInput,
    Augmentable<OpticalServiceCreateInput>
{



    public static final QName QNAME = $YangModuleInfoImpl.qnameOf("input");


}

