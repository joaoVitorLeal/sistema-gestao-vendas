package io.github.joaoVitorLeal.rest.mapper;

import io.github.joaoVitorLeal.domain.entity.ItemPedido;
import io.github.joaoVitorLeal.rest.dto.ItemPedidoRequestDTO;
import io.github.joaoVitorLeal.rest.dto.ItemPedidoResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemPedidoMapper {

    public static ItemPedidoResponseDTO converterParaDTO(ItemPedido item) {
        return ItemPedidoResponseDTO.builder()
                .descricaoProduto(item.getProduto().getDescricao())
                .precoUnitario(item.getProduto().getPreco())
                .quantidade(item.getQuantidade())
                .build();
    }

    public static List<ItemPedidoResponseDTO> converterParaListaDTO(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }

        return  itens.stream()
                .map(ItemPedidoMapper::converterParaDTO)
                .collect(Collectors.toList());
    }
}
