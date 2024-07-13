package Kafka.ProdAndConsume.Controller;

import Kafka.ProdAndConsume.Service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    ProducerService producerService;

    @GetMapping("/send")
    public String SendMessage(@RequestParam String message) {
        return producerService.SendMessage(message);
    }
}
