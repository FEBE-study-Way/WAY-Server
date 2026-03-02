package WAY.way.global.security.handler;

import WAY.way.global.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * 인가(Authorization) 실패 시 403 Forbidden 응답을 반환하는 핸들러.
 * <p>
 * 인증은 되었으나 해당 리소스에 접근할 권한이 없을 때 호출된다.
 * </p>
 */
@RequiredArgsConstructor
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 403 Forbidden 응답을 JSON 형태로 전송한다.
     *
     * @param request               HTTP 요청
     * @param response              HTTP 응답
     * @param accessDeniedException 접근 거부 예외
     * @throws IOException 응답 작성 중 입출력 예외
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException{
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(),new ErrorResponse(
                403, "접근 권한이 없습니다."
        ));
    }


    private final ObjectMapper objectMapper;

}
