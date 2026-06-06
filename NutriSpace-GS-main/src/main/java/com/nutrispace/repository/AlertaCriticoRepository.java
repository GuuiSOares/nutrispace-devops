package com.nutrispace.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nutrispace.model.AlertaCritico;
import com.nutrispace.model.AlertaCritico.StatusAlerta;

public interface AlertaCriticoRepository extends JpaRepository<AlertaCritico, Long> {

	List<AlertaCritico> findByStatusAlertaOrderByDtHrAlertaDesc(StatusAlerta statusAlerta);

	@Query("SELECT a FROM AlertaCritico a JOIN FETCH a.estufa e JOIN FETCH e.planta WHERE a.idAlerta = :id")
	Optional<AlertaCritico> findByIdWithEstufa(@Param("id") Long id);

	@Query("SELECT a FROM AlertaCritico a JOIN FETCH a.estufa e JOIN FETCH e.planta "
			+ "WHERE a.statusAlerta = :status ORDER BY a.dtHrAlerta DESC")
	List<AlertaCritico> findByStatusWithEstufa(@Param("status") StatusAlerta status);

	boolean existsByEstufaIdEstufaAndStatusAlerta(Long idEstufa, StatusAlerta statusAlerta);

	@Query("SELECT a FROM AlertaCritico a JOIN FETCH a.estufa e JOIN FETCH e.planta "
			+ "WHERE e.idEstufa = :idEstufa ORDER BY a.dtHrAlerta DESC")
	List<AlertaCritico> findByEstufaIdWithPlanta(@Param("idEstufa") Long idEstufa);

	void deleteByEstufaIdEstufa(Long idEstufa);
}
