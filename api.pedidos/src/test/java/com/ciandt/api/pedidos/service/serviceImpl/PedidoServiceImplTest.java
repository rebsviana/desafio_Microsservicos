package com.ciandt.api.pedidos.service.serviceImpl;

import com.ciandt.api.pedidos.enums.Status;
import com.ciandt.api.pedidos.exception.PedidoJaCadastrado;
import com.ciandt.api.pedidos.exception.PedidoNotFoundException;
import com.ciandt.api.pedidos.model.Pedido;
import com.ciandt.api.pedidos.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class PedidoServiceImplTest {

    public static final Long ID = Long.valueOf(1);
    public static final LocalDate LOCAL_DATE = LocalDate.now();
    public static final Status STATUS = Status.CONFIRMADO;

    @InjectMocks
    private PedidoServiceImpl service;

    @Mock
    private PedidoRepository repository;

    private Pedido pedido;

    private Optional<Pedido> optionalPedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido(ID, LOCAL_DATE, STATUS);
        optionalPedido = Optional.of(new Pedido(ID, LOCAL_DATE, STATUS));
    }

    @Test
    void whenFindByIdThenReturnAnOrderInstance() {
        when(repository.findById(anyLong())).thenReturn(optionalPedido);

        var response = service.getPedido(ID);

        assertNotNull(response);
        assertEquals(Pedido.class, response.getClass());
        assertEquals(ID, response.getId());
    }

    @Test
    void whenFindByIdThenReturnAnOrderNotFoundException() throws PedidoNotFoundException {
        when(repository.findById(anyLong())).thenThrow(new PedidoNotFoundException());

        Exception response = assertThrows(
                PedidoNotFoundException.class,
                () -> service.getPedido(ID),
                "Excecao nao encontrada"
        );

        assertNotNull(response);
        assertEquals(PedidoNotFoundException.class, response.getClass());
        assertEquals(PedidoNotFoundException.MSG, response.getMessage());
    }
    @Test
    void whenFindAllThenReturnAnListOfOrders() {
        when(repository.findAll()).thenReturn(List.of(pedido));

        var response = service.getAllPedido();

        assertNotNull(response);
        assertEquals(1,response.size());
        assertEquals(Pedido.class, response.get(0).getClass());
        assertEquals(pedido, response.get(0));
        assertEquals(ID, response.get(0).getId());
        assertEquals(STATUS, response.get(0).getStatus());
        assertEquals(LOCAL_DATE, response.get(0).getDataHora());
    }

    @Test
    void whenCreateAnOrderThenReturnAnOrder() throws PedidoJaCadastrado {
        when(repository.save(any())).thenReturn(pedido);

        var response = service.createPedido(pedido);

        assertNotNull(response);
        assertEquals(Pedido.class, response.getClass());
        assertEquals(pedido, response);
        assertEquals(ID, response.getId());
        assertEquals(STATUS, response.getStatus());
        assertEquals(LOCAL_DATE, response.getDataHora());
    }

    @Test
    void whenCreateAnOrderIfExistThenReturnPedidoJaCadastradoException() throws PedidoJaCadastrado {
        when(repository.findById(anyLong())).thenReturn(optionalPedido);

        optionalPedido.get().setId(2L);

        Exception exception = assertThrows(
                PedidoJaCadastrado.class,
                () -> service.createPedido(pedido),
                "Excecao nao encontrada"
        );

        assertNotNull(exception);
        assertEquals(PedidoJaCadastrado.class, exception.getClass());
        assertEquals(PedidoJaCadastrado.MSG, exception.getMessage());
    }

    @Test
    void whenUpdateAnOrderThenReturnAnOrder() {
        when(repository.findById(anyLong())).thenReturn(optionalPedido);
        when(repository.save(Mockito.any())).thenReturn(pedido);

        var response = service.updatePedido(ID, pedido);

        assertNotNull(response);
        assertEquals(Pedido.class, response.getClass());
        assertEquals(pedido, response);
        assertEquals(ID, response.getId());
        assertEquals(STATUS, response.getStatus());
        assertEquals(LOCAL_DATE, response.getDataHora());
    }

    @Test
    void whenUpdateAnOrderIfExistThenReturnPedidoNotFoundException() {
        when(repository.findById(anyLong())).thenThrow(new PedidoNotFoundException());

        Exception exception = assertThrows(
                PedidoNotFoundException.class,
                () -> service.updatePedido(ID, pedido),
                "Excecao nao encontrada"
        );

        assertNotNull(exception);
        assertEquals(PedidoNotFoundException.class, exception.getClass());
        assertEquals(PedidoNotFoundException.MSG, exception.getMessage());
    }

    @Test
    void whenUpdateStatusAnOrderThenSaveAnOrder() {
        when(repository.findById(anyLong())).thenReturn(optionalPedido);
        when(repository.save(Mockito.any())).thenReturn(pedido);

        pedido.setStatus(Status.PAGO);

        var response = service.updateStatusPedido(ID, Status.PAGO);

        assertNotNull(response);
        assertEquals(Pedido.class, response.getClass());
        assertEquals(pedido, response);
        assertEquals(ID, response.getId());
        assertEquals(Status.PAGO, response.getStatus());
        assertEquals(LOCAL_DATE, response.getDataHora());
    }

    @Test
    void whenUpdateStatusAnOrderIfExistThenReturnPedidoNotFoundException() {
        when(repository.findById(anyLong())).thenThrow(new PedidoNotFoundException());

        Exception exception = assertThrows(
                PedidoNotFoundException.class,
                () -> service.updateStatusPedido(ID, Status.PAGO),
                "Excecao nao encontrada"
        );

        assertNotNull(exception);
        assertEquals(PedidoNotFoundException.class, exception.getClass());
        assertEquals(PedidoNotFoundException.MSG, exception.getMessage());
    }

    @Test
    void whenDeleteAnOrderThenDeleteSucess() {
        when(repository.findById(anyLong())).thenReturn(optionalPedido);

        doNothing().when(repository).delete(Mockito.any(Pedido.class));

        service.deletePedido(ID);

        verify(repository, times(1)).delete(Mockito.any(Pedido.class));
    }

    @Test
    void whenDeleteAnOrderIfNotExistThenRuturnPedidoNotFoundException() {
        when(repository.findById(anyLong())).thenThrow(new PedidoNotFoundException());

        Exception response = assertThrows(
                PedidoNotFoundException.class,
                () -> service.deletePedido(ID),
                "Excecao nao encontrada"
        );

        assertNotNull(response);
        assertEquals(PedidoNotFoundException.class, response.getClass());
        assertEquals(PedidoNotFoundException.MSG, response.getMessage());
    }
}