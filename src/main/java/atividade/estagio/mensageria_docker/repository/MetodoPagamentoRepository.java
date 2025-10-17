package atividade.estagio.mensageria_docker.repository;


import atividade.estagio.mensageria_docker.entity.MetodoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamento, Long> {
    List<MetodoPagamento> findByClienteId(Long clienteId);

}
