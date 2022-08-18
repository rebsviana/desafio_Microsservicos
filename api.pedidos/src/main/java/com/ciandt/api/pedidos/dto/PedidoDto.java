package com.ciandt.api.pedidos.dto;

import com.ciandt.api.pedidos.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {

    @ApiModelProperty(example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "2022-08-16", required = true)
    private LocalDate dataHora;

    @ApiModelProperty(example = "CONFIRMADO", required = true)
    @NotNull(message = "Status não pode ser vazio")
    private Status status;
}

