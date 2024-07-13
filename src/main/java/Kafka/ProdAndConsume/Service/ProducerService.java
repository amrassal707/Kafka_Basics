package Kafka.ProdAndConsume.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final String topicName;

    public ProducerService(KafkaTemplate<String, String> kafkaTemplate,
                           @Value("${kafka.topic.name}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }


    public String SendMessage(String message){
        try {
            kafkaTemplate.send(topicName,message);
            return "Message sent successfully!";
        } catch (Exception e) {
            return "Failed to send message: " + e.getMessage();
        }
    }
}
