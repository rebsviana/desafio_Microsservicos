package com.ciandt.api.pedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PedidoJaCadastrado extends Exception {

    public static final String MSG = "Pedido ja cadastrado";
    public PedidoJaCadastrado() {
        super(MSG);
    }
}
