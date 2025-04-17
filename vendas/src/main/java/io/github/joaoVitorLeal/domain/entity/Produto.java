package io.github.joaoVitorLeal.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @NotBlank(message = "{campo.obrigatorio.descricao}")
    @Column
    private String descricao;

    @NotNull(message = "{campo.obrigatorio.preco}")
    @Column(name = "preco_unitario", precision = 20, scale = 2)
    private BigDecimal preco;
}
