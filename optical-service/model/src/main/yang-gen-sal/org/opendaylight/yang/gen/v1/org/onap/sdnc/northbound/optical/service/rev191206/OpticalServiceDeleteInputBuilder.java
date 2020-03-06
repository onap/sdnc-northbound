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
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.optical.service.delete.request.Payload;
import org.opendaylight.yangtools.concepts.Builder;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.CodeHelpers;
import org.opendaylight.yangtools.yang.binding.DataObject;

/**
 * Class that builds {@link OpticalServiceDeleteInputBuilder} instances.
 *
 * @see OpticalServiceDeleteInputBuilder
 *
 */
public class OpticalServiceDeleteInputBuilder implements Builder<OpticalServiceDeleteInput> {

    private Payload _payload;
    private String _requestId;
    private String _source;


    Map<Class<? extends Augmentation<OpticalServiceDeleteInput>>, Augmentation<OpticalServiceDeleteInput>> augmentation = Collections.emptyMap();

    public OpticalServiceDeleteInputBuilder() {
    }
    public OpticalServiceDeleteInputBuilder(org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteRequest arg) {
        this._payload = arg.getPayload();
        this._requestId = arg.getRequestId();
        this._source = arg.getSource();
    }
    public OpticalServiceDeleteInputBuilder(org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.RequestSourceG arg) {
        this._requestId = arg.getRequestId();
        this._source = arg.getSource();
    }

    public OpticalServiceDeleteInputBuilder(OpticalServiceDeleteInput base) {
        this._payload = base.getPayload();
        this._requestId = base.getRequestId();
        this._source = base.getSource();
        if (base instanceof OpticalServiceDeleteInputImpl) {
            OpticalServiceDeleteInputImpl impl = (OpticalServiceDeleteInputImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            Map<Class<? extends Augmentation<OpticalServiceDeleteInput>>, Augmentation<OpticalServiceDeleteInput>> aug =((AugmentationHolder<OpticalServiceDeleteInput>) base).augmentations();
            if (!aug.isEmpty()) {
                this.augmentation = new HashMap<>(aug);
            }
        }
    }

    /**
     * Set fields from given grouping argument. Valid argument is instance of one of following types:
     * <ul>
     * <li>org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteRequest</li>
     * <li>org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.RequestSourceG</li>
     * </ul>
     *
     * @param arg grouping object
     * @throws IllegalArgumentException if given argument is none of valid types
    */
    public void fieldsFrom(DataObject arg) {
        boolean isValidArg = false;
        if (arg instanceof org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteRequest) {
            this._payload = ((org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteRequest)arg).getPayload();
            isValidArg = true;
        }
        if (arg instanceof org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.RequestSourceG) {
            this._requestId = ((org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.RequestSourceG)arg).getRequestId();
            this._source = ((org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.RequestSourceG)arg).getSource();
            isValidArg = true;
        }
        CodeHelpers.validValue(isValidArg, arg, "[org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceDeleteRequest, org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.RequestSourceG]");
    }

    public Payload getPayload() {
        return _payload;
    }
    
    public String getRequestId() {
        return _requestId;
    }
    
    public String getSource() {
        return _source;
    }
    
    @SuppressWarnings({ "unchecked", "checkstyle:methodTypeParameterName"})
    public <E$$ extends Augmentation<OpticalServiceDeleteInput>> E$$ augmentation(Class<E$$> augmentationType) {
        return (E$$) augmentation.get(CodeHelpers.nonNullValue(augmentationType, "augmentationType"));
    }

    
    public OpticalServiceDeleteInputBuilder setPayload(final Payload value) {
        this._payload = value;
        return this;
    }
    
    public OpticalServiceDeleteInputBuilder setRequestId(final String value) {
        this._requestId = value;
        return this;
    }
    
    public OpticalServiceDeleteInputBuilder setSource(final String value) {
        this._source = value;
        return this;
    }
    
    public OpticalServiceDeleteInputBuilder addAugmentation(Class<? extends Augmentation<OpticalServiceDeleteInput>> augmentationType, Augmentation<OpticalServiceDeleteInput> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public OpticalServiceDeleteInputBuilder removeAugmentation(Class<? extends Augmentation<OpticalServiceDeleteInput>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public OpticalServiceDeleteInput build() {
        return new OpticalServiceDeleteInputImpl(this);
    }

    private static final class OpticalServiceDeleteInputImpl implements OpticalServiceDeleteInput {
    
        private final Payload _payload;
        private final String _requestId;
        private final String _source;
    
        private Map<Class<? extends Augmentation<OpticalServiceDeleteInput>>, Augmentation<OpticalServiceDeleteInput>> augmentation = Collections.emptyMap();
    
        OpticalServiceDeleteInputImpl(OpticalServiceDeleteInputBuilder base) {
            this._payload = base.getPayload();
            this._requestId = base.getRequestId();
            this._source = base.getSource();
            this.augmentation = ImmutableMap.copyOf(base.augmentation);
        }
    
        @Override
        public Class<OpticalServiceDeleteInput> getImplementedInterface() {
            return OpticalServiceDeleteInput.class;
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
        public String getSource() {
            return _source;
        }
        
        @SuppressWarnings({ "unchecked", "checkstyle:methodTypeParameterName"})
        @Override
        public <E$$ extends Augmentation<OpticalServiceDeleteInput>> E$$ augmentation(Class<E$$> augmentationType) {
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
            result = prime * result + Objects.hashCode(_payload);
            result = prime * result + Objects.hashCode(_requestId);
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
            if (!OpticalServiceDeleteInput.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            OpticalServiceDeleteInput other = (OpticalServiceDeleteInput)obj;
            if (!Objects.equals(_payload, other.getPayload())) {
                return false;
            }
            if (!Objects.equals(_requestId, other.getRequestId())) {
                return false;
            }
            if (!Objects.equals(_source, other.getSource())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                OpticalServiceDeleteInputImpl otherImpl = (OpticalServiceDeleteInputImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<Class<? extends Augmentation<OpticalServiceDeleteInput>>, Augmentation<OpticalServiceDeleteInput>> e : augmentation.entrySet()) {
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
            final MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper("OpticalServiceDeleteInput");
            CodeHelpers.appendValue(helper, "_payload", _payload);
            CodeHelpers.appendValue(helper, "_requestId", _requestId);
            CodeHelpers.appendValue(helper, "_source", _source);
            CodeHelpers.appendValue(helper, "augmentation", augmentation.values());
            return helper.toString();
        }
    }
}
