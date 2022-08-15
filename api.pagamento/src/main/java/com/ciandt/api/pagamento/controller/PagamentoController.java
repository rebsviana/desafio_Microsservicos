package com.ciandt.api.pagamento.controller;

import com.ciandt.api.pagamento.dto.PagamentoDto;
import com.ciandt.api.pagamento.exception.PagamentoNotFoundException;
import com.ciandt.api.pagamento.model.Pagamento;
import com.ciandt.api.pagamento.service.PagamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("api/pagamento")
@RestController
public class PagamentoController {

    private final PagamentoService service;

    private final ObjectMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> getPagamento(@PathVariable Long id) throws PagamentoNotFoundException {
        final var pagamento = service.getPagamento(id);

        return ResponseEntity.ok(pagamento);
    }

    @GetMapping
    public ResponseEntity<List<PagamentoDto>> getAllPagamentos(){
        final var pagamentoList = service.getAllPagamentos();

        return ResponseEntity.ok(pagamentoList.stream()
                .map(pagamento -> mapper.convertValue(pagamento, PagamentoDto.class))
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Void> savePagamento(@RequestBody PagamentoDto pagamentoDto){
        var pagamento = mapper.convertValue(pagamentoDto, Pagamento.class);

        var pagamentoSave = service.savePagamento(pagamento);

        final var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pagamentoSave.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePagamento(@PathVariable Long id, @RequestBody PagamentoDto pagamentoDto) throws PagamentoNotFoundException {
        var pagamentoMapper = mapper.convertValue(pagamentoDto, Pagamento.class);
        service.updatePagamento(id, pagamentoMapper);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) throws PagamentoNotFoundException {
        service.deletePagamento(id);

        return ResponseEntity.noContent().build();
    }
}
