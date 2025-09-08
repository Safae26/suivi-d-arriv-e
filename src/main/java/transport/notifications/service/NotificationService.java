package transport.notifications.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import transport.notifications.model.Notification;
import transport.notifications.dto.NotificationRequest;
import transport.notifications.repository.NotificationRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private PushNotificationService pushNotificationService;

    public Notification createArrivalTrackingNotification(NotificationRequest request) {
        String message = buildArrivalMessage(request);

        Notification notification = new Notification(
                request.getUserId(),
                request.getTitle() != null ? request.getTitle() : "Suivi d'arrivée",
                message,
                request.getChannel(),
                request.getTransportRequestId()
        );

        Notification savedNotification = notificationRepository.save(notification);
        sendNotification(savedNotification);

        return savedNotification;
    }

    private String buildArrivalMessage(NotificationRequest request) {
        if (request.getMessage() != null) {
            return request.getMessage();
        }

        StringBuilder message = new StringBuilder();
        message.append("Votre colis associé à la demande de transport #")
                .append(request.getTransportRequestId())
                .append(" est en approche.");

        if (request.getEstimatedArrivalTime() != null) {
            message.append(" Heure d'arrivée estimée: ")
                    .append(request.getEstimatedArrivalTime());
        }

        return message.toString();
    }

    public void sendNotification(Notification notification) {
        try {
            boolean sentSuccessfully = false;

            if ("EMAIL".equalsIgnoreCase(notification.getChannel()) || "ALL".equalsIgnoreCase(notification.getChannel())) {
                sentSuccessfully = emailService.sendEmail(notification.getUserId(), notification.getTitle(), notification.getMessage());
            }

            if ("SMS".equalsIgnoreCase(notification.getChannel()) || "ALL".equalsIgnoreCase(notification.getChannel())) {
                sentSuccessfully = smsService.sendSms(notification.getUserId(), notification.getMessage());
            }

            if ("PUSH".equalsIgnoreCase(notification.getChannel()) || "ALL".equalsIgnoreCase(notification.getChannel())) {
                sentSuccessfully = pushNotificationService.sendPushNotification(notification.getUserId(), notification.getTitle(), notification.getMessage());
            }

            if (sentSuccessfully) {
                notification.setStatus("SENT");
                notification.setSentAt(LocalDateTime.now());
            } else {
                notification.setStatus("FAILED");
            }

            notificationRepository.save(notification);
        } catch (Exception e) {
            notification.setStatus("FAILED");
            notificationRepository.save(notification);
            throw new RuntimeException("Erreur lors de l'envoi de la notification", e);
        }
    }

    public List<Notification> getUserNotifications(String userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getNotificationsByStatus(String status) {
        return notificationRepository.findByStatus(status);
    }
}