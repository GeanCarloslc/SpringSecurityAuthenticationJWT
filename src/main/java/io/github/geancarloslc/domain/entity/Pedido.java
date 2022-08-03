package io.github.geancarloslc.domain.entity;

import io.github.geancarloslc.domain.enums.StatusPedido;
import io.github.geancarloslc.validation.NotEmptyList;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    @Column(name = "total", length = 20, precision = 2)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido")
    @NotEmptyList(message = "Pedido não pode ser realizado sem itens.")
    private List<ItemPedido> itensPedidos;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedido statusPedido;

    //Evitando erro de lista nullas
    public List<ItemPedido> getItensPedidos() {
        if(this.itensPedidos == null){
            this.itensPedidos = new ArrayList<>();
        }
        return itensPedidos;
    }
}
