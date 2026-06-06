package com.nutrispace.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutrispace.dto.EstufaRequestDTO;
import com.nutrispace.dto.EstufaResponseDTO;
import com.nutrispace.exception.ResourceNotFoundException;
import com.nutrispace.model.Astronauta;
import com.nutrispace.model.Estufa;
import com.nutrispace.model.Planta;
import com.nutrispace.model.Reservatorio;
import com.nutrispace.repository.AlertaCriticoRepository;
import com.nutrispace.repository.AstronautaRepository;
import com.nutrispace.repository.ColheitaRepository;
import com.nutrispace.repository.EstufaRepository;
import com.nutrispace.repository.HistoricoRegaRepository;
import com.nutrispace.repository.LeituraSensorRepository;
import com.nutrispace.repository.ReservatorioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstufaService {

	private final EstufaRepository estufaRepository;
	private final PlantaService plantaService;
	private final AstronautaRepository astronautaRepository;
	private final ReservatorioRepository reservatorioRepository;
	private final LeituraSensorRepository leituraSensorRepository;
	private final AlertaCriticoRepository alertaCriticoRepository;
	private final ColheitaRepository colheitaRepository;
	private final HistoricoRegaRepository historicoRegaRepository;

	@Transactional(readOnly = true)
	public List<EstufaResponseDTO> listarTodas() {
		return estufaRepository.findAllWithRelacionamentos().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public EstufaResponseDTO buscarPorId(Long id) {
		return toResponse(buscarEntidadeComRelacionamentos(id));
	}

	@Transactional
	public EstufaResponseDTO criar(EstufaRequestDTO dto) {
		Estufa estufa = new Estufa();
		aplicarDados(estufa, dto);
		return toResponse(estufaRepository.save(estufa));
	}

	@Transactional
	public EstufaResponseDTO atualizar(Long id, EstufaRequestDTO dto) {
		Estufa estufa = buscarEntidadeComRelacionamentos(id);
		aplicarDados(estufa, dto);
		return toResponse(estufaRepository.save(estufa));
	}

	@Transactional
	public void excluir(Long id) {
		if (!estufaRepository.existsById(id)) {
			throw new ResourceNotFoundException("Estufa não encontrada com id: " + id);
		}
		reservatorioRepository.findByEstufaIdEstufa(id).ifPresent(reservatorioRepository::delete);
		leituraSensorRepository.deleteByEstufaIdEstufa(id);
		alertaCriticoRepository.deleteByEstufaIdEstufa(id);
		colheitaRepository.deleteByEstufaIdEstufa(id);
		historicoRegaRepository.deleteByEstufaIdEstufa(id);
		estufaRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Estufa buscarEntidade(Long id) {
		return estufaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Estufa não encontrada com id: " + id));
	}

	@Transactional(readOnly = true)
	public Estufa buscarEntidadeComRelacionamentos(Long id) {
		return estufaRepository.findByIdWithRelacionamentos(id)
				.orElseThrow(() -> new ResourceNotFoundException("Estufa não encontrada com id: " + id));
	}

	@Transactional(readOnly = true)
	public Estufa buscarEntidadeComPlanta(Long id) {
		return estufaRepository.findByIdWithPlanta(id)
				.orElseThrow(() -> new ResourceNotFoundException("Estufa não encontrada com id: " + id));
	}

	private void aplicarDados(Estufa estufa, EstufaRequestDTO dto) {
		Planta planta = plantaService.buscarEntidade(dto.getIdPlanta());
		Astronauta astronauta = astronautaRepository.findById(dto.getIdAstronauta())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Astronauta não encontrado com id: " + dto.getIdAstronauta()));

		estufa.setNomeEstufa(dto.getNomeEstufa());
		estufa.setStatusBomba(dto.getStatusBomba());
		estufa.setPlanta(planta);
		estufa.setAstronauta(astronauta);
	}

	EstufaResponseDTO toResponse(Estufa estufa) {
		Reservatorio reservatorio = estufa.getReservatorio();
		return new EstufaResponseDTO(
				estufa.getIdEstufa(),
				estufa.getNomeEstufa(),
				estufa.getStatusBomba(),
				reservatorio != null ? reservatorio.getIdReservatorio() : null,
				reservatorio != null ? reservatorio.getNivelAtualPercentual() : null,
				estufa.getPlanta().getNomePlanta(),
				estufa.getPlanta().getIdPlanta(),
				estufa.getAstronauta().getIdAstronauta(),
				estufa.getAstronauta().getNome());
	}
}
