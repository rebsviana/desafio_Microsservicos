package com.ciandt.api.pedidos.exception;

public class PedidoJaCadastrado extends Exception {

    private static final String MSG = "Pedido ja cadastrado";
    public PedidoJaCadastrado() {
        super(MSG);
    }
}
