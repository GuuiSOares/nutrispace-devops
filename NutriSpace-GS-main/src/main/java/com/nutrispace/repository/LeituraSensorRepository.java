package com.nutrispace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutrispace.model.LeituraSensor;

public interface LeituraSensorRepository extends JpaRepository<LeituraSensor, Long> {

	List<LeituraSensor> findByEstufaIdEstufaOrderByDtHrLeituraDesc(Long idEstufa);

	void deleteByEstufaIdEstufa(Long idEstufa);
}
