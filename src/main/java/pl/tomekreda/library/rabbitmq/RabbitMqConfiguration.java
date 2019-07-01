package pl.tomekreda.library.rabbitmq;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class RabbitMqConfiguration {

    public final static String SFG_MESSAGE_QUEUE = "sfg-message-queue";

    @Bean
    Queue queue() {
        return new Queue(SFG_MESSAGE_QUEUE, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(SFG_MESSAGE_QUEUE);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory
    ) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(SFG_MESSAGE_QUEUE);
        container.setMessageListener(null);
        return container;
    }
    @Bean
    @Primary
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        return builder.modulesToInstall(new JavaTimeModule());
    }
}
