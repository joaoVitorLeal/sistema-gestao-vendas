package io.github.joaoVitorLeal.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {
    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoRequestDTO> itens;
}
