package com.gotham.batman.controllers;

import com.gotham.batman.models.Divida;
import com.gotham.batman.repository.DividaRepository;
import com.gotham.batman.service.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/dividas")
public class DividaController {

    @Autowired
    private DividaRepository dividaRepository;

    @Autowired
    private RabbitSender rabbitSender;

    @GetMapping
    public List<Divida> listarDividas() {
        return dividaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Divida> buscarDividaPorId(@PathVariable Long id) {
        Optional<Divida> divida = dividaRepository.findById(id);
        if (divida.isPresent()) {
            return ResponseEntity.ok(divida.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Divida criarDivida(@RequestBody Divida divida) {
        return dividaRepository.save(divida);
    }

    @PostMapping("{id}/gerar-documento")
    public ResponseEntity gerarDocumentoDivida(@PathVariable Long id) throws Exception {

        Optional<Divida> divida = dividaRepository.findById(id);
        if (divida.isEmpty()) {
            throw new Exception("Dívida não encontrada com o ID " + id);
        }

        Map params = new HashMap();
        params.put("idDivida", divida.get().getId());

        rabbitSender.send("gerar-documento-divida", params);

        return ResponseEntity.accepted().build();
    }

    @PostMapping("gerar-documento-lote")
    public ResponseEntity gerarDocumentoDividaTodosRegistros() throws Exception {
        List<Divida> dividas = dividaRepository.findAll();

        dividas.forEach(divida -> {
            Map params = new HashMap();
            params.put("idDivida", divida.getId());

            rabbitSender.send("gerar-documento-divida", params);
        });

        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Divida> atualizarDivida(@PathVariable Long id, @RequestBody Divida dividaAtualizada) {
        Optional<Divida> dividaExistente = dividaRepository.findById(id);
        if (dividaExistente.isPresent()) {
            Divida divida = dividaExistente.get();
            divida.setDevedor(dividaAtualizada.getDevedor());
            divida.setCredor(dividaAtualizada.getCredor());
            divida.setValor(dividaAtualizada.getValor());
            divida.setDataVencimento(dividaAtualizada.getDataVencimento());
            divida.setStatus(dividaAtualizada.getStatus());
            divida.setHashDocumento(dividaAtualizada.getHashDocumento());

            return ResponseEntity.ok(dividaRepository.save(divida));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDivida(@PathVariable Long id) {
        Optional<Divida> divida = dividaRepository.findById(id);
        if (divida.isPresent()) {
            dividaRepository.delete(divida.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}