package io.github.geancarloslc.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @NotEmpty(message = "Campo LOGIN é obrigatório")
    private String login;

    @Column
    @NotEmpty(message = "Campo SENHA é obrigatório")
    private String senha;

    @Column
    private boolean adm;

}
