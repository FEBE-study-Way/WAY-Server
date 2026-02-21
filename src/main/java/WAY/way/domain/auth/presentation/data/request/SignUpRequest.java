package WAY.way.domain.auth.presentation.data.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotBlank(message = "학번은 필수입니다.")
        @Pattern(regexp = "^[1-3][1-4](0[1-9]|1[0-8])$")
        String studentNumber
) {
}
