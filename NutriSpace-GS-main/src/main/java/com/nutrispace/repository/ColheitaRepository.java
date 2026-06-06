package com.nutrispace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutrispace.model.Colheita;

public interface ColheitaRepository extends JpaRepository<Colheita, Long> {

	List<Colheita> findByEstufaIdEstufaOrderByDtColheitaDesc(Long idEstufa);

	void deleteByEstufaIdEstufa(Long idEstufa);
}
