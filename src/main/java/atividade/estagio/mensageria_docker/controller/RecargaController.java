package atividade.estagio.mensageria_docker.controller;

import atividade.estagio.mensageria_docker.entity.Recarga;
import atividade.estagio.mensageria_docker.service.RecargaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recargas")
public class RecargaController {


    @Autowired
    private RecargaService recargaService;


    @GetMapping
    public ResponseEntity<List<Recarga>> listarRecargas(){
        List <Recarga> lista = recargaService.listarRecargas();
        return ResponseEntity.status(200).body(lista);
    }

    @PostMapping
    public ResponseEntity<Recarga> novaRecarga(@RequestBody Recarga recarga){
        Recarga novaRecarga = recargaService.cadastrarRecarga(recarga);
        return ResponseEntity.status(201).body(novaRecarga);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recarga> editarRecarga(@RequestBody Recarga recarga){
        Recarga novaRecarga = recargaService.editarRecarga(recarga);
        return ResponseEntity.status(200).body(novaRecarga);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Recarga> excluirRecarga(@PathVariable Long id) throws Exception {
        recargaService.excluirRecarga(id);
        return ResponseEntity.noContent().build();

    }
}
