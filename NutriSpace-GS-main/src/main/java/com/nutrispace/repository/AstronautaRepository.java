package com.nutrispace.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutrispace.model.Astronauta;

public interface AstronautaRepository extends JpaRepository<Astronauta, Long> {

	Optional<Astronauta> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByEmailAndIdAstronautaNot(String email, Long idAstronauta);
}
