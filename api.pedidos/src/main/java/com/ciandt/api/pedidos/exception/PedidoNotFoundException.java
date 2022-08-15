package com.ciandt.api.pedidos.exception;

public class PedidoNotFoundException extends Exception {

    private static final String MSG = "Pedido not found";
    public PedidoNotFoundException() {
        super(MSG);
    }
}
