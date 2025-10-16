package atividade.estagio.mensageria_docker.mensageria;

import atividade.estagio.mensageria_docker.entity.Recarga;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// Essa classe é o produtor, ou seja, quem envia mensagens para o RabbitMQ.
@Service
public class RecargaProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate; //Pede ao Spring para injetar o RabbitTemplate configurado na RabbitMQConfig.

    Gson gson = new GsonBuilder().setPrettyPrinting().create();


    //Envia o objeto Recarga como JSON:
    public void enviarRecarga(Recarga recarga) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                recarga
        );
        String jsonFormatado = gson.toJson(recarga);
        System.out.println("Recarga do celular: " + jsonFormatado + " entrou na fila da mensageria");
    }
}
