# **Tasks Manager Application**
A Microservice-Based System for Service-Oriented Architecture

---

## **Overview**
The Tasks Manager Application is a fully containerized, microservice-based system designed to manage authentication, 
user management, and task management functionalities. It leverages Docker for containerization and Docker Compose for 
orchestration.

The system employs a robust backend built with Java Spring Boot, a microfrontend architecture using Angular and Module 
Federation, and an NGINX load-balanced API Gateway for efficient request routing. Additionally, it integrates serverless 
functions using AWS Lambda, RabbitMQ for asynchronous communication, and Apache Kafka for real-time event streaming. 
Security is ensured through JWT-based authentication and authorization.

## **Key Features**
- **Microservice Architecture**: Independent services for authentication, tasks, logging, and notifications.
- **Microfrontend Design**: Modular architecture using Angular and Module Federation.
- **Asynchronous Messaging**: RabbitMQ for message brokering and SMTP for email notifications.
- **Event Streaming**: Apache Kafka for real-time data processing and communication.
- **Serverless Computing**: AWS Lambda for lightweight, scalable functions.
- **Containerization**: Docker and Docker Compose for consistent and scalable deployments.
- **Secure APIs**: JWT-based token authentication.

## **Application Components:**

## **Backend Services**
1. **Auth Service**: Manages user authentication and generates JWT tokens for secure communication.
2. **Tasks Service**: Provides endpoints for managing tasks, including task creation, assignment, and updates.
3. **Logger Service**: Centralizes logs emitted by Tasks Service for better monitoring and debugging.
4. **Notification Service**: Sends emails for task assignments and upcoming deadlines.

## **API Gateway**
The API Gateway is built with **NGINX** and acts as a reverse proxy and load balancer, efficiently routing 
requests to the appropriate backend services.

## **Database Layer**
A shared **PostgreSQL** database instance is used for the Authentication and Tasks microservices.

## **Message Brokers and Event Streaming**
1. **RabbitMQ**:
    - Enables asynchronous communication between the Tasks Service and the Notification Service.
    - Example Workflow:
        - When a task is assigned, the Tasks Service sends a message to RabbitMQ.
        - RabbitMQ forwards the message to an SMTP server, which sends an email notification to the assigned user.
    - **Deadline Reminders**:
        - A scheduled job sends requests daily to a Lambda function (`deadline_reminder`) to compute days until task due dates.
        - If the due date is ≤ 3 days away, a reminder email is triggered via RabbitMQ and SMTP.

2. **Apache Kafka**:
    - Handles real-time event-driven communication.
    - Example Workflow:
        - Tasks Service emits events whenever an endpoint is accessed.
        - Logger Service consumes these events and centralizes logs for monitoring.

## **FaaS (Function as a Service)**
The `deadline_reminder` function is implemented using **AWS Lambda**. It calculates the number of days remaining 
until a task's due date and triggers notifications when the due date is ≤ 3 days.
This approach allows the system to handle user-specific queries without requiring a full-fledged backend service.
The function is deployed and managed using the Serverless Framework for efficient deployment to AWS.

## **System architecture**

The front-end of the tasks manager application is built as a microfrontend architecture using Module Federation with Webpack. It consists of a host (shell) and two microfrontends (authentication and tasks) to provide a modular and scalable interface.

All of the above are illustrated in the following architecture diagram:
![Untitled Diagram drawio(1)](https://github.com/user-attachments/assets/3b15f5b8-a7fa-44e9-b0e0-b46cefd6b1b7)

Principles:

- All environment-specific configurations are passed as variables defined in the Docker Compose file or activated through profile-based placeholders in the Spring application configuration.
- All data is stored in a shared PostgreSQL database instance.
- The Java REST APIs run multiple instances, with load balancing managed by the NGINX Gateway, which handles external access to the services.
- Task assignment and upcoming deadline emails are handled asynchronously through RabbitMQ messaging.
- Due to having multiple instances started, for traceability, logs are gathered in the loggingService.
- The REST micro-services are protected through OAuth using JWTs generated and managed by the authenticationService.

## **A detailed view of the microservices**

### **1. Authentication Microservice**

The **Authentication Microservice** is responsible for user authentication and registration. It provides REST API endpoints for user management and issues secure **JWT tokens** for authentication.

### **Key Features**
- User registration and account creation.
- Secure login with password hashing and validation.
- JWT token generation for authenticated access.

### **Main Components**
#### **REST API Endpoints**
- **`POST /register`** – Registers a new user in the system.
- **`POST /login`** – Authenticates a user and returns a JWT token.
- **`GET /jwks`** – Provides the **JSON Web Key Set (JWKS)**, containing the public key used to verify JWT signatures, ensuring secure authentication and authorization.

This is the corresponding UML diagram generated with Intellij's diagrams plugin:
![authentication_uml](https://github.com/user-attachments/assets/7c5f5c26-5966-408a-8217-f66e42f7609a)


## **Technologies Used**
- **Backend**: Java Spring Boot
- **Frontend**: Angular with Module Federation
- **Containerization**: Docker, Docker Compose
- **Load Balancer**: NGINX
- **Database**: PostgreSQL
- **Message Broker**: RabbitMQ
- **Event Streaming**: Apache Kafka
- **Serverless Framework**: AWS Lambda
- **Security**: JWT Authentication
