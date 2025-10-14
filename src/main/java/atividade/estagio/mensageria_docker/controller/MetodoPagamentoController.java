package atividade.estagio.mensageria_docker.controller;


import atividade.estagio.mensageria_docker.entity.MetodoPagamento;
import atividade.estagio.mensageria_docker.service.MetodoPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/metodos-pagamento")
public class MetodoPagamentoController {


    @Autowired
    private MetodoPagamentoService metodoPagamentoService;


    @GetMapping()
    public ResponseEntity<List<MetodoPagamento>> listarPagamentos(){
        List <MetodoPagamento> lista = metodoPagamentoService.listarTodosPagamentos();
        return ResponseEntity.status(200).body(lista);
    }

    @PostMapping()
    public ResponseEntity<MetodoPagamento> novoPagamento(@RequestBody MetodoPagamento pagamento){
        MetodoPagamento novoPagamento = metodoPagamentoService.cadastrarPagamento(pagamento);
        return ResponseEntity.status(201).body(novoPagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPagamento> editarPagamento(@RequestBody MetodoPagamento pagamento){
        MetodoPagamento novoPagamento = metodoPagamentoService.editarPagamento(pagamento);
        return ResponseEntity.status(200).body(novoPagamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MetodoPagamento> excluirPagamento(@PathVariable Long id ){
        metodoPagamentoService.excluirPagamento(id);
        return ResponseEntity.noContent().build();

    }
}
