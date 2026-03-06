package WAY.way.domain.auth.service;

import WAY.way.domain.auth.presentation.data.request.TeacherSignUpRequest;

public interface TeacherSignUpRequestService {
    void execute(TeacherSignUpRequest request);
}
