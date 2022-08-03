package io.github.geancarloslc.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

//JPA
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 100)
    @NotEmpty(message = "Campo NOME é obrigatório.")
    private String nome;

    @Column(name = "cpf", length = 11)
    @NotEmpty(message = "Campo CPF é obrigatório.")
    @CPF(message = "Informe um CPF válido.")
    private String cpf;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    //FetchType.LAZY não busca os pedidos do cliente ao carregar
    //mappedBy - Essa entidade não existe chave para pedido, quem possui a chave é a tabela de pedido,
    //utilizando o mappedBy é possível realizar esse fetch buscando os pedidos desse cliente
    //Variaveis que poderia ser usadas
    //Colletion
    //List
    //Set - Evita possiveis erros do hibernate, não aceita item repitidos
    private Set<Pedido> pedidos;

    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

}
