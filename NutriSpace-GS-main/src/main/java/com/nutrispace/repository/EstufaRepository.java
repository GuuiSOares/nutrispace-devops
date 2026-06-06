package com.nutrispace.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nutrispace.model.Estufa;

public interface EstufaRepository extends JpaRepository<Estufa, Long> {

	@Query("SELECT e FROM Estufa e "
			+ "JOIN FETCH e.planta "
			+ "JOIN FETCH e.astronauta "
			+ "LEFT JOIN FETCH e.reservatorio "
			+ "WHERE e.idEstufa = :id")
	Optional<Estufa> findByIdWithRelacionamentos(@Param("id") Long id);

	@Query("SELECT e FROM Estufa e "
			+ "JOIN FETCH e.planta "
			+ "JOIN FETCH e.astronauta "
			+ "LEFT JOIN FETCH e.reservatorio")
	List<Estufa> findAllWithRelacionamentos();

	@Query("SELECT e FROM Estufa e JOIN FETCH e.planta WHERE e.idEstufa = :id")
	Optional<Estufa> findByIdWithPlanta(@Param("id") Long id);

	boolean existsByPlantaIdPlanta(Long idPlanta);

	boolean existsByAstronautaIdAstronauta(Long idAstronauta);
}
