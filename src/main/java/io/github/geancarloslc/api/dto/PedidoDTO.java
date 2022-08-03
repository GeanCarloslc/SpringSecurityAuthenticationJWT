package io.github.geancarloslc.api.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// Data Transfer Object (DTO) ou simplesmente Transfer Object é um padrão
// de projetos bastante usado em Java para o transporte de dados entre
// diferentes componentes de um sistema, diferentes instâncias ou processos
// de um sistema distribuído ou diferentes sistemas via serialização.

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    @NotNull(message = "Campo CLIENTE é obrigatório.")
    private Integer cliente;

    @NotNull(message = "Campo TOTAL é obrigatório.")
    private BigDecimal total;

    private List<ItemPedidoDTO> itensPedidos;

    public List<ItemPedidoDTO> getItensPedidos() {
        if(this.itensPedidos == null){
            this.itensPedidos = new ArrayList<>();
        }
        return itensPedidos;
    }
}
