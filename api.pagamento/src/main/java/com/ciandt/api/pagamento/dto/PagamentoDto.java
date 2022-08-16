package com.ciandt.api.pagamento.dto;

import com.ciandt.api.pagamento.enums.FormaDePagamento;
import com.ciandt.api.pagamento.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Builder
@Data
public class PagamentoDto {

    @ApiModelProperty(example = "1")
    private Long id;

    @NotNull (message = "O valor nao pode ser vazio")
    @Positive (message = "O valor deve ser maior ou igual a zero")
    @ApiModelProperty(example = "10.0", required = true)
    private BigDecimal valor;

    @NotNull (message = "O nome não pode ser vazio")
    @Max(value = 100, message = "O nome deve ter no máximo 100 caracteres")
//    @ApiModelProperty(example = "Maria", required = true)
    private String nome;

    @NotBlank (message = "O número não pode ser vazio")
    @Max(value = 100, message = "O numero deve ter no máximo 100 caracteres")
//    @ApiModelProperty(example = "124578", required = true)
    private String numero;

    @ApiModelProperty(example = "08/09/2022")
    private String expiracao;

    @NotBlank (message = "O código não pode ser vazio")
    @Size(min = 3, max = 3, message = "O código deve ter 3 caracteres")
    @ApiModelProperty(example = "abc", required = true)
    private String codigo;

    @NotNull (message = "O status não pode ser vazio")
    @ApiModelProperty(example = "CRIADO", required = true)
    private Status status;

    @ApiModelProperty(example = "3")
    private Long pedidoId;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(example = "PIX")
    private FormaDePagamento formaDePagamento;
}
