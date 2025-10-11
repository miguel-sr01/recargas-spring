package atividade.estagio.mensageria_docker.service;


import atividade.estagio.mensageria_docker.entity.Recarga;
import atividade.estagio.mensageria_docker.repository.RecargaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecargaService {

    @Autowired
    private RecargaRepository recargaRepository;

    public List <Recarga> listarRecargas(){
        return recargaRepository.findAll();
    }

    public Recarga cadastrarRecarga(Recarga recarga){
        return recargaRepository.save(recarga);
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
