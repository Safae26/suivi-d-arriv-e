package transport.notifications.service;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {
    public boolean sendPushNotification(String userId, String title, String message) {
        // Implémentation réelle de notification push
        System.out.println("Envoi de notification push à " + userId + ": " + title + " - " + message);
        return true; // Simuler un envoi réussi
    }
}
