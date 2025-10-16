package atividade.estagio.mensageria_docker.service;


import atividade.estagio.mensageria_docker.entity.Cliente;
import atividade.estagio.mensageria_docker.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }

    public Cliente novoCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente editarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public void excluirCliente(Long id){
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("ID de cliente não existe!");
        }
        clienteRepository.deleteById(id);
    }

}
