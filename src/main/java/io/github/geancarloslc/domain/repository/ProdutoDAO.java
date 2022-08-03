package io.github.geancarloslc.domain.repository;

import io.github.geancarloslc.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoDAO extends JpaRepository<Produto, Integer> {

}
