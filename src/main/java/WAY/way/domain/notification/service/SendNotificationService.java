package WAY.way.domain.notification.service;

import WAY.way.domain.notification.entity.constant.NotificationType;

public interface SendNotificationService {
    void execute(NotificationType notificationType, Long sourceId);
}
