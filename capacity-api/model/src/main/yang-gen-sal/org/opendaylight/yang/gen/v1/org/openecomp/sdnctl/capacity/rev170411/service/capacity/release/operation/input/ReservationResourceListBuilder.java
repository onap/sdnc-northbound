package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.Objects;
import java.util.Collections;
import java.util.Map;

/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList
 *
 */
public class ReservationResourceListBuilder implements Builder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList> {

    private java.lang.String _endpointPosition;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>> augmentation = Collections.emptyMap();

    public ReservationResourceListBuilder() {
    }

    public ReservationResourceListBuilder(ReservationResourceList base) {
        this._endpointPosition = base.getEndpointPosition();
        if (base instanceof ReservationResourceListImpl) {
            ReservationResourceListImpl impl = (ReservationResourceListImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public java.lang.String getEndpointPosition() {
        return _endpointPosition;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public ReservationResourceListBuilder setEndpointPosition(final java.lang.String value) {
        this._endpointPosition = value;
        return this;
    }
    
    public ReservationResourceListBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public ReservationResourceListBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public ReservationResourceList build() {
        return new ReservationResourceListImpl(this);
    }

    private static final class ReservationResourceListImpl implements ReservationResourceList {

        @Override
        public java.lang.Class<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList.class;
        }

        private final java.lang.String _endpointPosition;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>> augmentation = Collections.emptyMap();

        private ReservationResourceListImpl(ReservationResourceListBuilder base) {
            this._endpointPosition = base.getEndpointPosition();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public java.lang.String getEndpointPosition() {
            return _endpointPosition;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + Objects.hashCode(_endpointPosition);
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
            if (!org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList other = (org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList)obj;
            if (!Objects.equals(_endpointPosition, other.getEndpointPosition())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                ReservationResourceListImpl otherImpl = (ReservationResourceListImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList>> e : augmentation.entrySet()) {
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
            java.lang.String name = "ReservationResourceList [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_endpointPosition != null) {
                builder.append("_endpointPosition=");
                builder.append(_endpointPosition);
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
