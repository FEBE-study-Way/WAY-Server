package WAY.way.domain.auth.presentation.data.request;

import WAY.way.domain.auth.presentation.data.ApproveType;
import jakarta.validation.constraints.NotNull;

public record ApproveTeacherSignUpRequest(
        @NotNull
        ApproveType approveType
) {
}
