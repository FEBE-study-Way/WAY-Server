package WAY.way.domain.auth.presentation.data.request;

import jakarta.validation.constraints.NotBlank;

public record TeacherSignUpRequest(
        @NotBlank
        String name,
        @NotBlank
        String roomName
) {
}
