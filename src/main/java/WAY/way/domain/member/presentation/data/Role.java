package WAY.way.domain.member.presentation.data;

/**
 * 회원 권한(역할)을 나타내는 열거형.
 * <ul>
 *   <li>{@link #UNAUTHENTICATED} - OAuth 로그인 후 회원가입 미완료 상태</li>
 *   <li>{@link #USER} - 회원가입 완료 후 일반 사용자</li>
 *   <li>{@link #ADMIN} - 관리자</li>
 * </ul>
 */
public enum Role {
    /** OAuth 로그인 후 이름·학번 등록 전 상태. */
    UNAUTHENTICATED,
    /** 회원가입 완료된 일반 사용자. */
    USER,
    /** 관리자. */
    ADMIN
}
