Je veux ce contenu en langage markdown de github (avec des ## etc):# 📧 Microservice de Notifications - Suivi d'Arrivée

## 📋 Table des Matières
- [🎯 Description du Projet](#-description-du-projet)
- [🛠️ Technologies Utilisées](#️-technologies-utilisées)
- [🚀 Fonctionnalités](#-fonctionnalités)
- [📦 Installation et Configuration](#-installation-et-configuration)
- [🔧 Structure du Projet](#-structure-du-projet)
- [📡 API Endpoints](#-api-endpoints)
- [🧪 Tests](#-tests)
- [📧 Configuration Email](#-configuration-email)
- [🗄️ Configuration Base de Données](#️-configuration-base-de-données)
- [📸 Captures d'Écran](#-captures-décran)

## 🎯 Description du Projet

Microservice Spring Boot dédié à la gestion des notifications de suivi d'arrivée pour une plateforme de transport de marchandises. Ce service fait partie d'une architecture microservices et permet l'envoi de notifications via email, SMS et push notifications.

## 🛠️ Technologies Utilisées

- **Java 21** - Langage de programmation
- **Spring Boot 3.5.4** - Framework principal
- **Spring Data JPA** - Persistance des données
- **Spring Mail** - Envoi d'emails
- **H2 Database** - Base de données en mémoire (développement)
- **MySQL** - Base de données de production
- **Maven** - Gestion des dépendances
- **Git** - Versionning

## 🚀 Fonctionnalités

- ✅ Envoi de notifications d'arrivée en temps réel
- ✅ Support multi-canaux (Email, SMS, Push)
- ✅ API RESTful complète
- ✅ Persistance des données
- ✅ Gestion des statuts de notifications
- ✅ Configuration externalisée

## 📦 Installation et Configuration

### 1. Prérequis
```bash
# Java 21
sudo apt install openjdk-21-jdk

# Maven
sudo apt install maven

# MySQL (optionnel)
sudo apt install mysql-server
```

### 2. Clonage du Repository
```bash
git clone https://github.com/Safae26/suivi-d-arriv-e.git
cd suivi-d-arriv-e
```

### 3. Configuration
Editez le fichier `src/main/resources/application.properties` :

```properties
# Configuration de base
spring.application.name=notifications
server.port=8082

# Base de données H2 (développement)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Configuration Gmail
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=votre.email@gmail.com
spring.mail.password=votre-mot-de-passe-application
```

### 4. Construction et Lancement
```bash
# Compilation
mvn clean compile

# Lancement
mvn spring-boot:run

# Ou depuis IDE
# Run NotificationsApplication.java
```

## 🔧 Structure du Projet

```
suivi-d-arriv-e/
├── src/
│   └── main/
│       ├── java/transport/notifications/
│       │   ├── NotificationsApplication.java
│       │   ├── controller/
│       │   │   └── NotificationController.java
│       │   ├── model/
│       │   │   └── Notification.java
│       │   ├── repository/
│       │   │   └── NotificationRepository.java
│       │   ├── service/
│       │   │   ├── NotificationService.java
│       │   │   ├── EmailService.java
│       │   │   ├── SmsService.java
│       │   │   └── PushNotificationService.java
│       │   └── dto/
│       │       └── NotificationRequest.java
│       └── resources/
│           └── application.properties
├── pom.xml
└── README.md
```

## 📡 API Endpoints

### POST /api/notifications/arrival-tracking
Envoie une notification de suivi d'arrivée

**Body:**
```json
{
    "userId": "string",
    "title": "string",
    "message": "string",
    "channel": "EMAIL|SMS|PUSH|ALL",
    "transportRequestId": "string",
    "estimatedArrivalTime": "datetime (optionnel)"
}
```

**Exemple:**
```bash
curl -X POST http://localhost:8082/api/notifications/arrival-tracking \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "client@example.com",
    "title": "Votre colis arrive",
    "message": "Livraison dans 30 minutes",
    "channel": "EMAIL",
    "transportRequestId": "TR-2024-001"
  }'
```

### GET /api/notifications/user/{userId}
Récupère les notifications d'un utilisateur

### GET /api/notifications/status/{status}
Récupère les notifications par statut

## 🧪 Tests

### Test avec PowerShell
```powershell
$testData = '{
    "userId": "test@example.com",
    "title": "Test Notification",
    "message": "Ceci est un test",
    "channel": "EMAIL",
    "transportRequestId": "TEST-001"
}'

Invoke-RestMethod -Uri "http://localhost:8082/api/notifications/arrival-tracking" `
  -Method Post `
  -ContentType "application/json" `
  -Body $testData
```

### Test avec curl
```bash
curl -X POST http://localhost:8082/api/notifications/arrival-tracking \
  -H "Content-Type: application/json" \
  -d '{"userId":"test@example.com","title":"Test","message":"Test","channel":"EMAIL","transportRequestId":"TEST-001"}'
```

## 📧 Configuration Email

### Configuration Gmail
1. **Activer la validation 2 étapes**
   - Allez sur https://myaccount.google.com/
   - Activez "Validation en deux étapes"

2. **Générer un mot de passe d'application**
   - Allez sur https://myaccount.google.com/apppasswords
   - Sélectionnez "Mail" → "Other" (nommez-le "Spring Boot")
   - Copiez le mot de passe généré

3. **Configurer application.properties**
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=votre.email@gmail.com
spring.mail.password=votre-mot-de-passe-application
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## 🗄️ Configuration Base de Données

### Option 1: H2 Database (Développement)
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

### Option 2: MySQL (Production)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/transport_db
spring.datasource.username=root
spring.datasource.password=votre-mot-de-passe
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

## 📸 Captures d'Écran

<!-- Ajoutez vos captures d'écran ici -->
![Architecture Microservices](screenshots/architecture.png)
*Diagramme d'architecture microservices*

![API Response](screenshots/api-response.png)
*Réponse de l'API de notifications*

![H2 Console](screenshots/h2-console.png)
*Console H2 pour visualisation des données*

![Email Reçu](screenshots/email-received.png)
*Exemple d'email reçu*
