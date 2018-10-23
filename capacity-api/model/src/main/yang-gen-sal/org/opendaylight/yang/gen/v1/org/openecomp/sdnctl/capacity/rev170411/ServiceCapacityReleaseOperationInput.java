package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList;
import org.opendaylight.yangtools.yang.common.QName;
import java.util.List;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList;
import org.opendaylight.yangtools.yang.binding.Augmentable;

/**
 * <p>This class represents the following YANG schema fragment defined in module <b>CAPACITY-API</b>
 * <pre>
 * container input {
 *     leaf service-model {
 *         type string;
 *     }
 *     leaf action {
 *         type enumeration;
 *     }
 *     list reservation-entity-list {
 *         key "reservation-entity-id";
 *         leaf reservation-entity-id {
 *             type string;
 *         }
 *         leaf reservation-entity-type {
 *             type string;
 *         }
 *     }
 *     list reservation-resource-list {
 *         leaf endpoint-position {
 *             type string;
 *         }
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>CAPACITY-API/service-capacity-release-operation/input</i>
 *
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInputBuilder}.
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInputBuilder
 *
 */
public interface ServiceCapacityReleaseOperationInput
    extends
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>
{


    public enum Action {
        Cancel(0, "cancel"),
        
        Disconnect(1, "disconnect"),
        
        Activate(2, "activate")
        ;
    
        private static final java.util.Map<java.lang.Integer, Action> VALUE_MAP;
    
        static {
            final com.google.common.collect.ImmutableMap.Builder<java.lang.Integer, Action> b = com.google.common.collect.ImmutableMap.builder();
            for (Action enumItem : Action.values()) {
                b.put(enumItem.value, enumItem);
            }
    
            VALUE_MAP = b.build();
        }
    
        private final java.lang.String name;
        private final int value;
    
        private Action(int value, java.lang.String name) {
            this.value = value;
            this.name = name;
        }
    
        /**
         * Returns the name of the enumeration item as it is specified in the input yang.
         *
         * @return the name of the enumeration item as it is specified in the input yang
         */
        public java.lang.String getName() {
            return name;
        }
    
        /**
         * @return integer value
         */
        public int getIntValue() {
            return value;
        }
    
        /**
         * @param valueArg integer value
         * @return corresponding Action item
         */
        public static Action forValue(int valueArg) {
            return VALUE_MAP.get(valueArg);
        }
    }

    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("org:openecomp:sdnctl:capacity",
        "2017-04-11", "input").intern();

    /**
     * Could be ADIOD, DHV
     *
     *
     *
     * @return <code>java.lang.String</code> <code>serviceModel</code>, or <code>null</code> if not present
     */
    java.lang.String getServiceModel();
    
    /**
     * Could be cancel for cancel pending, disconnect for active 
     *
     *
     *
     * @return <code>org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput.Action</code> <code>action</code>, or <code>null</code> if not present
     */
    Action getAction();
    
    /**
     * @return <code>java.util.List</code> <code>reservationEntityList</code>, or <code>null</code> if not present
     */
    List<ReservationEntityList> getReservationEntityList();
    
    /**
     * @return <code>java.util.List</code> <code>reservationResourceList</code>, or <code>null</code> if not present
     */
    List<ReservationResourceList> getReservationResourceList();

}

