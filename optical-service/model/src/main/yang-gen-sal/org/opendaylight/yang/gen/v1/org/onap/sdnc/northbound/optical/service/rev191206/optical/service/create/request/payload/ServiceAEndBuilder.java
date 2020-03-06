package org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.create.request.payload;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.opendaylight.yangtools.concepts.Builder;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.CodeHelpers;
import org.opendaylight.yangtools.yang.binding.DataObject;

/**
 * Class that builds {@link ServiceAEndBuilder} instances.
 *
 * @see ServiceAEndBuilder
 *
 */
public class ServiceAEndBuilder implements Builder<ServiceAEnd> {

    private String _portId;
    private String _portName;


    Map<Class<? extends Augmentation<ServiceAEnd>>, Augmentation<ServiceAEnd>> augmentation = Collections.emptyMap();

    public ServiceAEndBuilder() {
    }
    public ServiceAEndBuilder(org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.ServiceEndG arg) {
        this._portId = arg.getPortId();
        this._portName = arg.getPortName();
    }

    public ServiceAEndBuilder(ServiceAEnd base) {
        this._portId = base.getPortId();
        this._portName = base.getPortName();
        if (base instanceof ServiceAEndImpl) {
            ServiceAEndImpl impl = (ServiceAEndImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            Map<Class<? extends Augmentation<ServiceAEnd>>, Augmentation<ServiceAEnd>> aug =((AugmentationHolder<ServiceAEnd>) base).augmentations();
            if (!aug.isEmpty()) {
                this.augmentation = new HashMap<>(aug);
            }
        }
    }

    /**
     * Set fields from given grouping argument. Valid argument is instance of one of following types:
     * <ul>
     * <li>org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.ServiceEndG</li>
     * </ul>
     *
     * @param arg grouping object
     * @throws IllegalArgumentException if given argument is none of valid types
    */
    public void fieldsFrom(DataObject arg) {
        boolean isValidArg = false;
        if (arg instanceof org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.ServiceEndG) {
            this._portId = ((org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.ServiceEndG)arg).getPortId();
            this._portName = ((org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.ServiceEndG)arg).getPortName();
            isValidArg = true;
        }
        CodeHelpers.validValue(isValidArg, arg, "[org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.ServiceEndG]");
    }

    public String getPortId() {
        return _portId;
    }
    
    public String getPortName() {
        return _portName;
    }
    
    @SuppressWarnings({ "unchecked", "checkstyle:methodTypeParameterName"})
    public <E$$ extends Augmentation<ServiceAEnd>> E$$ augmentation(Class<E$$> augmentationType) {
        return (E$$) augmentation.get(CodeHelpers.nonNullValue(augmentationType, "augmentationType"));
    }

    
    public ServiceAEndBuilder setPortId(final String value) {
        this._portId = value;
        return this;
    }
    
    public ServiceAEndBuilder setPortName(final String value) {
        this._portName = value;
        return this;
    }
    
    public ServiceAEndBuilder addAugmentation(Class<? extends Augmentation<ServiceAEnd>> augmentationType, Augmentation<ServiceAEnd> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public ServiceAEndBuilder removeAugmentation(Class<? extends Augmentation<ServiceAEnd>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public ServiceAEnd build() {
        return new ServiceAEndImpl(this);
    }

    private static final class ServiceAEndImpl implements ServiceAEnd {
    
        private final String _portId;
        private final String _portName;
    
        private Map<Class<? extends Augmentation<ServiceAEnd>>, Augmentation<ServiceAEnd>> augmentation = Collections.emptyMap();
    
        ServiceAEndImpl(ServiceAEndBuilder base) {
            this._portId = base.getPortId();
            this._portName = base.getPortName();
            this.augmentation = ImmutableMap.copyOf(base.augmentation);
        }
    
        @Override
        public Class<ServiceAEnd> getImplementedInterface() {
            return ServiceAEnd.class;
        }
    
        @Override
        public String getPortId() {
            return _portId;
        }
        
        @Override
        public String getPortName() {
            return _portName;
        }
        
        @SuppressWarnings({ "unchecked", "checkstyle:methodTypeParameterName"})
        @Override
        public <E$$ extends Augmentation<ServiceAEnd>> E$$ augmentation(Class<E$$> augmentationType) {
            return (E$$) augmentation.get(CodeHelpers.nonNullValue(augmentationType, "augmentationType"));
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
            result = prime * result + Objects.hashCode(_portId);
            result = prime * result + Objects.hashCode(_portName);
            result = prime * result + Objects.hashCode(augmentation);
        
            hash = result;
            hashValid = true;
            return result;
        }
    
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DataObject)) {
                return false;
            }
            if (!ServiceAEnd.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            ServiceAEnd other = (ServiceAEnd)obj;
            if (!Objects.equals(_portId, other.getPortId())) {
                return false;
            }
            if (!Objects.equals(_portName, other.getPortName())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                ServiceAEndImpl otherImpl = (ServiceAEndImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<Class<? extends Augmentation<ServiceAEnd>>, Augmentation<ServiceAEnd>> e : augmentation.entrySet()) {
                    if (!e.getValue().equals(other.augmentation(e.getKey()))) {
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
        public String toString() {
            final MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper("ServiceAEnd");
            CodeHelpers.appendValue(helper, "_portId", _portId);
            CodeHelpers.appendValue(helper, "_portName", _portName);
            CodeHelpers.appendValue(helper, "augmentation", augmentation.values());
            return helper.toString();
        }
    }
}
