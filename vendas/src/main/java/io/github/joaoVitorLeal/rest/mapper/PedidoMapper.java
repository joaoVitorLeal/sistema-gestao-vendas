package io.github.joaoVitorLeal.rest.mapper;

import io.github.joaoVitorLeal.domain.entity.Pedido;
import io.github.joaoVitorLeal.rest.dto.PedidoResponseDTO;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class PedidoMapper {

    public PedidoResponseDTO converterParaDTO(Pedido pedido) {
        return PedidoResponseDTO.builder()
                .id(pedido.getId())
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .status(pedido.getStatus().name()) // name() - pega o valor do enum e transforma em uma String
                .itens(ItemPedidoMapper.converterParaListaDTO(pedido.getItens()))
                .build();
    }
}
