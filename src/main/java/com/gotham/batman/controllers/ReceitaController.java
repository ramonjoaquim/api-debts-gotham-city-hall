package com.gotham.batman.controllers;

import com.gotham.batman.models.DividaReceita;
import com.gotham.batman.repository.DividaReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dividas-receitas")
public class ReceitaController {

    @Autowired
    private DividaReceitaRepository receitaRepository;

    @GetMapping
    public List<DividaReceita> listarReceitas() {
        return receitaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DividaReceita> buscarReceitaPorId(@PathVariable Long id) {
        Optional<DividaReceita> receita = receitaRepository.findById(id);
        if (receita.isPresent()) {
            return ResponseEntity.ok(receita.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public DividaReceita criarReceita(@RequestBody DividaReceita receita) {
        return receitaRepository.save(receita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DividaReceita> atualizarReceita(@PathVariable Long id, @RequestBody DividaReceita receitaAtualizada) {
        Optional<DividaReceita> receitaExistente = receitaRepository.findById(id);
        if (receitaExistente.isPresent()) {
            DividaReceita receita = receitaExistente.get();
            receita.setValorRecebido(receitaAtualizada.getValorRecebido());
            receita.setDataRecebimento(receitaAtualizada.getDataRecebimento());

            return ResponseEntity.ok(receitaRepository.save(receita));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReceita(@PathVariable Long id) {
        Optional<DividaReceita> receita = receitaRepository.findById(id);
        if (receita.isPresent()) {
            receitaRepository.delete(receita.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}