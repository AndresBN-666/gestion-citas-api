package com.andres.citas_medicas.repository;

import com.andres.citas_medicas.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
