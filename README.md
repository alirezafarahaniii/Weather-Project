# Weather-Project

Introduction to a Sample Microservice Project

Welcome to my sample microservice project, designed to provide a practical learning experience for junior developers. In this project, I explore the world of microservices, RabbitMQ for service communication, and key technologies like WebFlux, Actuator, and Spring Security.

What Is a Microservice?

A microservice is an architectural style where an application is composed of small, independently deployable services. Each service focuses on a specific business capability and communicates with other services via APIs, typically over HTTP.

Service Communication with RabbitMQ

In my project, I use RabbitMQ, a message broker, to facilitate communication between microservices. RabbitMQ enables asynchronous messaging, making it possible for services to communicate in a decoupled and scalable manner.

Key Technologies I Explore:

WebFlux: I leverage Spring WebFlux to build reactive microservices. Reactive programming allows me to handle a large number of concurrent connections efficiently.

Actuator: Spring Boot Actuator provides monitoring and management capabilities for my microservices. I gain insights into the health, metrics, and other crucial aspects of my services.

Spring Security: Security is a top priority. I demonstrate how to secure my microservices and APIs using Spring Security, ensuring data privacy and access control.

Why Learn from This Project?

This project is a valuable resource for junior developers for several reasons:

Hands-On Experience: I believe in learning by doing. You'll gain practical experience working with technologies commonly used in microservice architectures.

Real-World Scenario: The project mirrors real-world scenarios where multiple services collaborate to deliver a complete application.

Clean Code and Best Practices: My project adheres to clean code principles and best practices, demonstrating how to write maintainable and efficient microservices.

Inter-Service Communication: Understanding how services communicate is a fundamental concept in microservices. You'll see this in action through RabbitMQ.

Security and Monitoring: I address security concerns and the importance of monitoring services to ensure reliability.

Getting Started:

To begin your journey into microservices and the technologies mentioned, explore my project's code, documentation, and step-by-step tutorials. Don't hesitate to reach out if you have questions or need assistance.






#########################################################################
post api : http://localhost:9090/api/v1/addCity

body : {
     "name": "tehran",
     "lat":" 1111",
     "lon":" 2222"
}

Basic Auth for postman:

username = user
password = password

#########################################################################
http://localhost:9090/api/v1/getWeather/Tehran
Basic Auth for postman:

username = user
password = password


#########################################################################

weather fetches from https://rapidapi.com/weatherapi/api/weatherapi-com

due to a connection loss, server1 sends an email to the defined email 

##########################################################################################
Access Actuator Endpoints: You can access Actuator endpoints via HTTP requests. For example:

Health Endpoint: http://localhost:9090/actuator/health

Info Endpoint: http://localhost:9090/actuator/info

Metrics Endpoint: http://localhost:9090/actuator/metrics

