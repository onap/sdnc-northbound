package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput.Action;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationResourceList;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.Objects;
import java.util.List;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.service.capacity.release.operation.input.ReservationEntityList;
import java.util.Collections;
import java.util.Map;

/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput
 *
 */
public class ServiceCapacityReleaseOperationInputBuilder implements Builder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput> {

    private Action _action;
    private List<ReservationEntityList> _reservationEntityList;
    private List<ReservationResourceList> _reservationResourceList;
    private java.lang.String _serviceModel;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>> augmentation = Collections.emptyMap();

    public ServiceCapacityReleaseOperationInputBuilder() {
    }

    public ServiceCapacityReleaseOperationInputBuilder(ServiceCapacityReleaseOperationInput base) {
        this._action = base.getAction();
        this._reservationEntityList = base.getReservationEntityList();
        this._reservationResourceList = base.getReservationResourceList();
        this._serviceModel = base.getServiceModel();
        if (base instanceof ServiceCapacityReleaseOperationInputImpl) {
            ServiceCapacityReleaseOperationInputImpl impl = (ServiceCapacityReleaseOperationInputImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public Action getAction() {
        return _action;
    }
    
    public List<ReservationEntityList> getReservationEntityList() {
        return _reservationEntityList;
    }
    
    public List<ReservationResourceList> getReservationResourceList() {
        return _reservationResourceList;
    }
    
    public java.lang.String getServiceModel() {
        return _serviceModel;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public ServiceCapacityReleaseOperationInputBuilder setAction(final Action value) {
        this._action = value;
        return this;
    }
    
     
    public ServiceCapacityReleaseOperationInputBuilder setReservationEntityList(final List<ReservationEntityList> value) {
        this._reservationEntityList = value;
        return this;
    }
    
     
    public ServiceCapacityReleaseOperationInputBuilder setReservationResourceList(final List<ReservationResourceList> value) {
        this._reservationResourceList = value;
        return this;
    }
    
     
    public ServiceCapacityReleaseOperationInputBuilder setServiceModel(final java.lang.String value) {
        this._serviceModel = value;
        return this;
    }
    
    public ServiceCapacityReleaseOperationInputBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public ServiceCapacityReleaseOperationInputBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public ServiceCapacityReleaseOperationInput build() {
        return new ServiceCapacityReleaseOperationInputImpl(this);
    }

    private static final class ServiceCapacityReleaseOperationInputImpl implements ServiceCapacityReleaseOperationInput {

        @Override
        public java.lang.Class<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput.class;
        }

        private final Action _action;
        private final List<ReservationEntityList> _reservationEntityList;
        private final List<ReservationResourceList> _reservationResourceList;
        private final java.lang.String _serviceModel;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>> augmentation = Collections.emptyMap();

        private ServiceCapacityReleaseOperationInputImpl(ServiceCapacityReleaseOperationInputBuilder base) {
            this._action = base.getAction();
            this._reservationEntityList = base.getReservationEntityList();
            this._reservationResourceList = base.getReservationResourceList();
            this._serviceModel = base.getServiceModel();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public Action getAction() {
            return _action;
        }
        
        @Override
        public List<ReservationEntityList> getReservationEntityList() {
            return _reservationEntityList;
        }
        
        @Override
        public List<ReservationResourceList> getReservationResourceList() {
            return _reservationResourceList;
        }
        
        @Override
        public java.lang.String getServiceModel() {
            return _serviceModel;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + Objects.hashCode(_action);
            result = prime * result + Objects.hashCode(_reservationEntityList);
            result = prime * result + Objects.hashCode(_reservationResourceList);
            result = prime * result + Objects.hashCode(_serviceModel);
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
            if (!org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput other = (org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput)obj;
            if (!Objects.equals(_action, other.getAction())) {
                return false;
            }
            if (!Objects.equals(_reservationEntityList, other.getReservationEntityList())) {
                return false;
            }
            if (!Objects.equals(_reservationResourceList, other.getReservationResourceList())) {
                return false;
            }
            if (!Objects.equals(_serviceModel, other.getServiceModel())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                ServiceCapacityReleaseOperationInputImpl otherImpl = (ServiceCapacityReleaseOperationInputImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.ServiceCapacityReleaseOperationInput>> e : augmentation.entrySet()) {
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
            java.lang.String name = "ServiceCapacityReleaseOperationInput [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
            if (_action != null) {
                builder.append("_action=");
                builder.append(_action);
                builder.append(", ");
            }
            if (_reservationEntityList != null) {
                builder.append("_reservationEntityList=");
                builder.append(_reservationEntityList);
                builder.append(", ");
            }
            if (_reservationResourceList != null) {
                builder.append("_reservationResourceList=");
                builder.append(_reservationResourceList);
                builder.append(", ");
            }
            if (_serviceModel != null) {
                builder.append("_serviceModel=");
                builder.append(_serviceModel);
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
