package io.github.geancarloslc.service.impl;

import io.github.geancarloslc.api.dto.ItemPedidoDTO;
import io.github.geancarloslc.api.dto.PedidoDTO;
import io.github.geancarloslc.domain.entity.Cliente;
import io.github.geancarloslc.domain.entity.ItemPedido;
import io.github.geancarloslc.domain.entity.Pedido;
import io.github.geancarloslc.domain.entity.Produto;
import io.github.geancarloslc.domain.enums.StatusPedido;
import io.github.geancarloslc.domain.repository.ClienteDAO;
import io.github.geancarloslc.domain.repository.ItemPedidoDAO;
import io.github.geancarloslc.domain.repository.PedidoDAO;
import io.github.geancarloslc.domain.repository.ProdutoDAO;
import io.github.geancarloslc.exception.PedidoNaoEncontradoExecption;
import io.github.geancarloslc.exception.RegraNegocioException;
import io.github.geancarloslc.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //Declara os construtores com as variaveis com final
public class PedidoServiceImpl implements PedidoService {

    private final PedidoDAO pedidoDAO;
    private final ClienteDAO clienteDAO;
    private final ProdutoDAO produtoDAO;
    private final ItemPedidoDAO itemPedidoDAO;

    @Override
    @Transactional
    //Garante a integridade dando commit apenas se todas as requisições no banco forem sucesso
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente = clienteDAO
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatusPedido(StatusPedido.REALIZADO);

        List<ItemPedido> listaItemPedidos = converterItens(pedido, pedidoDTO.getItensPedidos());
        pedidoDAO.save(pedido);
        itemPedidoDAO.saveAll(listaItemPedidos);
        pedido.setItensPedidos(listaItemPedidos);
        return pedido;
    }



    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itensPedidos){
        if(itensPedidos.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");
        }

        return itensPedidos
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtoDAO.findById(idProduto).
                            orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoDAO.findByIdFetchItensPedidos(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidoDAO.findById(id)
                .map( pedido -> {
                            pedido.setStatusPedido(statusPedido);
                            pedidoDAO.save(pedido);
                            return Void.TYPE;
                        }).orElseThrow( () -> new PedidoNaoEncontradoExecption());
    }
}
