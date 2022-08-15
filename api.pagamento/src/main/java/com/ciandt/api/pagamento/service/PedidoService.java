package com.ciandt.api.pagamento.service;

import com.ciandt.api.pagamento.enums.Status;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "pedidos", url = "http://localhost:8082/api/pedidos")
public interface PedidoService {

    @PutMapping(path = "/{id}/{status}")
    void modificarStatusPedido(@PathVariable Long id, @PathVariable Status status);
}
