# RelayCore 🚀

**RelayCore** is a highly resilient, asynchronous Webhook and Event Relay Engine. It is designed to act as a reliable middleman for distributed systems, decoupling the creation of an event from its delivery. 

Instead of making synchronous HTTP calls that can fail and slow down your primary application, services can instantly offload payloads to RelayCore. RelayCore queues the messages, guarantees delivery via background processing, and automatically handles retries and failures.



## 🌟 Key Features

* **Asynchronous Ingestion:** Instantly accepts webhook payloads and returns a success response to the sender, moving the heavy lifting to the background.
* **Guaranteed Delivery:** Uses **Apache Kafka** to queue events. If the destination server is down, the message is safe.
* **Smart Retries & Fault Tolerance:** Implements delayed retries. If a destination is unreachable, RelayCore backs off and tries again.
* **Dead Letter Queue (DLQ):** Messages that exhaust all retry attempts are safely parked in a Dead Letter Topic for manual inspection, ensuring zero data loss.
* **Control Plane UI:** A sleek React dashboard to manually dispatch events, view statuses, and monitor the queue in real-time.
* **OpenAPI Integration:** Frontend clients are automatically generated from the Spring Boot OpenAPI specifications, ensuring a strict and always-up-to-date API contract.

## 🛠️ Tech Stack

**Backend (The Engine):**
* Java 21+ & Spring Boot 3.x
* Spring Kafka (Message Broker & Consumers)
* Spring Web (`RestClient` for dispatching webhooks)
* PostgreSQL (Persistent storage for delivery logs)
* OpenAPI / SpringDoc (API documentation & client generation)

**Frontend (Control Plane):**
* React.js (Vite)
* JavaScript / JSX
* Auto-generated Superagent API Client

**Infrastructure:**
* Docker & Docker Compose (Kafka, PostgreSQL)

---

## 📂 Project Structure

├── .mvn/                  # Maven wrapper config

├── relaycore-ui/          # React frontend (Vite)

├── src/                   # Spring Boot Java source code

├── docker-compose.yml     # Infrastructure (Kafka, Postgres)

├── pom.xml                # Backend dependencies

└── README.md              # You are here!

---

## 🚀 Getting Started

### Prerequisites
* Java 21+ installed
* Node.js & npm installed
* Docker Desktop running

### 1. Start the Infrastructure
RelayCore relies on Kafka and PostgreSQL. Spin them up using Docker:
\`\`\`bash
docker-compose up -d
\`\`\`

### 2. Run the Backend (Spring Boot)
Open a terminal in the root directory and run:
\`\`\`bash
./mvnw spring-boot:run
\`\`\`
*The API will be available at `http://localhost:8080`*

### 3. Run the Frontend (React Control Plane)
Open a new terminal, navigate to the UI folder, install dependencies, and start Vite:
\`\`\`bash
cd relaycore-ui
npm install
npm run dev
\`\`\`
*The UI will be available at `http://localhost:5173`*

---

## 💡 How it Works (The Message Lifecycle)

1. **Ingestion:** The user (or a microservice) submits a JSON payload and a Destination URL via the UI/API.
2. **Queuing:** Spring Boot instantly publishes this data to the `webhook-inbound` Kafka topic.
3. **Processing:** The Java `DeliveryWorker` consumes the message and makes an HTTP POST request to the destination.
4. **Retry/DLT:** If the destination returns a 5xx error or times out, Kafka moves it to a retry queue. After max attempts, it enters the `webhook-inbound-dlt` (Dead Letter Topic).

---

## 🔮 Future Roadmap (Integration with E-Commerce)
RelayCore is built to be the "Notification Hub" for larger microservice architectures. Future implementations will include:
* Integration with **OP Shopy** (Microservices E-Commerce Platform).
* Consuming "Order Created" events to trigger asynchronous email receipts.
* Real-time history and DLT monitoring dashboard.

---
*Built with ❤️ for resilient distributed systems.*
