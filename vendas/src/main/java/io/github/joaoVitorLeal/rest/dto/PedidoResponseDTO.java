package io.github.joaoVitorLeal.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // Gera todas as propriedades como classes Builder, para que possamos construí-las em objetos (evitando primitivos).
public class PedidoResponseDTO {
    private Integer id;
    private String cpf;
    private String nomeCliente;
    private BigDecimal total;
    private String dataPedido;
    private String status;
    private List<ItemPedidoResponseDTO> itens;
}
