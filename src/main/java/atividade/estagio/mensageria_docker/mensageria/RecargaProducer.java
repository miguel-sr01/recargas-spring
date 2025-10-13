package atividade.estagio.mensageria_docker.mensageria;

import atividade.estagio.mensageria_docker.entity.Recarga;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// Envia mensagens (API → RabbitMQ)

@Service
public class RecargaProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void enviarRecarga(Recarga recarga) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                recarga
        );
        System.out.println("📤 Mensagem enviada para fila: " + recarga.getNumeroCelular());
    }
}
