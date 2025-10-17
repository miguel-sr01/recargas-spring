package atividade.estagio.mensageria_docker.mensageria;

import atividade.estagio.mensageria_docker.entity.Recarga;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// Essa classe é o produtor, ou seja, quem envia mensagens para o RabbitMQ.
@Service
public class RecargaProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate; //Pede ao Spring para injetar o RabbitTemplate configurado na RabbitMQConfig.

    //Envia o objeto Recarga como JSON:
    public void enviarRecarga(Recarga recarga) {
        if (recarga.getValor() < 100) {
            // Se o valor da recarga for inferior a 100, vai para a fila geral.
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY_GERAL,
                    recarga
            );
            System.out.println("RECARGA COMUM do celular: "+ recarga.getNumeroCelular() +  " entrou na fila de processamento da mensageria");

        }else{
            // Se o valor da recarga for maior ou igual a 100, vai para a fila especial.
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY_ESPECIAL,
                    recarga
            );
            System.out.println("RECARGA ESPECIAL do celular: "+ recarga.getNumeroCelular() +  " entrou na fila de processamento da mensageria");
        }
    }
}
