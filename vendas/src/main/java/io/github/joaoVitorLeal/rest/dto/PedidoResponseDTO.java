package io.github.joaoVitorLeal.rest.dto;

import java.math.BigDecimal;
import java.util.List;

public class PedidoResponseDTO {
    Integer cliente;
    BigDecimal total;
    List<ItemPedidoRequestDTO> itens;
}
