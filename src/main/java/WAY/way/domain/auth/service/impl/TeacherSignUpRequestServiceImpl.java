package WAY.way.domain.auth.service.impl;

import WAY.way.domain.auth.entity.TeacherSignUpRequestEntity;
import WAY.way.domain.auth.exception.AlreadyPendingTeacherSignUpRequestException;
import WAY.way.domain.auth.exception.TeacherAlreadyAuthenticatedException;
import WAY.way.domain.auth.presentation.data.ApproveType;
import WAY.way.domain.auth.presentation.data.request.TeacherSignUpRequest;
import WAY.way.domain.auth.repository.TeacherSignUpRequestRepository;
import WAY.way.domain.auth.service.TeacherSignUpRequestService;
import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.domain.member.presentation.data.Role;
import WAY.way.domain.notification.entity.constant.NotificationType;
import WAY.way.domain.room.exception.NotFoundRoomException;
import WAY.way.domain.room.repository.RoomRepository;
import WAY.way.global.event.SendNotificationEvent;
import WAY.way.global.util.MemberUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherSignUpRequestServiceImpl implements TeacherSignUpRequestService {

    private final MemberUtil memberUtil;
    private final RoomRepository roomRepository;
    private final TeacherSignUpRequestRepository teacherSignUpRequestRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void execute(TeacherSignUpRequest request) {
        MemberEntity teacher = memberUtil.getCurrentMember();

        if (teacher.getRole() != Role.UNAUTHENTICATED) {
            throw new TeacherAlreadyAuthenticatedException();
        }

        if (teacherSignUpRequestRepository.existsByTeacherAndApproveType(teacher, ApproveType.PENDING)) {
            throw new AlreadyPendingTeacherSignUpRequestException();
        }

        validateRoomExists(request.roomName());
        teacher.teacherSignUpRequest(request.name());

        TeacherSignUpRequestEntity teacherSignUpRequest = TeacherSignUpRequestEntity.builder()
                .teacher(teacher)
                .roomName(request.roomName())
                .build();

        teacherSignUpRequestRepository.save(teacherSignUpRequest);
        applicationEventPublisher.publishEvent(new SendNotificationEvent(NotificationType.TEACHER_SIGNUP_REQUEST, teacherSignUpRequest.getId()));
    }

    private void validateRoomExists(String roomName) {
        if (!roomRepository.existsByName(roomName)) {
            throw new NotFoundRoomException();
        }
    }
}
