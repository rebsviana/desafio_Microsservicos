package com.ciandt.api.pagamento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PagamentoNotFoundException extends Exception {

    public static final String MSG = "Payment not found";
    public PagamentoNotFoundException() {
        super(MSG);
    }
}
