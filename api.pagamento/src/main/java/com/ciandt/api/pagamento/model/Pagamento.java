package com.ciandt.api.pagamento.model;

import com.ciandt.api.pagamento.enums.FormaDePagamento;
import com.ciandt.api.pagamento.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;

    private String nome;

    private String numero;

    private String expiracao;

    private String codigo;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long pedidoId;

    @Enumerated(EnumType.STRING)
    private FormaDePagamento formaDePagamento;
}
