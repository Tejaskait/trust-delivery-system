# 📦 Trust Delivery System - Secure Handoff

A robust delivery management platform featuring a **Sequential Dual-Verification** system. This project ensures that a delivery is only marked as complete when both the recipient and the logistics partner verify the handoff in a specific order.

## 🚀 Core Features

* **Sequential Verification:** The Delivery Agent is logically locked out from confirming delivery until the Customer first marks the parcel as "Received."
* **Role-Based Access Control (RBAC):** Integrated Spring Security to distinguish between `ROLE_CUSTOMER` and `ROLE_DELIVERY`.
* **Live Status Tracking:** Real-time UI updates using Thymeleaf and Bootstrap 5.
* **Security Enforcement:** Server-side validation prevents API manipulation via manual POST requests.
* **Glassmorphism UI:** A modern, responsive dashboard with intuitive visual cues (locks, verified badges, and status-dependent colors).

---

## 🛠️ Tech Stack

* **Backend:** Java 17, Spring Boot 3.x
* **Security:** Spring Security (RBAC)
* **Database:** Spring Data JPA (H2/MySQL/PostgreSQL)
* **Frontend:** Thymeleaf, Bootstrap 5, FontAwesome
* **Architecture:** MVC (Model-View-Controller)

---

## 🔒 The Handoff Logic

The system implements a strict state-machine logic for order completion:

1. **Order Accepted:** The Logistics Partner accepts the order.
2. **Recipient Confirmation:** The Customer clicks **"Parcel Received"**. This unlocks the agent's side.
3. **Agent Confirmation:** The Logistics Partner clicks **"Parcel Delivered"**.
4. **Order Completion:** Once both flags are `true`, the Order status automatically transitions to `COMPLETED`.

---

## 💻 Installation & Setup

1. **Clone the repository:**
```bash
git clone https://github.com/your-username/trust-delivery-system.git

```


2. **Configure Database:**
Update `src/main/resources/application.properties` with your database credentials.
3. **Build the project:**
```bash
mvn clean install

```


4. **Run the application:**
```bash
mvn spring-boot:run

```

## 🛡️ Security Implementation

Unlike standard systems, this project checks both **Role** and **State** at the Controller level:

```java
if (role.equals("ROLE_DELIVERY")) {
    if (!orderConfirmation.isCustomerConfirmed()) {
        throw new RuntimeException("Protocol Violation: Recipient must confirm first.");
    }
    // Proceed to confirm delivery
}

```

---

## 🤝 Contributing

Feel free to open issues or submit pull requests to improve the security flow or UI.
