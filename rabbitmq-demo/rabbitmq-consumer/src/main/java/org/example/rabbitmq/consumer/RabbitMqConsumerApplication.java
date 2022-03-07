package org.example.rabbitmq.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * desc: 一个服务有可能是消费者也有可能是生产者
 *
 * @author Rao
 * @Date 2022/02/28
 **/
@SpringBootApplication
public class RabbitMqConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run( RabbitMqConsumerApplication.class,args );
    }
}
