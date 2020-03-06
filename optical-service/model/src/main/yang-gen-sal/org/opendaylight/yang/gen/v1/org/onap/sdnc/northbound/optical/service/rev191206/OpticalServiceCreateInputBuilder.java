package org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206;
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
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.create.request.Payload;
import org.opendaylight.yangtools.concepts.Builder;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.CodeHelpers;
import org.opendaylight.yangtools.yang.binding.DataObject;

/**
 * Class that builds {@link OpticalServiceCreateInputBuilder} instances.
 *
 * @see OpticalServiceCreateInputBuilder
 *
 */
public class OpticalServiceCreateInputBuilder implements Builder<OpticalServiceCreateInput> {

    private String _globalCustomerId;
    private String _notificationUrl;
    private Payload _payload;
    private String _requestId;
    private String _serviceId;
    private String _serviceType;
    private String _source;


    Map<Class<? extends Augmentation<OpticalServiceCreateInput>>, Augmentation<OpticalServiceCreateInput>> augmentation = Collections.emptyMap();

    public OpticalServiceCreateInputBuilder() {
    }
    public OpticalServiceCreateInputBuilder(org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateRequest arg) {
        this._globalCustomerId = arg.getGlobalCustomerId();
        this._serviceType = arg.getServiceType();
        this._serviceId = arg.getServiceId();
        this._notificationUrl = arg.getNotificationUrl();
        this._payload = arg.getPayload();
        this._requestId = arg.getRequestId();
        this._source = arg.getSource();
    }
    public OpticalServiceCreateInputBuilder(org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.RequestSourceG arg) {
        this._requestId = arg.getRequestId();
        this._source = arg.getSource();
    }

