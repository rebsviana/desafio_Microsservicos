package com.ciandt.api.pedidos.controller;

import com.ciandt.api.pedidos.dto.PedidoDto;
import com.ciandt.api.pedidos.enums.Status;
import com.ciandt.api.pedidos.model.Pedido;
import com.ciandt.api.pedidos.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class PedidoControllerTest {

    public static final Long ID = Long.valueOf(1);
    public static final LocalDate LOCAL_DATE = LocalDate.now();
    public static final Status STATUS = Status.CONFIRMADO;

    @InjectMocks
    private PedidoController controller;

    @Mock
    private PedidoService service;

    @Mock
    private ObjectMapper mapper;

    private PedidoDto pedidoDto;
    private Pedido pedido;


    @BeforeEach
    void setUp() {
        pedido = new Pedido(ID, LOCAL_DATE, STATUS);
        pedidoDto = new PedidoDto(ID, LOCAL_DATE, STATUS);
    }

    @Test
    void whenFindByIdThenReturnResponseEntityOK() {
        when(service.getPedido(ID)).thenReturn(pedido);
        when(mapper.convertValue(pedido, PedidoDto.class)).thenReturn(pedidoDto);

        var response = controller.getPedido(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(PedidoDto.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(STATUS, response.getBody().getStatus());
        assertEquals(LOCAL_DATE, response.getBody().getDataHora());
    }

    @Test
    void whenFindAllThenReturnAListOfUserDTO() {
        when(service.getAllPedido()).thenReturn(List.of(pedido));
        when(mapper.convertValue(pedido, PedidoDto.class)).thenReturn(pedidoDto);

        var response = controller.getAllPedido();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(PedidoDto.class, response.getBody().get(0).getClass());
        assertEquals(1, response.getBody().size());

        assertEquals(ID, response.getBody().get(0).getId());
        assertEquals(STATUS, response.getBody().get(0).getStatus());
        assertEquals(LOCAL_DATE, response.getBody().get(0).getDataHora());
    }

    @Test
    void whenCreateThenReturnCreated() {
        when(mapper.convertValue(pedidoDto, Pedido.class)).thenReturn(pedido);
        when(service.createPedido(pedido)).thenReturn(pedido);

        var response = controller.createPedido(pedidoDto);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdatePedidoThenReturnResponseEntityOk() {
        when(mapper.convertValue(pedidoDto, Pedido.class)).thenReturn(pedido);
        when(service.updatePedido(ID, pedido)).thenReturn(pedido);

        var response = controller.updatePedido(ID, pedidoDto);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenUpdateStatusPedidoThenReturnResponseEntityOk() {

//        service.updateStatusPedido(id,status);
//
//        return ResponseEntity.ok().build();
        when(service.updateStatusPedido(anyLong(), any())).thenReturn(pedido);

        when(mapper.convertValue(pedido, PedidoDto.class)).thenReturn(pedidoDto);

        pedidoDto.setStatus(Status.PAGO);

        var response = controller.updateStatusPedido(ID,Status.PAGO);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(PedidoDto.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(Status.PAGO, response.getBody().getStatus());
        assertEquals(LOCAL_DATE, response.getBody().getDataHora());
    }

    @Test
    void whenDeletePedidoThenReturnResponseEntityNoContent() {
        doNothing().when(service).deletePedido(ID);

        var response = controller.deletePedido(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deletePedido(anyLong());
    }
}