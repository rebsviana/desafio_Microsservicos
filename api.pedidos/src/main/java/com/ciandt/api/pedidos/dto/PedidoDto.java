package com.ciandt.api.pedidos.dto;

import com.ciandt.api.pedidos.model.ItemPedido;
import com.ciandt.api.pedidos.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
public class PedidoDto {

    private Long id;

    private LocalDateTime dataHora;

    @NotNull(message = "Status não pode ser vazio")
    private Status status;

    @JsonIgnore
    Set<ItemPedido> itens;
}
