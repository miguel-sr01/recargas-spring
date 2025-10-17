package atividade.estagio.mensageria_docker.service;


import atividade.estagio.mensageria_docker.entity.MetodoPagamento;
import atividade.estagio.mensageria_docker.entity.Recarga;
import atividade.estagio.mensageria_docker.entity.StatusRecarga;
import atividade.estagio.mensageria_docker.mensageria.RecargaProducer;
import atividade.estagio.mensageria_docker.repository.MetodoPagamentoRepository;
import atividade.estagio.mensageria_docker.repository.RecargaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecargaService {

    @Autowired
    private RecargaProducer recargaProducer;

    @Autowired
    private RecargaRepository recargaRepository;

    @Autowired
    private MetodoPagamentoRepository metodoPagamentoRepository;

    public List <Recarga> listarRecargas(){
        return recargaRepository.findAll();
    }

    public List <Recarga> listarClientesId(Long id){
        return recargaRepository.findByClienteId(id);
    }

    public Recarga novaRecarga(Recarga recarga){
        validarMetodoPagamento(recarga);
        recarga.setStatus(StatusRecarga.PENDENTE);
        Recarga salva = recargaRepository.save(recarga);
        recargaProducer.enviarRecarga(salva);
        return salva;
    }

    private void validarMetodoPagamento(Recarga recarga) {
        Long metodoId = recarga.getMetodoPagamento().getId();
        Long clienteId = recarga.getCliente().getId();

        MetodoPagamento metodo = metodoPagamentoRepository.findById(metodoId)
                .orElseThrow(() -> new IllegalArgumentException("Método de pagamento não encontrado"));

        if (!metodo.getCliente().getId().equals(clienteId)) {
            throw new IllegalArgumentException("Método de pagamento não pertence ao cliente informado");
        }
    }


    public Recarga editarRecarga(Recarga recarga){
        return recargaRepository.save(recarga);
    }

    public void excluirRecarga(Long id){
        if (!recargaRepository.existsById(id)) {
            throw new RuntimeException("Id de recarga inexistente");
        }
        recargaRepository.deleteById(id);
    }
}
