package transport.notifications.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {
    public boolean sendSms(String phoneNumber, String message) {
        // Implémentation réelle d'envoi de SMS
        System.out.println("Envoi de SMS à " + phoneNumber + ": " + message);
        return true; // Simuler un envoi réussi
    }
}

