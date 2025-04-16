package io.github.joaoVitorLeal.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Integer id;

    @Column(length = 100)
    private String nome;

    @Column(length = 11, unique = true)
    private String cpf;

    @JsonIgnore // Informa para o parse (transformador de objetos JSON) que deve ignorar essa propriedade
    @OneToMany(mappedBy = "cliente") // mapeando relação já que na tabela cliente não referencía a tabela pedido
    private Set<Pedido> pedidos;
}
