package WAY.way.domain.notification.service.impl;

import WAY.way.domain.auth.entity.TeacherSignUpRequestEntity;
import WAY.way.domain.auth.exception.NotFoundTeacherSignUpRequestException;
import WAY.way.domain.auth.repository.TeacherSignUpRequestRepository;
import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.domain.member.presentation.data.Role;
import WAY.way.domain.member.repository.MemberRepository;
import WAY.way.domain.notification.entity.NotificationEntity;
import WAY.way.domain.notification.entity.constant.NotificationType;
import WAY.way.domain.notification.repository.NotificationRepository;
import WAY.way.domain.notification.service.SendNotificationService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SendNotificationServiceImpl implements SendNotificationService {

    private final TeacherSignUpRequestRepository teacherSignUpRequestRepository;
    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void execute(NotificationType notificationType, Long sourceId) {
        if (notificationType == NotificationType.TEACHER_SIGNUP_REQUEST) {
            sendTeacherSignUpRequestNotification(sourceId, notificationType);
        } else if (notificationType == NotificationType.TEACHER_REQUEST_APPROVED
                || notificationType == NotificationType.TEACHER_REQUEST_REJECTED) {
            sendTeacherSignUpRequestResultNotification(sourceId, notificationType);
        }
    }

    private void sendTeacherSignUpRequestNotification(Long teacherSignUpRequestId, NotificationType notificationType) {
        TeacherSignUpRequestEntity request = findRequestOrThrow(teacherSignUpRequestId);

        List<MemberEntity> admins = memberRepository.findAllByRole(Role.ADMIN);

        String title = notificationType.getTitle();
        String body = notificationType.formatBody(request.getTeacher().getName(), request.getRoomName());

        List<NotificationEntity> notificationEntities = admins.stream()
                .map(memberEntity -> NotificationEntity.builder()
                        .receiverId(memberEntity.getId())
                        .title(title)
                        .body(body)
                        .type(notificationType)
                        .isRead(false)
                        .build())
                .toList();

        notificationRepository.saveAll(notificationEntities);
    }

    private void sendTeacherSignUpRequestResultNotification(Long teacherSignUpRequestId, NotificationType notificationType) {
        TeacherSignUpRequestEntity request = findRequestOrThrow(teacherSignUpRequestId);

        String title = notificationType.getTitle();
        String body = notificationType.formatBody(request.getTeacher().getName());

        NotificationEntity notification = NotificationEntity.builder()
                .receiverId(request.getTeacher().getId())
                .title(title)
                .body(body)
                .type(notificationType)
                .isRead(false)
                .build();

        notificationRepository.save(notification);
    }

    private TeacherSignUpRequestEntity findRequestOrThrow(Long teacherSignUpRequestId) {
        return teacherSignUpRequestRepository.findById(teacherSignUpRequestId)
                .orElseThrow(NotFoundTeacherSignUpRequestException::new);
    }
}