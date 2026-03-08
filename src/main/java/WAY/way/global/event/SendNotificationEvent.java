package WAY.way.global.event;

import WAY.way.domain.notification.entity.constant.NotificationType;

public record SendNotificationEvent(
    NotificationType notificationType,
    Long sourceId
) {
}
