package com.ciandt.api.pedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PedidoNotFoundException extends RuntimeException {

    public static final String MSG = "Order not found";
    public PedidoNotFoundException() {
        super(MSG);
    }
}
