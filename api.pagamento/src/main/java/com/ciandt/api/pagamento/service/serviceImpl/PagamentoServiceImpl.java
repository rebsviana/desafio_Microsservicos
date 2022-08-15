package com.ciandt.api.pagamento.service.serviceImpl;

import com.ciandt.api.pagamento.exception.PagamentoNotFoundException;
import com.ciandt.api.pagamento.model.Pagamento;
import com.ciandt.api.pagamento.enums.Status;
import com.ciandt.api.pagamento.repository.PagamentoRepository;
import com.ciandt.api.pagamento.service.PagamentoService;
import com.ciandt.api.pagamento.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@RequiredArgsConstructor
@Service
public class PagamentoServiceImpl implements PagamentoService {

    private final PedidoService pedidoService;

    private final PagamentoRepository repository;

    private final ObjectMapper mapper;

    private final String MSG_ID_INVALID = "Id precisa ser maior que zero";

    private final String PAGAMENTO_NOT_FOUND = "Pagamento nao encontrado";
    
    @Override
    public Pagamento getPagamento(Long id) throws PagamentoNotFoundException {

        checkArgument(id > 0, MSG_ID_INVALID);

        return repository.findById(id).orElseThrow(() -> new PagamentoNotFoundException(PAGAMENTO_NOT_FOUND));
    }

    @Override
    public Pagamento savePagamento(Pagamento pagamento) {
        checkNotNull(pagamento, "Pagamento nao pode ser null");

        final var pagamentoSave = repository.save(pagamento);

        updateStatusPedido(pagamento);

        return pagamentoSave;
    }

    private void updateStatusPedido(Pagamento pagamento) {
        final var status = pagamento.getStatus();

        checkNotNull(status, "Status nao pode ser null");

        if (Status.CONFIRMADO.equals(status))
            pedidoService.modificarStatusPedido(pagamento.getPedidoId(), pagamento.getStatus());
    }

    @Override
    public void updatePagamento(Long id, Pagamento pagamento) throws PagamentoNotFoundException {
        checkArgument(id > 0, MSG_ID_INVALID);
        checkNotNull(pagamento, "Pagamento nao pode ser null");

        repository.findById(id).orElseThrow(() -> new PagamentoNotFoundException(PAGAMENTO_NOT_FOUND));

        repository.save(pagamento);

        updateStatusPedido(pagamento);
    }

    @Override
    public void deletePagamento(Long id) throws PagamentoNotFoundException {
        checkArgument(id > 0, MSG_ID_INVALID);

        final var pagamento = repository.findById(id).orElseThrow(() -> new PagamentoNotFoundException(PAGAMENTO_NOT_FOUND));

        repository.delete(pagamento);
    }

    @Override
    public List<Pagamento> getAllPagamentos() {
        return repository.findAll();
    }
}
