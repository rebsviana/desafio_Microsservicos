package com.ciandt.api.pagamento.controller;

import com.ciandt.api.pagamento.dto.PagamentoDto;
import com.ciandt.api.pagamento.exception.PagamentoNotFoundException;
import com.ciandt.api.pagamento.model.Pagamento;
import com.ciandt.api.pagamento.service.PagamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
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

@Api(value = "Pagamento")
@RequiredArgsConstructor
@RequestMapping("api/pagamento")
@RestController
public class PagamentoController {

    private final PagamentoService service;

    private final ObjectMapper mapper;

    @ApiOperation(value = "Get a payment by id", notes = "Returns a payment as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = PagamentoNotFoundException.MSG),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> getPagamento(@PathVariable Long id) throws PagamentoNotFoundException {
        final var pagamento = service.getPagamento(id);

        return ResponseEntity.ok(pagamento);
    }

    @ApiOperation(value = "Get all payments", notes = "Returns all payments")
    @GetMapping
    public ResponseEntity<List<PagamentoDto>> getAllPagamentos(){
        final var pagamentoList = service.getAllPagamentos();

        return ResponseEntity.ok(pagamentoList.stream()
                .map(pagamento -> mapper.convertValue(pagamento, PagamentoDto.class))
                .collect(Collectors.toList()));
    }


    @ApiOperation(value = "Save a payment", notes = "Saves a new payment")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Created"),
//            @ApiResponse(code = 400, message = "Argument not valid"),
//            @ApiResponse(code = 500, message = "Internal Server Error")
//    })
    @PostMapping
    public ResponseEntity<Void> savePagamento(@Valid @RequestBody PagamentoDto pagamentoDto){
        var pagamento = mapper.convertValue(pagamentoDto, Pagamento.class);

        var pagamentoSave = service.savePagamento(pagamento);

        final var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pagamentoSave.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Update a payment by id", notes = "Updates a payment as per the id")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePagamento(@PathVariable Long id, @Valid @RequestBody PagamentoDto pagamentoDto) throws PagamentoNotFoundException {
        var pagamentoMapper = mapper.convertValue(pagamentoDto, Pagamento.class);
        service.updatePagamento(id, pagamentoMapper);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Delete a payment by id", notes = "Deletes a payment as per the id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) throws PagamentoNotFoundException {
        service.deletePagamento(id);

        return ResponseEntity.noContent().build();
    }
}
