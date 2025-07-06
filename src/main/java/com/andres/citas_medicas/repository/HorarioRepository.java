package com.andres.citas_medicas.repository;

import com.andres.citas_medicas.entity.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    List<Horario> findByOdontologoId(Long id);
}
