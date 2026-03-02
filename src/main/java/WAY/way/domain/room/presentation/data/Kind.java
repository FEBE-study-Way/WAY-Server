package WAY.way.domain.room.presentation.data;

/**
 * 공간 유형을 나타내는 열거형.
 * <ul>
 *   <li>{@link #SCHOOL} - 학교 내 공간 (강의실 등)</li>
 *   <li>{@link #DORMITORY} - 기숙사 내 공간</li>
 * </ul>
 */
public enum Kind {
    /** 학교 내 공간. */
    SCHOOL,
    /** 기숙사 내 공간. */
    DORMITORY
}
