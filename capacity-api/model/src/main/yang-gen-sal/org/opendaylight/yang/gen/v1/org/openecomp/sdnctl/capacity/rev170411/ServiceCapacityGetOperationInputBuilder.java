package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation;
import java.util.Objects;
import java.util.Collections;
import java.util.Map;

/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput
 *
 */
public class ServiceCapacityGetOperationInputBuilder implements Builder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput> {

    private CapacityReservationInformation _capacityReservationInformation;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>> augmentation = Collections.emptyMap();

    public ServiceCapacityGetOperationInputBuilder() {
    }
    public ServiceCapacityGetOperationInputBuilder(org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityReservationInformation arg) {
        this._capacityReservationInformation = arg.getCapacityReservationInformation();
    }

    public ServiceCapacityGetOperationInputBuilder(ServiceCapacityGetOperationInput base) {
        this._capacityReservationInformation = base.getCapacityReservationInformation();
        if (base instanceof ServiceCapacityGetOperationInputImpl) {
            ServiceCapacityGetOperationInputImpl impl = (ServiceCapacityGetOperationInputImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }

    /**
     *Set fields from given grouping argument. Valid argument is instance of one of following types:
     * <ul>
     * <li>org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityReservationInformation</li>
     * </ul>
     *
     * @param arg grouping object
     * @throws IllegalArgumentException if given argument is none of valid types
    */
    public void fieldsFrom(DataObject arg) {
        boolean isValidArg = false;
        if (arg instanceof org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityReservationInformation) {
            this._capacityReservationInformation = ((org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityReservationInformation)arg).getCapacityReservationInformation();
            isValidArg = true;
        }
        if (!isValidArg) {
            throw new IllegalArgumentException(
              "expected one of: [org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.CapacityReservationInformation] \n" +
              "but was: " + arg
            );
        }
    }

    public CapacityReservationInformation getCapacityReservationInformation() {
        return _capacityReservationInformation;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public ServiceCapacityGetOperationInputBuilder setCapacityReservationInformation(final CapacityReservationInformation value) {
        this._capacityReservationInformation = value;
        return this;
    }
    
    public ServiceCapacityGetOperationInputBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public ServiceCapacityGetOperationInputBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public ServiceCapacityGetOperationInput build() {
        return new ServiceCapacityGetOperationInputImpl(this);
    }

    private static final class ServiceCapacityGetOperationInputImpl implements ServiceCapacityGetOperationInput {

        @Override
        public java.lang.Class<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput.class;
        }

        private final CapacityReservationInformation _capacityReservationInformation;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>> augmentation = Collections.emptyMap();

        private ServiceCapacityGetOperationInputImpl(ServiceCapacityGetOperationInputBuilder base) {
            this._capacityReservationInformation = base.getCapacityReservationInformation();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public CapacityReservationInformation getCapacityReservationInformation() {
            return _capacityReservationInformation;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>> E getAugmentation(java.lang.Class<E> augmentationType) {
            if (augmentationType == null) {
                throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
            }
            return (E) augmentation.get(augmentationType);
        }

        private int hash = 0;
        private volatile boolean hashValid = false;
        
        @Override
        public int hashCode() {
            if (hashValid) {
                return hash;
            }
        
            final int prime = 31;
            int result = 1;
            result = prime * result + Objects.hashCode(_capacityReservationInformation);
            result = prime * result + Objects.hashCode(augmentation);
        
            hash = result;
            hashValid = true;
            return result;
        }

        @Override
        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DataObject)) {
                return false;
            }
            if (!org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput other = (org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput)obj;
            if (!Objects.equals(_capacityReservationInformation, other.getCapacityReservationInformation())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                ServiceCapacityGetOperationInputImpl otherImpl = (ServiceCapacityGetOperationInputImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityGetOperationInput>> e : augmentation.entrySet()) {
                    if (!e.getValue().equals(other.getAugmentation(e.getKey()))) {
                        return false;
                    }
                }
                // .. and give the other one the chance to do the same
                if (!obj.equals(this)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public java.lang.String toString() {
            java.lang.String name = "ServiceCapacityGetOperationInput [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_capacityReservationInformation != null) {
                builder.append("_capacityReservationInformation=");
                builder.append(_capacityReservationInformation);
            }
            final int builderLength = builder.length();
            final int builderAdditionalLength = builder.substring(name.length(), builderLength).length();
            if (builderAdditionalLength > 2 && !builder.substring(builderLength - 2, builderLength).equals(", ")) {
                builder.append(", ");
            }
            builder.append("augmentation=");
            builder.append(augmentation.values());
            return builder.append(']').toString();
        }
    }

}
