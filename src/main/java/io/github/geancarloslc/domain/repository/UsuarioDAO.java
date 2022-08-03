package io.github.geancarloslc.domain.repository;

import io.github.geancarloslc.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);

}
