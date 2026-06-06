package com.nutrispace.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutrispace.dto.ColheitaRequestDTO;
import com.nutrispace.dto.ColheitaResponseDTO;
import com.nutrispace.exception.ResourceNotFoundException;
import com.nutrispace.model.Colheita;
import com.nutrispace.model.Estufa;
import com.nutrispace.repository.ColheitaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColheitaService {

	private final ColheitaRepository colheitaRepository;
	private final EstufaService estufaService;

	@Transactional
	public ColheitaResponseDTO registrar(ColheitaRequestDTO dto) {
		Estufa estufa = estufaService.buscarEntidade(dto.getIdEstufa());

		Colheita colheita = new Colheita();
		colheita.setEstufa(estufa);
		colheita.setQuantidadeKg(dto.getQuantidadeKg());
		colheita.setQualidade(dto.getQualidade());
		colheita.setDtColheita(dto.getDtColheita() != null ? dto.getDtColheita() : LocalDateTime.now());

		return toResponse(colheitaRepository.save(colheita));
	}

	@Transactional(readOnly = true)
	public List<ColheitaResponseDTO> listarPorEstufa(Long idEstufa) {
		estufaService.buscarEntidade(idEstufa);
		return colheitaRepository.findByEstufaIdEstufaOrderByDtColheitaDesc(idEstufa).stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public ColheitaResponseDTO buscarPorId(Long id) {
		return toResponse(buscarEntidade(id));
	}

	@Transactional(readOnly = true)
	public Colheita buscarEntidade(Long id) {
		return colheitaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Colheita não encontrada com id: " + id));
	}

	private ColheitaResponseDTO toResponse(Colheita colheita) {
		return new ColheitaResponseDTO(
				colheita.getIdColheita(),
				colheita.getEstufa().getIdEstufa(),
				colheita.getEstufa().getNomeEstufa(),
				colheita.getQuantidadeKg(),
				colheita.getDtColheita(),
				colheita.getQualidade());
	}
}
