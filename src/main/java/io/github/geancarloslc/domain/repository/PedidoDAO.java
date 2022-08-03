package io.github.geancarloslc.domain.repository;

import io.github.geancarloslc.domain.entity.Cliente;
import io.github.geancarloslc.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoDAO extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

    @Query("select p from Pedido p left join fetch p.itensPedidos where p.id = :id")
    Optional<Pedido> findByIdFetchItensPedidos(@Param("id") Integer id);
}
