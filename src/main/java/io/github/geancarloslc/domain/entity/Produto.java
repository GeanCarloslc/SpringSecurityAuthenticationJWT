package io.github.geancarloslc.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "descricao")
    @NotEmpty(message = "Campo DESCRIÇÃO é obrigatório.")
    private String descricao;

    @Column(name = "preco_unitario", length = 20, precision = 2)
    @NotNull(message = "Campo PREÇO UNITÁRIO é obrigatório.")
    private BigDecimal preco;

}
