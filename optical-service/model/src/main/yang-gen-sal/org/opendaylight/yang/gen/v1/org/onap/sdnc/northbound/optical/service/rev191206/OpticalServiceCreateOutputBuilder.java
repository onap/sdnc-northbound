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
import org.opendaylight.yangtools.concepts.Builder;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.CodeHelpers;
import org.opendaylight.yangtools.yang.binding.DataObject;

/**
 * Class that builds {@link OpticalServiceCreateOutputBuilder} instances.
 *
 * @see OpticalServiceCreateOutputBuilder
 *
 */
public class OpticalServiceCreateOutputBuilder implements Builder<OpticalServiceCreateOutput> {

    private String _ackFinalIndicator;
    private String _responseCode;
    private String _responseMessage;


    Map<Class<? extends Augmentation<OpticalServiceCreateOutput>>, Augmentation<OpticalServiceCreateOutput>> augmentation = Collections.emptyMap();

    public OpticalServiceCreateOutputBuilder() {
    }
    public OpticalServiceCreateOutputBuilder(org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceResponse arg) {
        this._responseCode = arg.getResponseCode();
        this._responseMessage = arg.getResponseMessage();
        this._ackFinalIndicator = arg.getAckFinalIndicator();
    }

    public OpticalServiceCreateOutputBuilder(OpticalServiceCreateOutput base) {
        this._ackFinalIndicator = base.getAckFinalIndicator();
        this._responseCode = base.getResponseCode();
        this._responseMessage = base.getResponseMessage();
        if (base instanceof OpticalServiceCreateOutputImpl) {
            OpticalServiceCreateOutputImpl impl = (OpticalServiceCreateOutputImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            Map<Class<? extends Augmentation<OpticalServiceCreateOutput>>, Augmentation<OpticalServiceCreateOutput>> aug =((AugmentationHolder<OpticalServiceCreateOutput>) base).augmentations();
            if (!aug.isEmpty()) {
                this.augmentation = new HashMap<>(aug);
            }
        }
    }

    /**
     * Set fields from given grouping argument. Valid argument is instance of one of following types:
     * <ul>
     * <li>org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceResponse</li>
     * </ul>
     *
     * @param arg grouping object
     * @throws IllegalArgumentException if given argument is none of valid types
    */
    public void fieldsFrom(DataObject arg) {
        boolean isValidArg = false;
        if (arg instanceof org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceResponse) {
            this._responseCode = ((org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceResponse)arg).getResponseCode();
            this._responseMessage = ((org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceResponse)arg).getResponseMessage();
            this._ackFinalIndicator = ((org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceResponse)arg).getAckFinalIndicator();
            isValidArg = true;
        }
        CodeHelpers.validValue(isValidArg, arg, "[org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206.OpticalServiceResponse]");
    }

    public String getAckFinalIndicator() {
        return _ackFinalIndicator;
    }
    
    public String getResponseCode() {
        return _responseCode;
    }
    
    public String getResponseMessage() {
        return _responseMessage;
    }
    
    @SuppressWarnings({ "unchecked", "checkstyle:methodTypeParameterName"})
    public <E$$ extends Augmentation<OpticalServiceCreateOutput>> E$$ augmentation(Class<E$$> augmentationType) {
        return (E$$) augmentation.get(CodeHelpers.nonNullValue(augmentationType, "augmentationType"));
    }

    
    public OpticalServiceCreateOutputBuilder setAckFinalIndicator(final String value) {
        this._ackFinalIndicator = value;
        return this;
    }
    
    public OpticalServiceCreateOutputBuilder setResponseCode(final String value) {
        this._responseCode = value;
        return this;
    }
    
    public OpticalServiceCreateOutputBuilder setResponseMessage(final String value) {
        this._responseMessage = value;
        return this;
    }
    
    public OpticalServiceCreateOutputBuilder addAugmentation(Class<? extends Augmentation<OpticalServiceCreateOutput>> augmentationType, Augmentation<OpticalServiceCreateOutput> augmentationValue) {
        if (augmentationValue == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentationValue);
        return this;
    }
    
    public OpticalServiceCreateOutputBuilder removeAugmentation(Class<? extends Augmentation<OpticalServiceCreateOutput>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    @Override
    public OpticalServiceCreateOutput build() {
        return new OpticalServiceCreateOutputImpl(this);
    }

    private static final class OpticalServiceCreateOutputImpl implements OpticalServiceCreateOutput {
    
        private final String _ackFinalIndicator;
        private final String _responseCode;
        private final String _responseMessage;
    
        private Map<Class<? extends Augmentation<OpticalServiceCreateOutput>>, Augmentation<OpticalServiceCreateOutput>> augmentation = Collections.emptyMap();
    
        OpticalServiceCreateOutputImpl(OpticalServiceCreateOutputBuilder base) {
            this._ackFinalIndicator = base.getAckFinalIndicator();
            this._responseCode = base.getResponseCode();
            this._responseMessage = base.getResponseMessage();
            this.augmentation = ImmutableMap.copyOf(base.augmentation);
        }
    
        @Override
        public Class<OpticalServiceCreateOutput> getImplementedInterface() {
            return OpticalServiceCreateOutput.class;
        }
    
        @Override
        public String getAckFinalIndicator() {
            return _ackFinalIndicator;
        }
        
        @Override
        public String getResponseCode() {
            return _responseCode;
        }
        
        @Override
        public String getResponseMessage() {
            return _responseMessage;
        }
        
        @SuppressWarnings({ "unchecked", "checkstyle:methodTypeParameterName"})
        @Override
        public <E$$ extends Augmentation<OpticalServiceCreateOutput>> E$$ augmentation(Class<E$$> augmentationType) {
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
            result = prime * result + Objects.hashCode(_ackFinalIndicator);
            result = prime * result + Objects.hashCode(_responseCode);
            result = prime * result + Objects.hashCode(_responseMessage);
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
            if (!OpticalServiceCreateOutput.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            OpticalServiceCreateOutput other = (OpticalServiceCreateOutput)obj;
            if (!Objects.equals(_ackFinalIndicator, other.getAckFinalIndicator())) {
                return false;
            }
            if (!Objects.equals(_responseCode, other.getResponseCode())) {
                return false;
            }
            if (!Objects.equals(_responseMessage, other.getResponseMessage())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                OpticalServiceCreateOutputImpl otherImpl = (OpticalServiceCreateOutputImpl) obj;
                if (!Objects.equals(augmentation, otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<Class<? extends Augmentation<OpticalServiceCreateOutput>>, Augmentation<OpticalServiceCreateOutput>> e : augmentation.entrySet()) {
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
            final MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper("OpticalServiceCreateOutput");
            CodeHelpers.appendValue(helper, "_ackFinalIndicator", _ackFinalIndicator);
            CodeHelpers.appendValue(helper, "_responseCode", _responseCode);
            CodeHelpers.appendValue(helper, "_responseMessage", _responseMessage);
            CodeHelpers.appendValue(helper, "augmentation", augmentation.values());
            return helper.toString();
        }
    }
}
