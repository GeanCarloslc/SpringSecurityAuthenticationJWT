package io.github.geancarloslc.api.controller;

import io.github.geancarloslc.api.dto.AtualizacaoStatusPedidoDTO;
import io.github.geancarloslc.api.dto.InformacoesItemPedidoDTO;
import io.github.geancarloslc.api.dto.InformacoesPedidoDTO;
import io.github.geancarloslc.api.dto.PedidoDTO;
import io.github.geancarloslc.domain.entity.ItemPedido;
import io.github.geancarloslc.domain.entity.Pedido;
import io.github.geancarloslc.domain.enums.StatusPedido;
import io.github.geancarloslc.service.PedidoService;
import static org.springframework.http.HttpStatus.*;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos/")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("salvarPedido")
    @ResponseStatus(CREATED)
    public Integer salvarPedido(@Valid @RequestBody PedidoDTO pedidoDTO){

        Pedido pedido = pedidoService.salvar(pedidoDTO);
        return pedido.getId();

    }

    @PatchMapping("atualizaStatus/{id}")
    @ResponseStatus(NO_CONTENT)
    public void atualizaStatus(
            @Valid
            @PathVariable Integer id,
            @RequestBody AtualizacaoStatusPedidoDTO dto
    ){
        String novoStatus = dto.getNovoStatus();
        pedidoService.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    @GetMapping("getPedidoById/{id}")
    public InformacoesPedidoDTO getById(@PathVariable("id") Integer id){

        return pedidoService.obterPedidoCompleto(id).map(pedido ->
                        converterPedidoDTO(pedido)
                ).orElseThrow( () ->
                        new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
    }

    private InformacoesPedidoDTO converterPedidoDTO(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatusPedido().name())
                .itemPedido(converterItemPedidoDTO(pedido.getItensPedidos()))
                .build();
    }

    private List<InformacoesItemPedidoDTO> converterItemPedidoDTO(List<ItemPedido> itemPedidos){
        if(CollectionUtils.isEmpty(itemPedidos)){
            return Collections.emptyList();
        }

        return itemPedidos.stream().map(itemPedidoMap ->
                InformacoesItemPedidoDTO
                        .builder()
                        .descricaoProduto(itemPedidoMap.getProduto().getDescricao())
                        .precoUnitario(itemPedidoMap.getProduto().getPreco())
                        .quantidadde(itemPedidoMap.getQuantidade())
                        .build()
        ).collect(Collectors.toList());

    }



}
