package com.nutrispace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutrispace.model.HistoricoRega;

public interface HistoricoRegaRepository extends JpaRepository<HistoricoRega, Long> {

	List<HistoricoRega> findByEstufaIdEstufaOrderByDtHrRegaDesc(Long idEstufa);

	void deleteByEstufaIdEstufa(Long idEstufa);
}
