package atividade.estagio.mensageria_docker.mensageria;

import atividade.estagio.mensageria_docker.entity.Recarga;
import atividade.estagio.mensageria_docker.entity.StatusRecarga;
import atividade.estagio.mensageria_docker.repository.RecargaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

//classe consumidora, escuta a fila e processa as mensagens que chegam.
@Service
public class RecargaConsumer {

    @Autowired
    private RecargaRepository recargaRepository; //Usa o repositório JPA para salvar o resultado do processamento da recarga (sucesso ou falha).

    private final Random random = new Random();


    @RabbitListener(queues = RabbitMQConfig.QUEUE1)
    public void processarRecargaGeral(Recarga recarga) {
        processar(recarga, "GERAL");
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE2)
    public void processarRecargaEspecial(Recarga recarga) {
        processar(recarga, "ESPECIAL");
    }

    public void processar(Recarga recarga, String tipoFila) {
        try {
            System.out.println("[" + tipoFila + "] Mensagem recebida, aguardando 10 segundos para processar...");
            Thread.sleep(10000); // ⏳ espera 5 segundos antes de processar
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boolean sucesso = random.nextBoolean();
        if (sucesso) {
            recarga.setStatus(StatusRecarga.SUCESSO);
        }else{
            recarga.setStatus(StatusRecarga.FALHA);
        }

        recargaRepository.save(recarga);


        if (sucesso) {
            System.out.println("[" + tipoFila + "] Recarga realizada com SUCESSO para o número: " + recarga.getNumeroCelular());

        }else{
            System.out.println("[" + tipoFila + "] Recarga FALHOU para o número: " + recarga.getNumeroCelular());
        }
    }


}
