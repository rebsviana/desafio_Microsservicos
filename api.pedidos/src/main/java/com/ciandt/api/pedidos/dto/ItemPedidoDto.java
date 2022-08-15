package com.ciandt.api.pedidos.dto;

import com.ciandt.api.pedidos.model.Pedido;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ItemPedidoDto {

    private Integer quantidade;

    private String descricao;

    private Pedido pedido;
}
