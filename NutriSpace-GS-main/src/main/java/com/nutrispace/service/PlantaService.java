package com.nutrispace.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutrispace.dto.PlantaRequestDTO;
import com.nutrispace.dto.PlantaResponseDTO;
import com.nutrispace.exception.BusinessException;
import com.nutrispace.exception.ResourceNotFoundException;
import com.nutrispace.model.Planta;
import com.nutrispace.dto.PlantaResponseRecord;
import com.nutrispace.repository.EstufaRepository;
import com.nutrispace.repository.PlantaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlantaService {

	private final PlantaRepository plantaRepository;
	private final EstufaRepository estufaRepository;

	@Transactional(readOnly = true)
	public List<PlantaResponseRecord> listarTodasRecords() {
		return plantaRepository.findAll().stream().map(this::toRecord).toList();
	}

	@Transactional(readOnly = true)
	public List<PlantaResponseDTO> listarTodas() {
		return plantaRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public PlantaResponseRecord buscarRecordPorId(Long id) {
		return toRecord(buscarEntidade(id));
	}

	@Transactional(readOnly = true)
	public PlantaResponseDTO buscarPorId(Long id) {
		return toResponse(buscarEntidade(id));
	}

	@Transactional
	public PlantaResponseRecord criarRecord(PlantaRequestDTO dto) {
		return toRecord(criar(dto));
	}

	@Transactional
	public PlantaResponseDTO criar(PlantaRequestDTO dto) {
		validarFaixaTemperatura(dto.getTempMinIdeal(), dto.getTempMaxIdeal());
		Planta planta = new Planta();
		aplicarDados(planta, dto);
		return toResponse(plantaRepository.save(planta));
	}

	@Transactional
	public PlantaResponseRecord atualizarRecord(Long id, PlantaRequestDTO dto) {
		return toRecord(atualizar(id, dto));
	}

	@Transactional
	public PlantaResponseDTO atualizar(Long id, PlantaRequestDTO dto) {
		validarFaixaTemperatura(dto.getTempMinIdeal(), dto.getTempMaxIdeal());
		Planta planta = buscarEntidade(id);
		aplicarDados(planta, dto);
		return toResponse(plantaRepository.save(planta));
	}

	@Transactional
	public void excluir(Long id) {
		if (!plantaRepository.existsById(id)) {
			throw new ResourceNotFoundException("Planta não encontrada com id: " + id);
		}
		if (estufaRepository.existsByPlantaIdPlanta(id)) {
			throw new BusinessException("Não é possível excluir planta vinculada a estufa");
		}
		plantaRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Planta buscarEntidade(Long id) {
		return plantaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Planta não encontrada com id: " + id));
	}

	private void aplicarDados(Planta planta, PlantaRequestDTO dto) {
		planta.setNomePlanta(dto.getNomePlanta());
		planta.setTempMinIdeal(dto.getTempMinIdeal());
		planta.setTempMaxIdeal(dto.getTempMaxIdeal());
		planta.setUmiMinIdeal(dto.getUmiMinIdeal());
	}

	private void validarFaixaTemperatura(Double tempMin, Double tempMax) {
		if (tempMin != null && tempMax != null && tempMin >= tempMax) {
			throw new BusinessException("A temperatura máxima ideal deve ser maior que a mínima");
		}
	}

	private PlantaResponseRecord toRecord(Planta planta) {
		return new PlantaResponseRecord(
				planta.getIdPlanta(),
				planta.getNomePlanta(),
				planta.getTempMinIdeal(),
				planta.getTempMaxIdeal(),
				planta.getUmiMinIdeal());
	}

	private PlantaResponseRecord toRecord(PlantaResponseDTO dto) {
		return new PlantaResponseRecord(
				dto.getIdPlanta(),
				dto.getNomePlanta(),
				dto.getTempMinIdeal(),
				dto.getTempMaxIdeal(),
				dto.getUmiMinIdeal());
	}

	private PlantaResponseDTO toResponse(Planta planta) {
		return new PlantaResponseDTO(
				planta.getIdPlanta(),
				planta.getNomePlanta(),
				planta.getTempMinIdeal(),
				planta.getTempMaxIdeal(),
				planta.getUmiMinIdeal());
	}
}
