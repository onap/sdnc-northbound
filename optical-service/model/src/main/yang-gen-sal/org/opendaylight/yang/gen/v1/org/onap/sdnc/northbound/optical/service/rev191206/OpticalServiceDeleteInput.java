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
 *   uses optical-service-delete-request;
 * }
 * </pre>The schema path to identify an instance is
 * <i>opticalservice/optical-service-delete/input</i>
 *
 * <p>To create instances of this class use {@link OpticalServiceDeleteInputBuilder}.
 * @see OpticalServiceDeleteInputBuilder
 *
 */
public interface OpticalServiceDeleteInput
    extends
    OpticalServiceDeleteRequest,
    RpcInput,
    Augmentable<OpticalServiceDeleteInput>
{



    public static final QName QNAME = $YangModuleInfoImpl.qnameOf("input");


}

