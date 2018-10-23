package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.binding.Augmentable;

/**
 * <p>This class represents the following YANG schema fragment defined in module <b>CAPACITY-API</b>
 * <pre>
 * container input {
 *     container capacity-reservation-information {
 *         leaf service-model {
 *             type string;
 *         }
 *         list reservation-entity-list {
 *             key "reservation-entity-id";
 *             leaf reservation-entity-type {
 *                 type string;
 *             }
 *             leaf reservation-entity-id {
 *                 type string;
 *             }
 *             list reservation-entity-data {
 *                 key "name";
 *                 leaf name {
 *                     type string;
 *                 }
 *                 leaf value {
 *                     type string;
 *                 }
 *             }
 *         }
 *         list reservation-target-list {
 *             key "reservation-target-id";
 *             leaf reservation-target-type {
 *                 type string;
 *             }
 *             leaf reservation-target-id {
 *                 type string;
 *             }
 *             list reservation-target-data {
 *                 key "name";
 *                 leaf name {
 *                     type string;
 *                 }
 *                 leaf value {
 *                     type string;
 *                 }
 *             }
 *         }
 *         list reservation-resource-list {
 *             leaf resource-name {
 *                 type string;
 *             }
 *             leaf endpoint-position {
 *                 type string;
 *             }
 *             leaf resource-share-group {
 *                 type string;
 *             }
 *         }
 *     }
 *     uses capacity-reservation-information;
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>CAPACITY-API/service-capacity-get-operation/input</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInputBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInputBuilder
 *
 */
public interface ServiceCapacityGetOperationInput
    extends
    CapacityReservationInformation,
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:openecomp:sdnctl:capacity",
        "2017-04-11", "input").intern();


}

