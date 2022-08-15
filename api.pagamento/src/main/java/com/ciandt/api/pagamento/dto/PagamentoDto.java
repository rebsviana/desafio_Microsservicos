package com.ciandt.api.pagamento.dto;

import com.ciandt.api.pagamento.enums.FormaDePagamento;
import com.ciandt.api.pagamento.enums.Status;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Builder
@Data
public class PagamentoDto {

    private Long id;

    @NotNull (message = "O valor nao pode ser vazio")
    @Positive (message = "O valor deve ser maior ou igual a zero")
    private BigDecimal valor;

    @NotNull (message = "O nome não pode ser vazio")
    @Max(value = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank (message = "O número não pode ser vazio")
    @Max(value = 100, message = "O numero deve ter no máximo 100 caracteres")
    private String numero;

    private String expiracao;

    @NotBlank (message = "O código não pode ser vazio")
    @Size(min = 3, max = 3, message = "O código deve ter no minimo e máximo 3 caracteres")
    private String codigo;

    @NotNull (message = "O status não pode ser vazio")
    private Status status;

    private Long pedidoId;

    @Enumerated(EnumType.STRING)
    private FormaDePagamento formaDePagamento;
}
