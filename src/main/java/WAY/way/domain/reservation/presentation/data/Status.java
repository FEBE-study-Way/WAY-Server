package WAY.way.domain.reservation.presentation.data;

/**
 * 예약 상태를 나타내는 열거형.
 * <ul>
 *   <li>{@link #PENDING} - 승인 대기 중</li>
 *   <li>{@link #ACCEPTED} - 승인됨</li>
 *   <li>{@link #REJECTED} - 거부됨</li>
 * </ul>
 */
public enum Status {
    /** 승인 대기 중. */
    PENDING,
    /** 예약 승인됨. */
    ACCEPTED,
    /** 예약 거부됨. */
    REJECTED,
}
