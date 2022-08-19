package com.ciandt.api.pedidos.controller;

import com.ciandt.api.pedidos.dto.ErrorDto;
import com.ciandt.api.pedidos.exception.PedidoJaCadastrado;
import com.ciandt.api.pedidos.exception.PedidoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler controllerExceptionHandler;

    @Test
    void whenPedidoNotFoundExceptionThenReturnResponseEntity() {
        ResponseEntity<ErrorDto> response = controllerExceptionHandler
                    .handlePedidoNotFoundException(new PedidoNotFoundException());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ErrorDto.class, response.getBody().getClass());
        assertEquals(PedidoNotFoundException.MSG, response.getBody().getMessage());
        assertEquals(404, response.getBody().getCode());
    }

    @Test
    void whenPedidoJaCadastradoExceptionThenReturnResponseEntity() {
        ResponseEntity<ErrorDto> response = controllerExceptionHandler
                .handlePedidoJaCadastradoException(new PedidoJaCadastrado());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ErrorDto.class, response.getBody().getClass());
        assertEquals(PedidoJaCadastrado.MSG, response.getBody().getMessage());
        assertEquals(400, response.getBody().getCode());
    }
}