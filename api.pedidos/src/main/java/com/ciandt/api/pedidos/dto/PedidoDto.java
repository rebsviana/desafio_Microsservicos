package com.ciandt.api.pedidos.dto;

import com.ciandt.api.pedidos.model.ItemPedido;
import com.ciandt.api.pedidos.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
public class PedidoDto {

    private Long id;

    private LocalDateTime dataHora;

    private Status status;

    @JsonIgnore
    Set<ItemPedido> itens;
}
