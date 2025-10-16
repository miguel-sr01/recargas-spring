package atividade.estagio.mensageria_docker.mensageria;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "recarga_queue"; //Fila (queue): onde as mensagens vão ficar armazenadas.
    public static final String EXCHANGE = "recarga_exchange"; //Exchange: ponto intermediário que recebe as mensagens e decide para qual fila enviar.
    public static final String ROUTING_KEY = "recarga_routingKey"; //outing key: uma “chave” usada para ligar o exchange à fila.

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true); //Cria uma fila chamada "recarga_queue" com persistência habilitada
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE); //Cria um exchange do tipo Topic
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY); //“Amarra” a fila ao exchange, usando a routing key definida antes.
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
