### Services

#### Wallet service

You can create, add funds to your wallet, and transfer funds from one wallet to another.
The user can create multiple wallets.

* To prevent loss of data in case Kafka fails or network problems, I have used the outbox pattern. What basically
  happens is
  that before we send events through Kafka, we save them on a different database with the processed status set to false.
  This happens
  on a single transaction to ensure both succeed or both fail. The events are then processed by a scheduler that
  publishes the unprocessed events and waits for acknowledgement from Kafka to update the processed status to true. We
  can decide to delete this information from the outbox DB or just keep it. I decided to keep it.
* To ensure our events don’t break, I have used Avro schema and schema registry. It generates our events for us and any
  changes made, we make them through the Avro schema.
* For updating the balance with optimistic locking and publishing an event, this is where we use versioning. When the
  balance
  is updated, the version increases. This helps control concurrent operations, especially on write.
* To deal with eventual inconsistency, I have implemented the outbox pattern and also Kafka retries and idempotency.

#### History service

Listen for wallet events from Kafka and store transaction history. This includes money added to the wallet, money
transferred from one wallet to the other.
To handle duplicates, we check the transaction id to ensure they are processed only once, and also enable Kafka
idempotency.

Activities covered

* You can fund wallets concurrently without losing money.
* Events flow reliably from Wallet Service to History Service.
* History eventually catches up, even when services restart
* Understood why optimistic locking matters for financial data
* Understood the trade-offs of eventual consistency

### Tech stack

- Backend: Java + Spring Boot
- Database: PostgreSQL + Spring Data JPA
- Messaging: Apache Kafka
- Infrastructure: Docker Compose