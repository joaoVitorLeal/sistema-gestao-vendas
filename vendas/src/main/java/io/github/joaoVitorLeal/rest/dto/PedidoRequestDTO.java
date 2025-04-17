package io.github.joaoVitorLeal.rest.dto;

import io.github.joaoVitorLeal.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {
    @NotNull(message = "{campo.obrigatorio.codigo-cliente}")
    private Integer cliente;

    @NotNull(message = "{campo.obrigatorio.total-pedido}")
    private BigDecimal total;

    @NotEmptyList(message = "{campo.obrigatorio.itens-pedido}")
    private List<ItemPedidoRequestDTO> itens;
}
