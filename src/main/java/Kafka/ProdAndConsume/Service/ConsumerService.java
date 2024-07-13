package Kafka.ProdAndConsume.Service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {


    @KafkaListener(topics ="#{T(java.util.Collections).singletonList('${kafka.topic.name}')}" , groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }
}
