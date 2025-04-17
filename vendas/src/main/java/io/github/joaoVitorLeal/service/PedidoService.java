package io.github.joaoVitorLeal.service;

import io.github.joaoVitorLeal.domain.entity.Pedido;
import io.github.joaoVitorLeal.domain.enums.StatusPedido;
import io.github.joaoVitorLeal.rest.dto.PedidoRequestDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoRequestDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizarStatus(Integer id, StatusPedido status);
}
