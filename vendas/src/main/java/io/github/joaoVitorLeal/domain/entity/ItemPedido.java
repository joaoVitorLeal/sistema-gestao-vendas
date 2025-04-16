package io.github.joaoVitorLeal.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "item_pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @ManyToOne // Muitos itens-pedidos para um pedido
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne() // Muitos itens de pedido para um produto
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column
    private Integer quantidade;
}
