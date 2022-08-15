package com.ciandt.api.pagamento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PagamentoNotFoundException extends Exception {
    public PagamentoNotFoundException(String message) {
        super(message);
    }
}
