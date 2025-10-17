package atividade.estagio.mensageria_docker.mensageria;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE1 = "queue_geral"; //Fila (queue): onde as mensagens vão ficar armazenadas.
    public static final String QUEUE2 = "queue_especial"; //Fila (queue): onde as mensagens vão ficar armazenadas.
    public static final String EXCHANGE = "recarga_exchange"; //Exchange: ponto intermediário que recebe as mensagens e decide para qual fila enviar.
    public static final String ROUTING_KEY_GERAL = "routing_key_geral"; //Routing key: uma “chave” usada para ligar o exchange à fila.
    public static final String ROUTING_KEY_ESPECIAL = "routing_key_especial"; //Routing key: uma “chave” usada para ligar o exchange à fila.


    @Bean
    public Queue queue_geral() {
        return new Queue(QUEUE1, true); //Cria uma fila chamada "queue_geral" com persistência habilitada
    }

    @Bean
    public Queue queue_especial() {
        return new Queue(QUEUE2, true); //Cria uma fila chamada "queue_especial" com persistência habilitada
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE); //Cria um exchange do tipo Topic
    }

    @Bean
    public Binding bindingQueue1(@Qualifier("queue_geral") Queue queue1, TopicExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with(ROUTING_KEY_GERAL); //“Amarra” a fila ao exchange, usando a routing_key_geral
    }

    @Bean
    public Binding bindingQueue2(@Qualifier("queue_especial") Queue queue2, TopicExchange exchange) {
        return BindingBuilder.bind(queue2).to(exchange).with(ROUTING_KEY_ESPECIAL); //“Amarra” a fila ao exchange, usando a routing_key_especial
    }


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(); //Converte automaticamente objetos Java em JSON ao enviar, e JSON de volta em objeto Java ao receber.
    }

    //Cria o objeto que a aplicação usa para enviar mensagens (RabbitTemplate), e garante que ele use o conversor JSON configurado acima.
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
