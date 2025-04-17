package io.github.joaoVitorLeal.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "{campo.obrigatorio.nome}")
    @Column(length = 100)
    private String nome;

    @CPF(message = "{campo.invalido.cpf}")
    @NotBlank(message = "{campo.obrigatorio.cpf}")
    @Column(length = 11, unique = true)
    private String cpf;

    @JsonIgnore // Informa para o parse (transformador de objetos JSON) que deve ignorar essa propriedade
    @OneToMany(mappedBy = "cliente") // mapeando relação já que na tabela cliente não referencía a tabela pedido
    private Set<Pedido> pedidos;
}
