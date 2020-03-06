package org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206;
import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yangtools.yang.binding.RpcOutput;
import org.opendaylight.yangtools.yang.common.QName;

/**
 *
 * <p>
 * This class represents the following YANG schema fragment defined in module <b>opticalservice</b>
 * <pre>
 * output {
 *   uses optical-service-response;
 * }
 * </pre>The schema path to identify an instance is
 * <i>opticalservice/optical-service-create/output</i>
 *
 * <p>To create instances of this class use {@link OpticalServiceCreateOutputBuilder}.
 * @see OpticalServiceCreateOutputBuilder
 *
 */
public interface OpticalServiceCreateOutput
    extends
    OpticalServiceResponse,
    RpcOutput,
    Augmentable<OpticalServiceCreateOutput>
{



    public static final QName QNAME = $YangModuleInfoImpl.qnameOf("output");


}

