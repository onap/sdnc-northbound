package org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.create.request;
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
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.create.request.payload.ServiceAEnd;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.create.request.payload.ServiceZEnd;
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

    private String _codingFunc;
    private String _domainType;
    private ServiceAEnd _serviceAEnd;
    private String _serviceLayer;
    private String _serviceName;
    private String _serviceProtocol;
    private String _serviceRate;
    private ServiceZEnd _serviceZEnd;


    Map<Class<? extends Augmentation<Payload>>, Augmentation<Payload>> augmentation = Collections.emptyMap();

    public PayloadBuilder() {
    }

    public PayloadBuilder(Payload base) {
        this._codingFunc = base.getCodingFunc();
        this._domainType = base.getDomainType();
        this._serviceAEnd = base.getServiceAEnd();
        this._serviceLayer = base.getServiceLayer();
        this._serviceName = base.getServiceName();
        this._serviceProtocol = base.getServiceProtocol();
        this._serviceRate = base.getServiceRate();
        this._serviceZEnd = base.getServiceZEnd();
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


    public String getCodingFunc() {
        return _codingFunc;
    }
    
    public String getDomainType() {
        return _domainType;
    }
    
    public ServiceAEnd getServiceAEnd() {
        return _serviceAEnd;
    }
    
    public String getServiceLayer() {
        return _serviceLayer;
    }
    
    public String getServiceName() {
        return _serviceName;
    }
    
    public String getServiceProtocol() {
        return _serviceProtocol;
    }
    
    public String getServiceRate() {
        return _serviceRate;
    }
    
    public ServiceZEnd getServiceZEnd() {
        return _serviceZEnd;
    }
    
    @SuppressWarnings({ "unchecked", "checkstyle:methodTypeParameterName"})
    public <E$$ extends Augmentation<Payload>> E$$ augmentation(Class<E$$> augmentationType) {
        return (E$$) augmentation.get(CodeHelpers.nonNullValue(augmentationType, "augmentationType"));
    }

    
    public PayloadBuilder setCodingFunc(final String value) {
        this._codingFunc = value;
        return this;
    }
    
    public PayloadBuilder setDomainType(final String value) {
        this._domainType = value;
        return this;
    }
    
    public PayloadBuilder setServiceAEnd(final ServiceAEnd value) {
        this._serviceAEnd = value;
        return this;
    }
    
    public PayloadBuilder setServiceLayer(final String value) {
        this._serviceLayer = value;
        return this;
    }
    
    public PayloadBuilder setServiceName(final String value) {
        this._serviceName = value;
        return this;
    }
    
    public PayloadBuilder setServiceProtocol(final String value) {
        this._serviceProtocol = value;
        return this;
    }
    
    public PayloadBuilder setServiceRate(final String value) {
        this._serviceRate = value;
        return this;
    }
    
    public PayloadBuilder setServiceZEnd(final ServiceZEnd value) {
        this._serviceZEnd = value;
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
    
        private final String _codingFunc;
        private final String _domainType;
        private final ServiceAEnd _serviceAEnd;
        private final String _serviceLayer;
        private final String _serviceName;
        private final String _serviceProtocol;
        private final String _serviceRate;
        private final ServiceZEnd _serviceZEnd;
    
        private Map<Class<? extends Augmentation<Payload>>, Augmentation<Payload>> augmentation = Collections.emptyMap();
    
        PayloadImpl(PayloadBuilder base) {
            this._codingFunc = base.getCodingFunc();
            this._domainType = base.getDomainType();
            this._serviceAEnd = base.getServiceAEnd();
            this._serviceLayer = base.getServiceLayer();
            this._serviceName = base.getServiceName();
            this._serviceProtocol = base.getServiceProtocol();
            this._serviceRate = base.getServiceRate();
            this._serviceZEnd = base.getServiceZEnd();
            this.augmentation = ImmutableMap.copyOf(base.augmentation);
        }
    
        @Override
        public Class<Payload> getImplementedInterface() {
            return Payload.class;
        }
    
        @Override
        public String getCodingFunc() {
            return _codingFunc;
        }
        
        @Override
        public String getDomainType() {
            return _domainType;
        }
        
        @Override
        public ServiceAEnd getServiceAEnd() {
            return _serviceAEnd;
        }
        
        @Override
        public String getServiceLayer() {
            return _serviceLayer;
        }
        
        @Override
        public String getServiceName() {
            return _serviceName;
        }
        
        @Override
        public String getServiceProtocol() {
            return _serviceProtocol;
        }
        
        @Override
        public String getServiceRate() {
            return _serviceRate;
        }
        
        @Override
        public ServiceZEnd getServiceZEnd() {
            return _serviceZEnd;
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
            result = prime * result + Objects.hashCode(_codingFunc);
            result = prime * result + Objects.hashCode(_domainType);
            result = prime * result + Objects.hashCode(_serviceAEnd);
            result = prime * result + Objects.hashCode(_serviceLayer);
            result = prime * result + Objects.hashCode(_serviceName);
            result = prime * result + Objects.hashCode(_serviceProtocol);
            result = prime * result + Objects.hashCode(_serviceRate);
            result = prime * result + Objects.hashCode(_serviceZEnd);
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
            if (!Objects.equals(_codingFunc, other.getCodingFunc())) {
                return false;
            }
            if (!Objects.equals(_domainType, other.getDomainType())) {
                return false;
            }
            if (!Objects.equals(_serviceAEnd, other.getServiceAEnd())) {
                return false;
            }
            if (!Objects.equals(_serviceLayer, other.getServiceLayer())) {
                return false;
            }
            if (!Objects.equals(_serviceName, other.getServiceName())) {
                return false;
            }
            if (!Objects.equals(_serviceProtocol, other.getServiceProtocol())) {
                return false;
            }
            if (!Objects.equals(_serviceRate, other.getServiceRate())) {
                return false;
            }
            if (!Objects.equals(_serviceZEnd, other.getServiceZEnd())) {
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
            CodeHelpers.appendValue(helper, "_codingFunc", _codingFunc);
            CodeHelpers.appendValue(helper, "_domainType", _domainType);
            CodeHelpers.appendValue(helper, "_serviceAEnd", _serviceAEnd);
            CodeHelpers.appendValue(helper, "_serviceLayer", _serviceLayer);
            CodeHelpers.appendValue(helper, "_serviceName", _serviceName);
            CodeHelpers.appendValue(helper, "_serviceProtocol", _serviceProtocol);
            CodeHelpers.appendValue(helper, "_serviceRate", _serviceRate);
            CodeHelpers.appendValue(helper, "_serviceZEnd", _serviceZEnd);
            CodeHelpers.appendValue(helper, "augmentation", augmentation.values());
            return helper.toString();
        }
    }
}
