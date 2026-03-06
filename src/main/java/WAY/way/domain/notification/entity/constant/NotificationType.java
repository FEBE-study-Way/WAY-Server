package WAY.way.domain.notification.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    TEACHER_SIGNUP_REQUEST("선생님 가입 요청", "%s님이 %s 방에 가입 요청을 보냈습니다."),
    TEACHER_REQUEST_APPROVED("가입 요청 승인", "%s님의 가입 요청이 승인되었습니다."),
    TEACHER_REQUEST_REJECTED("가입 요청 거절", "%s님의 가입 요청이 거절되었습니다.");

    private final String title;
    private final String bodyFormat;

    public String formatBody(Object... args) {
        return String.format(bodyFormat, args);
    }
}
