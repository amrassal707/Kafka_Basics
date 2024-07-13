# Kafka Setup with Docker and Spring Boot

This guide demonstrates how to set up Apache Kafka using Docker and integrate it with a Spring Boot application. You'll learn how to send messages to a Kafka topic via a REST endpoint and consume those messages in the console.

## Prerequisites

- Docker installed on your machine
- Java 11 or higher
- Maven or Gradle

## Step-by-Step Setup

### 1. Download and Run Kafka Docker Image

First, pull the Apache Kafka Docker image from Docker Hub and run it:

```bash
docker pull apache/kafka:3.7.1
docker run -p 9092:9092 --name kafka-server apache/kafka:3.7.1

```
### 2. Set Up Spring Boot Application
#### Clone the Spring Boot project or set up a new one.

### 3. Configure Kafka in Spring Boot
#### Add the Kafka configuration to src/main/resources/application.yml:


```yml
spring:
  application:
    name: KafkaSpringExample
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: group_id
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer

kafka:
  topic:
    name: my-new-topic


```


### 4. Implement Producer and Consumer
ProducerService
Create a service to send messages to Kafka:

```java 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class ProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topicName;

    public ProducerService(KafkaTemplate<String, String> kafkaTemplate,
                           @Value("${kafka.topic.name}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    public void sendMessage(String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }
}

```

ConsumerService
Create a service to consume messages from Kafka:

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @KafkaListener(topics = "#{T(java.util.Collections).singletonList('${kafka.topic.name}')}" , groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }
}
```

### 5. Build and Run the Spring Boot Application
 Build and run the Spring Boot application.

### 6. Test the Application
 Use a tool like curl or Postman to send a message to Kafka via the /send endpoint.

### Conclusion
You've now successfully set up Apache Kafka using Docker and integrated it with a Spring Boot application to send and consume messages. This setup demonstrates a basic implementation; feel free to explore and extend it based on your requirements.
