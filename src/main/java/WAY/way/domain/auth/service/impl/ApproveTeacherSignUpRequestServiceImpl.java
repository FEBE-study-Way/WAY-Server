package WAY.way.domain.auth.service.impl;

import WAY.way.domain.auth.entity.TeacherSignUpRequestEntity;
import WAY.way.domain.auth.exception.NotFoundTeacherSignUpRequestException;
import WAY.way.domain.auth.exception.UnauthorizedUserException;
import WAY.way.domain.auth.presentation.data.ApproveType;
import WAY.way.domain.auth.presentation.data.request.ApproveTeacherSignUpRequest;
import WAY.way.domain.auth.repository.TeacherSignUpRequestRepository;
import WAY.way.domain.auth.service.ApproveTeacherSignUpRequestService;
import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.domain.member.exception.NotFoundMemberException;
import WAY.way.domain.member.presentation.data.Role;
import WAY.way.domain.member.repository.MemberRepository;
import WAY.way.domain.notification.entity.constant.NotificationType;
import WAY.way.domain.room.entity.RoomEntity;
import WAY.way.domain.room.exception.NotFoundRoomException;
import WAY.way.domain.room.repository.RoomRepository;
import WAY.way.global.event.SendNotificationEvent;
import WAY.way.global.util.MemberUtil;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApproveTeacherSignUpRequestServiceImpl implements ApproveTeacherSignUpRequestService {

    private final TeacherSignUpRequestRepository teacherSignUpRequestRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void execute(Long teacherSignUpRequestId, ApproveTeacherSignUpRequest request) {
        TeacherSignUpRequestEntity teacherSignUpRequest = teacherSignUpRequestRepository.findById(teacherSignUpRequestId)
                .orElseThrow(NotFoundTeacherSignUpRequestException::new);

        teacherSignUpRequest.approveRequest(request.approveType());

        if (request.approveType() == ApproveType.APPROVED) {
            approveTeacherSignUpRequest(teacherSignUpRequest.getTeacher().getId(), teacherSignUpRequest.getRoomName());
            applicationEventPublisher.publishEvent(new SendNotificationEvent(NotificationType.TEACHER_REQUEST_APPROVED, teacherSignUpRequestId));
        } else if (request.approveType() == ApproveType.REJECTED) {
            applicationEventPublisher.publishEvent(new SendNotificationEvent(NotificationType.TEACHER_REQUEST_REJECTED, teacherSignUpRequestId));
        }
    }

    private void approveTeacherSignUpRequest(Long teacherId, String roomName) {
        MemberEntity teacher = memberRepository.findById(teacherId)
                .orElseThrow(NotFoundMemberException::new);

        RoomEntity room = roomRepository.findByName(roomName)
                .orElseThrow(NotFoundRoomException::new);

        teacher.completeTeacherSignUp();
        room.assignOwner(teacher);
    }
}
