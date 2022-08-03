package io.github.geancarloslc.service;

import io.github.geancarloslc.api.dto.PedidoDTO;
import io.github.geancarloslc.domain.entity.Pedido;
import io.github.geancarloslc.domain.enums.StatusPedido;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO pedidoDTO);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
