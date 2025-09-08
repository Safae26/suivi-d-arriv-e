package transport.notifications.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationRequest {
    private String userId;
    private String title;
    private String message;
    private String channel; // "EMAIL", "SMS", "PUSH", "ALL"
    private String transportRequestId;
    private LocalDateTime estimatedArrivalTime;

    // Constructeurs
    public NotificationRequest() {}

    public NotificationRequest(String userId, String title, String message, String channel, String transportRequestId, LocalDateTime estimatedArrivalTime) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.channel = channel;
        this.transportRequestId = transportRequestId;
        this.estimatedArrivalTime = estimatedArrivalTime;
    }
}