package WAY.way.domain.auth.presentation.data.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 회원가입(이름·학번 등록) 요청 DTO.
 *
 * @param name          회원 이름 (필수)
 * @param studentNumber 학번 (형식: 학년[1-3] + 반[1-4] + 번호[01-18], 예: "1101")
 */
public record SignUpRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotBlank(message = "학번은 필수입니다.")
        @Pattern(regexp = "^[1-3][1-4](0[1-9]|1[0-8])$",message = "학번 형식이 올바르지 않습니다.")
        String studentNumber
) {
}
