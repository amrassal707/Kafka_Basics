package Kafka.ProdAndConsume.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminKafka {


    @Bean
    public NewTopic topic1() {
        return new NewTopic("my-new-topic", 1, (short) 1);
    }
}


