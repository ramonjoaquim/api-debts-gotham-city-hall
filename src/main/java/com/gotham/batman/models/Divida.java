package com.gotham.batman.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gotham.batman.enums.StatusDivida;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "dividas")
public class Divida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String devedor;

    @Column(nullable = false)
    private String credor;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    @Enumerated(EnumType.STRING) // Mapeia o enum como String no banco de dados
    @Column(nullable = false)
    private StatusDivida status;

    @Column(nullable = true)
    private String hashDocumento;

    @Column(nullable = true)
    private String hashAssinatura;

    @Column(nullable = true)
    private Boolean executouScript;

    @JsonIgnore
    @OneToMany(mappedBy = "divida", cascade = CascadeType.ALL)
    private List<DividaReceita> receitas;

}
