package com.nutrispace.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutrispace.dto.HistoricoRegaRequestDTO;
import com.nutrispace.dto.HistoricoRegaResponseDTO;
import com.nutrispace.exception.ResourceNotFoundException;
import com.nutrispace.model.Estufa;
import com.nutrispace.model.HistoricoRega;
import com.nutrispace.repository.HistoricoRegaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoricoRegaService {

	private final HistoricoRegaRepository historicoRegaRepository;
	private final EstufaService estufaService;

	@Transactional
	public HistoricoRegaResponseDTO registrar(HistoricoRegaRequestDTO dto) {
		Estufa estufa = estufaService.buscarEntidade(dto.getIdEstufa());

		HistoricoRega rega = new HistoricoRega();
		rega.setEstufa(estufa);
		rega.setTipoRega(dto.getTipoRega());
		rega.setDtHrRega(dto.getDtHrRega() != null ? dto.getDtHrRega() : LocalDateTime.now());

		return toResponse(historicoRegaRepository.save(rega));
	}

	@Transactional(readOnly = true)
	public List<HistoricoRegaResponseDTO> listarPorEstufa(Long idEstufa) {
		estufaService.buscarEntidade(idEstufa);
		return historicoRegaRepository.findByEstufaIdEstufaOrderByDtHrRegaDesc(idEstufa).stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public HistoricoRegaResponseDTO buscarPorId(Long id) {
		return toResponse(buscarEntidade(id));
	}

	@Transactional(readOnly = true)
	public HistoricoRega buscarEntidade(Long id) {
		return historicoRegaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Registro de rega não encontrado com id: " + id));
	}

	private HistoricoRegaResponseDTO toResponse(HistoricoRega rega) {
		return new HistoricoRegaResponseDTO(
				rega.getIdRega(),
				rega.getEstufa().getIdEstufa(),
				rega.getEstufa().getNomeEstufa(),
				rega.getTipoRega(),
				rega.getDtHrRega());
	}
}
