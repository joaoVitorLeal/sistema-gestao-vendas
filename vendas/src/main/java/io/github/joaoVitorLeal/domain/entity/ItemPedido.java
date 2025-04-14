package io.github.joaoVitorLeal.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "item_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @ManyToOne // Muitos itens-pedidos para um pedido
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne() // Muitos itens de pedido para um produto
    @JoinColumn(name = "produto_id", table = "produto")
    private Produto produto;

    @Column
    private Integer quantidade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
