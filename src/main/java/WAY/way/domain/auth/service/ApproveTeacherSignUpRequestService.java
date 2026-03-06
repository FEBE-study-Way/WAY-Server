package WAY.way.domain.auth.service;

import WAY.way.domain.auth.presentation.data.request.ApproveTeacherSignUpRequest;

public interface ApproveTeacherSignUpRequestService {
    void execute(Long teacherSignUpRequestId, ApproveTeacherSignUpRequest request);
}
