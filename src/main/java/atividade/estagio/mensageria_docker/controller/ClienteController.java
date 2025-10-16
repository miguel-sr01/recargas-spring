package atividade.estagio.mensageria_docker.controller;


import atividade.estagio.mensageria_docker.entity.Cliente;
import atividade.estagio.mensageria_docker.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @GetMapping
    public ResponseEntity<List <Cliente>> listarClientes(){
        List <Cliente> lista = clienteService.listarClientes();
        return ResponseEntity.status(200).body(lista);
    }

    @PostMapping
    public ResponseEntity<Cliente> novoCliente(@RequestBody Cliente cliente){
        Cliente novoCliente = clienteService.novoCliente(cliente);
        return ResponseEntity.status(201).body(novoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> editarCliente(@RequestBody Cliente cliente){
        Cliente novoCliente = clienteService.editarCliente(cliente);
        return ResponseEntity.status(200).body(novoCliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> excluirCliente(@PathVariable Long id) throws Exception {
        clienteService.excluirCliente(id);
        return ResponseEntity.noContent().build();

    }

}










