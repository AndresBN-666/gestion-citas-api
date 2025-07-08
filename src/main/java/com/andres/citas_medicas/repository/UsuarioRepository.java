package com.andres.citas_medicas.repository;

import com.andres.citas_medicas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
