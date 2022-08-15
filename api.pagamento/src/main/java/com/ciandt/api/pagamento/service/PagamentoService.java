package com.ciandt.api.pagamento.service;

import com.ciandt.api.pagamento.dto.PagamentoDto;
import com.ciandt.api.pagamento.exception.PagamentoNotFoundException;
import com.ciandt.api.pagamento.model.Pagamento;

import java.util.List;

public interface PagamentoService {
    Pagamento getPagamento(Long id) throws PagamentoNotFoundException;

    Pagamento savePagamento(Pagamento pagamento);

    void updatePagamento(Long id, Pagamento pagamento) throws PagamentoNotFoundException;

    void deletePagamento(Long id) throws PagamentoNotFoundException;

    List<Pagamento> getAllPagamentos();
}
