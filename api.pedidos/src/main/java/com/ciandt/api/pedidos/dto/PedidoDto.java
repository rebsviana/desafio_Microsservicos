package com.ciandt.api.pedidos.dto;

import com.ciandt.api.pedidos.model.ItemPedido;
import com.ciandt.api.pedidos.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Builder
@Data
public class PedidoDto {

    @ApiModelProperty(example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "2022-08-16", required = true)
    private LocalDate dataHora;

    @ApiModelProperty(example = "CONFIRMADO", required = true)
    @NotNull(message = "Status não pode ser vazio")
    private Status status;

    @JsonIgnore
    Set<ItemPedido> itens;
}
