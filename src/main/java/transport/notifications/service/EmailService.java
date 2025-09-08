package transport.notifications.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public boolean sendEmail(String userId, String subject, String message) {
        // Implémentation réelle d'envoi d'email
        System.out.println("Envoi d'email à " + userId + ": " + subject + " - " + message);
        return true; // Simuler un envoi réussi
    }
}