package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation;
import org.opendaylight.yangtools.yang.binding.Augmentable;

/**
 * <p>This class represents the following YANG schema fragment defined in module <b>CAPACITY-API</b>
 * <pre>
 * list reservation-resource-list {
 *     leaf resource-name {
 *         type string;
 *     }
 *     leaf endpoint-position {
 *         type string;
 *     }
 *     leaf resource-share-group {
 *         type string;
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>CAPACITY-API/capacity-reservation-information/capacity-reservation-information/reservation-resource-list</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationResourceListBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationResourceListBuilder
 *
 *
 */
public interface ReservationResourceList
    extends
    ChildOf<CapacityReservationInformation>,
    Augmentable<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationResourceList>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:openecomp:sdnctl:capacity",
        "2017-04-11", "reservation-resource-list").intern();

    /**
     * resource_name or range_name from resource_rule or range_rule tables
     *
     *
     *
     * @return <code>java.lang.String</code> <code>resourceName</code>, or <code>null</code> if not present
     */
    java.lang.String getResourceName();
    
    /**
     * endpoint_position from resource_rule or range_rule tables
     *
     *
     *
     * @return <code>java.lang.String</code> <code>endpointPosition</code>, or <code>null</code> if not present
     */
    java.lang.String getEndpointPosition();
    
    /**
     * resource_share_group to share same resource range or limit value
     *
     *
     *
     * @return <code>java.lang.String</code> <code>resourceShareGroup</code>, or <code>null</code> if not present
     */
    java.lang.String getResourceShareGroup();

}

