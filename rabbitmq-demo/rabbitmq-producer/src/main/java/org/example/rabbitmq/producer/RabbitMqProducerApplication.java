package org.example.rabbitmq.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * desc:
 *
 * @author Rao
 * @Date 2022/02/28
 **/
@SpringBootApplication
public class RabbitMqProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run( RabbitMqProducerApplication.class,args);
    }
}
