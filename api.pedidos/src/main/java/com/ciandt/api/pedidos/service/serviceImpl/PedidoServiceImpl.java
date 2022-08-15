package com.ciandt.api.pedidos.service.serviceImpl;

import com.ciandt.api.pedidos.exception.PedidoJaCadastrado;
import com.ciandt.api.pedidos.exception.PedidoNotFoundException;
import com.ciandt.api.pedidos.model.Pedido;
import com.ciandt.api.pedidos.enums.Status;
import com.ciandt.api.pedidos.repository.PedidoRepository;
import com.ciandt.api.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repository;

    private final String ID_INVALID = "Id precisa ser maior que zero";
    private final String PEDIDO_NULL = "Pedido nao pode ser vazio";

    @Override
    public Pedido getPedido(Long id) throws PedidoNotFoundException {
        checkArgument(id > 0, ID_INVALID);

        return repository.findById(id).orElseThrow(PedidoNotFoundException::new);
    }

    @Override
    public List<Pedido> getAllPedido() {
        return repository.findAll();
    }

    @Override
    public Pedido createPedido(Pedido pedido) throws PedidoJaCadastrado {
        checkNotNull(pedido, PEDIDO_NULL);

        var pedidoExiste = repository.findById(pedido.getId());
        if (pedidoExiste.isPresent())
            throw new PedidoJaCadastrado();

        return repository.save(pedido);
    }

    @Override
    public void updatePedido(Long id, Pedido pedido) throws PedidoNotFoundException {
        checkArgument(id > 0, ID_INVALID);
        checkNotNull(pedido, PEDIDO_NULL);

        repository.findById(id).orElseThrow(PedidoNotFoundException::new);

        repository.save(pedido);
    }

    @Override
    public void updateStatusPedido(Long id, Status status) throws PedidoNotFoundException {
        checkArgument(id > 0, ID_INVALID);
        checkNotNull(status, "Status nao pode ser vazio");

        var pedidoExist = repository.findById(id).orElseThrow(PedidoNotFoundException::new);

        pedidoExist.setStatus(status);

        repository.save(pedidoExist);
    }
}
