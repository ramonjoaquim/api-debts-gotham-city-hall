package com.gotham.batman.repository;

import com.gotham.batman.models.Divida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DividaRepository extends JpaRepository<Divida, Long> {
    // Métodos customizados podem ser adicionados aqui se necessário
}