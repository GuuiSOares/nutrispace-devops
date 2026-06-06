package com.nutrispace.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutrispace.model.Reservatorio;

public interface ReservatorioRepository extends JpaRepository<Reservatorio, Long> {

	Optional<Reservatorio> findByEstufaIdEstufa(Long idEstufa);

	boolean existsByEstufaIdEstufa(Long idEstufa);
}
