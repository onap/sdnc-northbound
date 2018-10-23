package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information;
import org.opendaylight.yangtools.yang.binding.Identifier;
import java.util.Objects;

public class ReservationTargetListKey
 implements Identifier<ReservationTargetList> {
    private static final long serialVersionUID = 2576730725914335185L;
    private final java.lang.String _reservationTargetId;


    public ReservationTargetListKey(java.lang.String _reservationTargetId) {
    
    
        this._reservationTargetId = _reservationTargetId;
    }
    
    /**
     * Creates a copy from Source Object.
     *
     * @param source Source object
     */
    public ReservationTargetListKey(ReservationTargetListKey source) {
        this._reservationTargetId = source._reservationTargetId;
    }


    public java.lang.String getReservationTargetId() {
        return _reservationTargetId;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(_reservationTargetId);
        return result;
    }

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ReservationTargetListKey other = (ReservationTargetListKey) obj;
        if (!Objects.equals(_reservationTargetId, other._reservationTargetId)) {
            return false;
        }
        return true;
    }

    @Override
    public java.lang.String toString() {
        java.lang.StringBuilder builder = new java.lang.StringBuilder(org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411.capacity.reservation.information.capacity.reservation.information.ReservationTargetListKey.class.getSimpleName()).append(" [");
        boolean first = true;
    
        if (_reservationTargetId != null) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }
            builder.append("_reservationTargetId=");
            builder.append(_reservationTargetId);
        }
        return builder.append(']').toString();
    }
}

