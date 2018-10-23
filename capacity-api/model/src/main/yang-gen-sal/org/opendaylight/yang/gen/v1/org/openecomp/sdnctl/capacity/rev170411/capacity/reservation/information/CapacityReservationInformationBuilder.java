package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationResourceList;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetList;
import java.util.Objects;
import java.util.List;
import org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationEntityList;
import java.util.Collections;
import java.util.Map;

/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation} instances.
 *
 * @see org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation
 *
 */
public class CapacityReservationInformationBuilder implements Builder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation> {

    private List<ReservationEntityList> _reservationEntityList;
    private List<ReservationResourceList> _reservationResourceList;
    private List<ReservationTargetList> _reservationTargetList;
    private java.lang.String _serviceModel;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>> augmentation = Collections.emptyMap();

    public CapacityReservationInformationBuilder() {
    }

    public CapacityReservationInformationBuilder(CapacityReservationInformation base) {
        this._reservationEntityList = base.getReservationEntityList();
        this._reservationResourceList = base.getReservationResourceList();
        this._reservationTargetList = base.getReservationTargetList();
        this._serviceModel = base.getServiceModel();
        if (base instanceof CapacityReservationInformationImpl) {
            CapacityReservationInformationImpl impl = (CapacityReservationInformationImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public List<ReservationEntityList> getReservationEntityList() {
        return _reservationEntityList;
    }
    
    public List<ReservationResourceList> getReservationResourceList() {
        return _reservationResourceList;
    }
    
    public List<ReservationTargetList> getReservationTargetList() {
        return _reservationTargetList;
    }
    
    public java.lang.String getServiceModel() {
        return _serviceModel;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

     
    public CapacityReservationInformationBuilder setReservationEntityList(final List<ReservationEntityList> value) {
        this._reservationEntityList = value;
        return this;
    }
    
     
    public CapacityReservationInformationBuilder setReservationResourceList(final List<ReservationResourceList> value) {
        this._reservationResourceList = value;
        return this;
    }
    
     
    public CapacityReservationInformationBuilder setReservationTargetList(final List<ReservationTargetList> value) {
        this._reservationTargetList = value;
        return this;
    }
    
     
    public CapacityReservationInformationBuilder setServiceModel(final java.lang.String value) {
        this._serviceModel = value;
        return this;
    }
    
    public CapacityReservationInformationBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public CapacityReservationInformationBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public CapacityReservationInformation build() {
        return new CapacityReservationInformationImpl(this);
    }

    private static final class CapacityReservationInformationImpl implements CapacityReservationInformation {

        @Override
        public java.lang.Class<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation.class;
        }

        private final List<ReservationEntityList> _reservationEntityList;
        private final List<ReservationResourceList> _reservationResourceList;
        private final List<ReservationTargetList> _reservationTargetList;
        private final java.lang.String _serviceModel;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>> augmentation = Collections.emptyMap();

        private CapacityReservationInformationImpl(CapacityReservationInformationBuilder base) {
            this._reservationEntityList = base.getReservationEntityList();
            this._reservationResourceList = base.getReservationResourceList();
            this._reservationTargetList = base.getReservationTargetList();
            this._serviceModel = base.getServiceModel();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
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
        public List<ReservationTargetList> getReservationTargetList() {
            return _reservationTargetList;
        }
        
        @Override
        public java.lang.String getServiceModel() {
            return _serviceModel;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + Objects.hashCode(_reservationEntityList);
            result = prime * result + Objects.hashCode(_reservationResourceList);
            result = prime * result + Objects.hashCode(_reservationTargetList);
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
            if (!org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation other = (org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation)obj;
            if (!Objects.equals(_reservationEntityList, other.getReservationEntityList())) {
                return false;
            }
            if (!Objects.equals(_reservationResourceList, other.getReservationResourceList())) {
                return false;
            }
            if (!Objects.equals(_reservationTargetList, other.getReservationTargetList())) {
                return false;
            }
            if (!Objects.equals(_serviceModel, other.getServiceModel())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                CapacityReservationInformationImpl otherImpl = (CapacityReservationInformationImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>>, Augmentation<org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.CapacityReservationInformation>> e : augmentation.entrySet()) {
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
            java.lang.String name = "CapacityReservationInformation [";
            java.lang.StringBuilder builder = new java.lang.StringBuilder (name);
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
            if (_reservationTargetList != null) {
                builder.append("_reservationTargetList=");
                builder.append(_reservationTargetList);
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
