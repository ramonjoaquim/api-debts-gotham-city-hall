package com.gotham.batman.repository;

import com.gotham.batman.models.DividaReceita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DividaReceitaRepository extends JpaRepository<DividaReceita, Long> {
    // Métodos customizados podem ser adicionados aqui se necessário
}