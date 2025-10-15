package atividade.estagio.mensageria_docker.repository;


import atividade.estagio.mensageria_docker.entity.Recarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecargaRepository extends JpaRepository <Recarga, Long> {
    List<Recarga> findByClienteId(Long clienteId);
}
