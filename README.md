# 📧 Microservice de Suivi d'Arrivée - Notifications

## 📋 Description
Ce microservice fait partie d'une plateforme d'optimisation du transport de marchandises, il est chargé d’émettre des notifications de suivi d’arrivée aux clients. Il permet d'informer les utilisateurs de l'état d'avancement de leurs livraisons via différents canaux de communication **e-mail**, **SMS** et **push**.

---

## Fonctionnalités Implémentées

| Fonctionnalité | Détails |
|---------------|---------|
| 🚚 **Alerte « colis en approche »** | Diffusion en *temps-réel* dès que le chauffeur entre dans la zone de livraison. |
| **Multi-canal** | Un seul appel ➜ e-mail📧 **ET/OU** SMS📱 **ET/OU** push🔔 (ou `ALL`🌐 pour tout d’un coup). |
| 🔄 **Gestion des statuts de notification** | Statuts automatisés : `PENDING` ➜ `SENT` ✔ ou `FAILED` ❌ (avec retry programmé). |
| **Historique** | Historique des notifications par utilisateur. |
| **Filtrage des notifications** | par statut. |
| **Personnalisation des messages** | Personnalisation des messages avec informations de livraison. |
| 🔧 **Config externalisée** | Profils `dev` (H2) & `prod` (MySQL) switchables en 1 variable. |

---

## 🛠️ Stack technique
| Technologie | Version / Remarque |
|-------------|--------------------|
| Java | Avec Spring Boot |
| Spring Data JPA | Persistance des données |
| Spring Mail | Envoi d’e-mails |
| MySQL | Base de production |
| Lombok | Réduction du CODE boiler-plate |
| Maven | Gestion des dépendances |
| Git | Versionning |

---

## 🔧 Structure du projet
```
notifications/
├── src/main/java/transport/notifications/
│   ├── controller/NotificationController.java
│   ├── dto/NotificationRequest.java
│   ├── model/Notification.java
│   ├── repository/NotificationRepository.java
│   ├── service/
│   │   ├── NotificationService.java
│   │   ├── EmailService.java
│   │   ├── SmsService.java
│   │   └── PushNotificationService.java
│   └── NotificationsApplication.java
├── src/main/resources/
│   └── application.properties
└── pom.xml
```

---

## Base MySQL
```sql
CREATE DATABASE transport_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

---

## 🔌 API Endpoints

```json
POST /api/notifications/arrival-tracking
Content-Type: application/json

{
  "userId": "youemail@gmail.com",
  "title": "Votre colis arrive",
  "message": "Livraison estimée dans 30 min",
  "channel": "EMAIL",
  "transportRequestId": "TR-2024-001",
  "estimatedArrivalTime": "2024-09-09T16:30:00"
}
```
Réponse : `200 OK` + objet `Notification` créé.

---

## 🚀 Démarrage rapide
Le service écoute sur **http://localhost:8082**

---

## 📡 Endpoints REST
Base : `http://localhost:8082/api/notifications`

| Méthode | URL | Description | Body JSON |
|---------|-----|-------------|-----------|
| **POST** | `/arrival-tracking` | Créer + envoyer une notification | [voir ci-dessous](#exemple-de-requête) |
| **GET**  | `/user/{userId}` | Lister les notifications d’un utilisateur | - |
| **GET**  | `/status/{status}` | Filtrer par statut (`PENDING`, `SENT`, `FAILED`) | - |

---

## Tester l’API

### cURL
```bash
curl -X POST http://localhost:8082/api/notifications/arrival-tracking \
  -H "Content-Type: application/json" \
  -d '{"userId":"test@demo.com","title":"Test","message":"Hello","channel":"EMAIL","transportRequestId":"TEST-001"}'
```

### PowerShell
```powershell
$body = @{
    userId = "test@demo.com"
    title  = "Test"
    message = "Hello depuis PowerShell"
    channel = "ALL"
    transportRequestId = "PS-001"
} | ConvertTo-Json

Invoke-RestMethod -Uri http://localhost:8082/api/notifications/arrival-tracking -Method Post -Body $body -ContentType "application/json"
```

---

## 📧 Configuration e-mail (Gmail)

1. Activez **l’authentification à 2 facteurs** sur votre compte Google.  
2. Générez un **mot de passe d’application** :  
   https://myaccount.google.com/apppasswords  
3. Renseignez-le dans `application-prod.properties` :

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=s.eraji@edu.umi.ac.ma
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
```

---

## 🗄️ Configuration base de données

### Profil `dev` (H2 embarquée)
```properties
spring.datasource.url=jdbc:h2:mem:transport_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

### Profil `prod` (MySQL)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/transport_db
spring.datasource.username=transport
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

---

## 🔐 Sécurité des secrets
- Utilisez les **variables d’environnement** ou Spring Cloud Config Server.  
- Ajoutez `application-prod.properties` à `.gitignore`.  
- Fournissez un template :
```properties
spring.mail.password=${MAIL_PASSWORD:changeme}
spring.datasource.password=${DB_PASSWORD:changeme}
```

---

## 📸 Captures d’écran
- `api-response.png` – réponse Swagger / Postman  
- `h2-console.png` – consultation des notifications  
- `email-received.png` – rendu dans la boîte mail
---

**Safae ERAJI**
