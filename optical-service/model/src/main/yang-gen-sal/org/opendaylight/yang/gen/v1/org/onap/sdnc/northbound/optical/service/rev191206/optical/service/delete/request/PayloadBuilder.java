package org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.delete.request;
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
 * Class that builds {@link PayloadBuilder} instances.
 *
 * @see PayloadBuilder
 *
 */
public class PayloadBuilder implements Builder<Payload> {

    private String _serviceName;


    Map<Class<? extends Augmentation<Payload>>, Augmentation<Payload>> augmentation = Collections.emptyMap();

    public PayloadBuilder() {
    }

    public PayloadBuilder(Payload base) {
        this._serviceName = base.getServiceName();
        if (base instanceof PayloadImpl) {
            PayloadImpl impl = (PayloadImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            Map<Class<? extends Augmentation<Payload>>, Augmentation<Payload>> aug =((AugmentationHolder<Payload>) base).augmentations();
            if (!aug.isEmpty()) {
                this.augmentation = new HashMap<>(aug);
            }
        }
    }


    public String getServiceName() {
        return _serviceName;
    }
    
    @SuppressWarnings({ "unchecked", "checkstyle:methodTypeParameterName"})
    public <E$$ extends Augmentation<Payload>> E$$ augmentation(Class<E$$> augmentationType) {
        return (E$$) augmentation.get(CodeHelpers.nonNullValue(augmentationType, "augmentationType"));
    }

    
    public PayloadBuilder setServiceName(final String value) {
        this._serviceName = value;
        return this;
    }
    
    public PayloadBuilder addAugmentation(Class<? extends Augmentation<Payload>> augmentationType, Augmentation<Payload> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public PayloadBuilder removeAugmentation(Class<? extends Augmentation<Payload>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public Payload build() {
        return new PayloadImpl(this);
    }

    private static final class PayloadImpl implements Payload {
    
        private final String _serviceName;
    
        private Map<Class<? extends Augmentation<Payload>>, Augmentation<Payload>> augmentation = Collections.emptyMap();
    
        PayloadImpl(PayloadBuilder base) {
            this._serviceName = base.getServiceName();
            this.augmentation = ImmutableMap.copyOf(base.augmentation);
        }
    
        @Override
        public Class<Payload> getImplementedInterface() {
            return Payload.class;
        }
    
        @Override
        public String getServiceName() {
            return _serviceName;
        }
        
        @SuppressWarnings({ "unchecked", "checkstyle:methodTypeParameterName"})
        @Override
        public <E$$ extends Augmentation<Payload>> E$$ augmentation(Class<E$$> augmentationType) {
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
            result = prime * result + Objects.hashCode(_serviceName);
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
            if (!Payload.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            Payload other = (Payload)obj;
            if (!Objects.equals(_serviceName, other.getServiceName())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                PayloadImpl otherImpl = (PayloadImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<Class<? extends Augmentation<Payload>>, Augmentation<Payload>> e : augmentation.entrySet()) {
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
            final MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper("Payload");
            CodeHelpers.appendValue(helper, "_serviceName", _serviceName);
            CodeHelpers.appendValue(helper, "augmentation", augmentation.values());
            return helper.toString();
        }
    }
}
