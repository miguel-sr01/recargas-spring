package atividade.estagio.mensageria_docker.mensageria;

import atividade.estagio.mensageria_docker.entity.Recarga;
import atividade.estagio.mensageria_docker.entity.StatusRecarga;
import atividade.estagio.mensageria_docker.repository.RecargaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RecargaConsumer {

    @Autowired
    private RecargaRepository recargaRepository;

    private final Random random = new Random();


    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void processarRecarga(Recarga recarga) {
        try {
            System.out.println("📥 Mensagem recebida, aguardando 5 segundos...");
            Thread.sleep(10000); // ⏳ espera 5 segundos antes de processar
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boolean sucesso = random.nextBoolean();
        recarga.setStatus(sucesso ? StatusRecarga.SUCESSO : StatusRecarga.FALHA);
        recargaRepository.save(recarga);

        System.out.println("💡 Recarga " + (sucesso ? "realizada com sucesso" : "falhou") +
                " para " + recarga.getNumeroCelular());
    }
}
