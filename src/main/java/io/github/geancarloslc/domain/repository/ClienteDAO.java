package io.github.geancarloslc.domain.repository;
import io.github.geancarloslc.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//QueryMethods
public interface ClienteDAO extends JpaRepository<Cliente, Integer> {

}
