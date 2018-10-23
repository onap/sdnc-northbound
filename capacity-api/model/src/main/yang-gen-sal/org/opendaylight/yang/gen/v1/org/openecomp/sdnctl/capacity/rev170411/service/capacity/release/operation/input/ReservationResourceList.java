package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput;
import org.opendaylight.yangtools.yang.binding.Augmentable;

/**
 * <p>This class represents the following YANG schema fragment defined in module <b>CAPACITY-API</b>
 * <pre>
 * list reservation-resource-list {
 *     leaf endpoint-position {
 *         type string;
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>CAPACITY-API/service-capacity-release-operation/input/reservation-resource-list</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceListBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceListBuilder
 *
 *
 */
public interface ReservationResourceList
    extends
    ChildOf<ServiceCapacityReleaseOperationInput>,
    Augmentable<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:openecomp:sdnctl:capacity",
        "2017-04-11", "reservation-resource-list").intern();

    /**
     * endpoint_position from resource_rule or range_rule tables
     *
     *
     *
     * @return <code>java.lang.String</code> <code>endpointPosition</code>, or <code>null</code> if not present
     */
    java.lang.String getEndpointPosition();

}

