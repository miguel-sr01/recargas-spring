package atividade.estagio.mensageria_docker.repository;


import atividade.estagio.mensageria_docker.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository <Cliente, Long> {

}
