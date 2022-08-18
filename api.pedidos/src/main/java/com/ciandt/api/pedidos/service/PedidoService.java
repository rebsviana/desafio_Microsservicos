package com.ciandt.api.pedidos.service;

import com.ciandt.api.pedidos.exception.PedidoJaCadastrado;
import com.ciandt.api.pedidos.exception.PedidoNotFoundException;
import com.ciandt.api.pedidos.model.Pedido;
import com.ciandt.api.pedidos.enums.Status;

import java.util.List;

public interface PedidoService {
    Pedido getPedido(Long id) throws PedidoNotFoundException;

    List<Pedido> getAllPedido();

    Pedido createPedido(Pedido pedido) throws PedidoJaCadastrado;

    Pedido updatePedido(Long id, Pedido pedido) throws PedidoNotFoundException;

    Pedido updateStatusPedido(Long id, Status status) throws PedidoNotFoundException;

    void deletePedido(Long id) throws PedidoNotFoundException;
}
