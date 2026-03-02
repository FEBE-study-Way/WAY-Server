package WAY.way.domain.auth.service;

import WAY.way.domain.auth.presentation.data.request.SignUpRequest;
import WAY.way.domain.auth.presentation.data.response.TokenResponse;

/**
 * 회원가입(이름·학번 등록) 서비스 인터페이스.
 */
public interface SignUpService {

    /**
     * 현재 로그인된 미인증 회원의 이름과 학번을 등록하여 회원가입을 완료한다.
     *
     * @param request 이름과 학번을 담은 요청 객체
     * @return 역할이 USER로 갱신된 새 토큰 정보
     */
    TokenResponse execute(SignUpRequest request);
}
