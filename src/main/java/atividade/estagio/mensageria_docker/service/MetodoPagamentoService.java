package atividade.estagio.mensageria_docker.service;


import atividade.estagio.mensageria_docker.entity.MetodoPagamento;
import atividade.estagio.mensageria_docker.repository.MetodoPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetodoPagamentoService {

    @Autowired
    private MetodoPagamentoRepository metodoPagamentoRepository;

    public List<MetodoPagamento> listarTodosPagamentos(){
        return metodoPagamentoRepository.findAll();
    }

    public MetodoPagamento novoPagamento(MetodoPagamento pagamento){
        return metodoPagamentoRepository.save(pagamento);
    }

    public MetodoPagamento editarPagamento(MetodoPagamento pagamento){
        return metodoPagamentoRepository.save(pagamento);
    }

    public void excluirPagamento(Long id){
        if (!metodoPagamentoRepository.existsById(id)) {
            throw new RuntimeException("ID do metodo de pagamento não existe!");
        }
        metodoPagamentoRepository.deleteById(id);
    }
}
