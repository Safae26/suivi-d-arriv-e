package transport.notifications.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity @Data
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String userId;

    @Column(nullable = false, length = 50)
    private String type; // "ARRIVAL_TRACKING"

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 1000)
    private String message;

    @Column(nullable = false, length = 20)
    private String status; // "PENDING", "SENT", "FAILED"

    @Column(nullable = false, length = 10)
    private String channel; // "EMAIL", "SMS", "PUSH", "ALL"

    @Column(length = 50)
    private String transportRequestId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime sentAt;

    // Constructeurs
    public Notification() {
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";
    }

    public Notification(String userId, String title, String message, String channel, String transportRequestId) {
        this();
        this.userId = userId;
        this.type = "ARRIVAL_TRACKING";
        this.title = title;
        this.message = message;
        this.channel = channel;
        this.transportRequestId = transportRequestId;
    }
}