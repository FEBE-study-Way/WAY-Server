package WAY.way.global.event.handler;

import WAY.way.domain.notification.service.SendNotificationService;
import WAY.way.global.event.SendNotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SendNotificationEventListener {

    private final SendNotificationService sendNotificationService;

    @Async
    @TransactionalEventListener(value = SendNotificationEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handleNotification(SendNotificationEvent event) {
            sendNotificationService.execute(event.notificationType(), event.sourceId());
    }
}
