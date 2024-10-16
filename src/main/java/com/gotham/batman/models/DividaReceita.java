package com.gotham.batman.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "dividas_receitas")
public class DividaReceita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "divida_id", nullable = false)
    private Divida divida;

    @Column(nullable = false)
    private BigDecimal valorRecebido;

    @Column(nullable = false)
    private LocalDate dataRecebimento;

}