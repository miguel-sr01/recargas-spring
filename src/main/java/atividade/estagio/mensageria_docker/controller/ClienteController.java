package atividade.estagio.mensageria_docker.controller;


import atividade.estagio.mensageria_docker.entity.Cliente;
import atividade.estagio.mensageria_docker.entity.MetodoPagamento;
import atividade.estagio.mensageria_docker.entity.Recarga;
import atividade.estagio.mensageria_docker.repository.MetodoPagamentoRepository;
import atividade.estagio.mensageria_docker.service.ClienteService;
import atividade.estagio.mensageria_docker.service.MetodoPagamentoService;
import atividade.estagio.mensageria_docker.service.RecargaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MetodoPagamentoService metodoPagamentoService;

    @Autowired
    private RecargaService recargaService;


    @GetMapping
    public ResponseEntity<List <Cliente>> listarClientes(){
        List <Cliente> lista = clienteService.listarClientes();
        return ResponseEntity.status(200).body(lista);
    }

    @GetMapping("/{id}/metodos-pagamento")
    public ResponseEntity<List <MetodoPagamento>> listarMetodoPagamentoDeCliente(@PathVariable Long id){
        List <MetodoPagamento> metodos = metodoPagamentoService.listarClientesId(id);
        return ResponseEntity.status(200).body(metodos);
    }

    @GetMapping("/{id}/recargas")
    public ResponseEntity<List <Recarga>> listarRecargasDeCliente(@PathVariable Long id){
        List <Recarga> recargas = recargaService.listarClientesId(id);
        return ResponseEntity.status(200).body(recargas);
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