    public OpticalServiceCreateInputBuilder(OpticalServiceCreateInput base) {
        this._globalCustomerId = base.getGlobalCustomerId();
        this._notificationUrl = base.getNotificationUrl();
        this._payload = base.getPayload();
        this._requestId = base.getRequestId();
        this._serviceId = base.getServiceId();
        this._serviceType = base.getServiceType();
        this._source = base.getSource();
        if (base instanceof OpticalServiceCreateInputImpl) {
            OpticalServiceCreateInputImpl impl = (OpticalServiceCreateInputImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            Map<Class<? extends Augmentation<OpticalServiceCreateInput>>, Augmentation<OpticalServiceCreateInput>> aug =((AugmentationHolder<OpticalServiceCreateInput>) base).augmentations();
            if (!aug.isEmpty()) {
                this.augmentation = new HashMap<>(aug);
            }
        }
    }

    /**
     * Set fields from given grouping argument. Valid argument is instance of one of following types:
     * <ul>
     * <li>org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.RequestSourceG</li>
     * <li>org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateRequest</li>
     * </ul>
     *
     * @param arg grouping object
     * @throws IllegalArgumentException if given argument is none of valid types
    */
    public void fieldsFrom(DataObject arg) {
        boolean isValidArg = false;
        if (arg instanceof org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.RequestSourceG) {
            this._requestId = ((org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.RequestSourceG)arg).getRequestId();
            this._source = ((org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.RequestSourceG)arg).getSource();
            isValidArg = true;
        }
        if (arg instanceof org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateRequest) {
            this._globalCustomerId = ((org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateRequest)arg).getGlobalCustomerId();
            this._serviceType = ((org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateRequest)arg).getServiceType();
            this._serviceId = ((org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateRequest)arg).getServiceId();
            this._notificationUrl = ((org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateRequest)arg).getNotificationUrl();
            this._payload = ((org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateRequest)arg).getPayload();
            isValidArg = true;
        }
        CodeHelpers.validValue(isValidArg, arg, "[org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.RequestSourceG, org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceCreateRequest]");
    }

    public String getGlobalCustomerId() {
        return _globalCustomerId;
    }
    
    public String getNotificationUrl() {
        return _notificationUrl;
    }
    
    public Payload getPayload() {
        return _payload;
    }
    
    public String getRequestId() {
        return _requestId;
    }
    
    public String getServiceId() {
        return _serviceId;
    }
    
    public String getServiceType() {
        return _serviceType;
    }
    
    public String getSource() {
        return _source;
    }
    
    @SuppressWarnings({ "unchecked", "checkstyle:methodTypeParameterName"})
    public <E$$ extends Augmentation<OpticalServiceCreateInput>> E$$ augmentation(Class<E$$> augmentationType) {
        return (E$$) augmentation.get(CodeHelpers.nonNullValue(augmentationType, "augmentationType"));
    }

    
    public OpticalServiceCreateInputBuilder setGlobalCustomerId(final String value) {
        this._globalCustomerId = value;
        return this;
    }
    
    public OpticalServiceCreateInputBuilder setNotificationUrl(final String value) {
        this._notificationUrl = value;
        return this;
    }
    
    public OpticalServiceCreateInputBuilder setPayload(final Payload value) {
        this._payload = value;
        return this;
    }
    
    public OpticalServiceCreateInputBuilder setRequestId(final String value) {
        this._requestId = value;
        return this;
    }
    
    public OpticalServiceCreateInputBuilder setServiceId(final String value) {
        this._serviceId = value;
        return this;
    }
    
    public OpticalServiceCreateInputBuilder setServiceType(final String value) {
        this._serviceType = value;
        return this;
    }
    
    public OpticalServiceCreateInputBuilder setSource(final String value) {
        this._source = value;
        return this;
    }
    
    public OpticalServiceCreateInputBuilder addAugmentation(Class<? extends Augmentation<OpticalServiceCreateInput>> augmentationType, Augmentation<OpticalServiceCreateInput> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public OpticalServiceCreateInputBuilder removeAugmentation(Class<? extends Augmentation<OpticalServiceCreateInput>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public OpticalServiceCreateInput build() {
        return new OpticalServiceCreateInputImpl(this);
    }

    private static final class OpticalServiceCreateInputImpl implements OpticalServiceCreateInput {
    
        private final String _globalCustomerId;
        private final String _notificationUrl;
        private final Payload _payload;
        private final String _requestId;
        private final String _serviceId;
        private final String _serviceType;
        private final String _source;
    
        private Map<Class<? extends Augmentation<OpticalServiceCreateInput>>, Augmentation<OpticalServiceCreateInput>> augmentation = Collections.emptyMap();
    
        OpticalServiceCreateInputImpl(OpticalServiceCreateInputBuilder base) {
            this._globalCustomerId = base.getGlobalCustomerId();
            this._notificationUrl = base.getNotificationUrl();
            this._payload = base.getPayload();
            this._requestId = base.getRequestId();
            this._serviceId = base.getServiceId();
            this._serviceType = base.getServiceType();
            this._source = base.getSource();
            this.augmentation = ImmutableMap.copyOf(base.augmentation);
        }
    
        @Override
        public Class<OpticalServiceCreateInput> getImplementedInterface() {
            return OpticalServiceCreateInput.class;
        }
    
        @Override
        public String getGlobalCustomerId() {
            return _globalCustomerId;
        }
        
        @Override
        public String getNotificationUrl() {
            return _notificationUrl;
        }
        
        @Override
        public Payload getPayload() {
            return _payload;
        }
        
        @Override
        public String getRequestId() {
            return _requestId;
        }
        
        @Override
        public String getServiceId() {
            return _serviceId;
        }
        
        @Override
        public String getServiceType() {
            return _serviceType;
        }
        
        @Override
        public String getSource() {
            return _source;
        }
        
        @SuppressWarnings({ "unchecked", "checkstyle:methodTypeParameterName"})
        @Override
        public <E$$ extends Augmentation<OpticalServiceCreateInput>> E$$ augmentation(Class<E$$> augmentationType) {
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
            result = prime * result + Objects.hashCode(_globalCustomerId);
            result = prime * result + Objects.hashCode(_notificationUrl);
            result = prime * result + Objects.hashCode(_payload);
            result = prime * result + Objects.hashCode(_requestId);
            result = prime * result + Objects.hashCode(_serviceId);
            result = prime * result + Objects.hashCode(_serviceType);
            result = prime * result + Objects.hashCode(_source);
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
            if (!OpticalServiceCreateInput.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            OpticalServiceCreateInput other = (OpticalServiceCreateInput)obj;
            if (!Objects.equals(_globalCustomerId, other.getGlobalCustomerId())) {
                return false;
            }
            if (!Objects.equals(_notificationUrl, other.getNotificationUrl())) {
                return false;
            }
            if (!Objects.equals(_payload, other.getPayload())) {
                return false;
            }
            if (!Objects.equals(_requestId, other.getRequestId())) {
                return false;
            }
            if (!Objects.equals(_serviceId, other.getServiceId())) {
                return false;
            }
            if (!Objects.equals(_serviceType, other.getServiceType())) {
                return false;
            }
            if (!Objects.equals(_source, other.getSource())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                OpticalServiceCreateInputImpl otherImpl = (OpticalServiceCreateInputImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<Class<? extends Augmentation<OpticalServiceCreateInput>>, Augmentation<OpticalServiceCreateInput>> e : augmentation.entrySet()) {
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
            final MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper("OpticalServiceCreateInput");
            CodeHelpers.appendValue(helper, "_globalCustomerId", _globalCustomerId);
            CodeHelpers.appendValue(helper, "_notificationUrl", _notificationUrl);
            CodeHelpers.appendValue(helper, "_payload", _payload);
            CodeHelpers.appendValue(helper, "_requestId", _requestId);
            CodeHelpers.appendValue(helper, "_serviceId", _serviceId);
            CodeHelpers.appendValue(helper, "_serviceType", _serviceType);
            CodeHelpers.appendValue(helper, "_source", _source);
            CodeHelpers.appendValue(helper, "augmentation", augmentation.values());
            return helper.toString();
        }
    }
}
