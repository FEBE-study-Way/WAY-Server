package WAY.way.domain.room.presentation.data;

/**
 * 공간 유형을 나타내는 열거형.
 * <ul>
 *   <li>{@link #MAIN} - 본관 내 공간 (강의실 등)</li>
 *   <li>{@link #DONGHANG} - 동행관 내 공간</li>
 *   <li>{@link #GEUMBONG} - 금봉관 내 공간</li>
 * </ul>
 */
public enum Kind {
    /** 본관 내 공간. */
    MAIN,
    /** 동행관 내 공간. */
    DONGHANG,
    /** 금봉관 내 공간. */
    GEUMBONG
}
