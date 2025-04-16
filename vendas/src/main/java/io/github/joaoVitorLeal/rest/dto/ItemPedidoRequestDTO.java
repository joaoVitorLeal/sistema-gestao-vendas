package io.github.joaoVitorLeal.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoRequestDTO {
    private Integer produto;
    private Integer quantidade;
}
