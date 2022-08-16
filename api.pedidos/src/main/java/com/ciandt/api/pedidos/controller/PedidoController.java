package com.ciandt.api.pedidos.controller;

import com.ciandt.api.pedidos.dto.PedidoDto;
import com.ciandt.api.pedidos.exception.PedidoJaCadastrado;
import com.ciandt.api.pedidos.exception.PedidoNotFoundException;
import com.ciandt.api.pedidos.model.Pedido;
import com.ciandt.api.pedidos.enums.Status;
import com.ciandt.api.pedidos.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/pedidos", produces = "application/json")
public class PedidoController {

    private final PedidoService service;

    private final ObjectMapper mapper;

    @ApiOperation(value = "Get an order by id", notes = "Returns an order as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = PedidoNotFoundException.MSG),
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> getPedido (@PathVariable Long id) throws PedidoNotFoundException {
        final var pedido = service.getPedido(id);
        var pedidoDto = mapper.convertValue(pedido, PedidoDto.class);
        return ResponseEntity.ok(pedidoDto);
    }

    @ApiOperation(value = "Get all orders", notes = "Returns all orders")
    @GetMapping
    public ResponseEntity<List<PedidoDto>> getAllPedido (){
        final var allPedidos = service.getAllPedido();

        final var allPedidosDto = allPedidos.stream()
                .map(pedido -> mapper.convertValue(pedido, PedidoDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(allPedidosDto);
    }

    @ApiOperation(value = "Save an order", notes = "Saves a new order")
    @PostMapping
    public ResponseEntity<Void> createPedido(@Valid @RequestBody PedidoDto pedidoDto) throws PedidoJaCadastrado {
        final var pedido = mapper.convertValue(pedidoDto, Pedido.class);

        final var pagamentoSave = service.createPedido(pedido);

        final var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pagamentoSave.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Update an order by id", notes = "Updates an order as per the id")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePedido(@PathVariable Long id, @Valid @RequestBody PedidoDto pedidoDto) throws PedidoNotFoundException {
        final var pedido = mapper.convertValue(pedidoDto, Pedido.class);
        service.updatePedido(id,pedido);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Update an order by id and status", notes = "Updates aa order as per the id and status")
    @PutMapping("/{id}/{status}")
    public ResponseEntity<Void> updateStatusPedido(@PathVariable Long id, @Valid @PathVariable Status status) throws PedidoNotFoundException {
        service.updateStatusPedido(id,status);

        return ResponseEntity.ok().build();
    }
}
