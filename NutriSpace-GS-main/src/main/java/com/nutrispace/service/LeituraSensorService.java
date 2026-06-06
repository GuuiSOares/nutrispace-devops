package com.nutrispace.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutrispace.dto.LeituraSensorRequestDTO;
import com.nutrispace.dto.LeituraSensorResponseDTO;
import com.nutrispace.exception.BusinessException;
import com.nutrispace.exception.ResourceNotFoundException;
import com.nutrispace.model.AlertaCritico;
import com.nutrispace.model.Estufa;
import com.nutrispace.model.LeituraSensor;
import com.nutrispace.repository.LeituraSensorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeituraSensorService {

	private final LeituraSensorRepository leituraSensorRepository;
	private final EstufaService estufaService;
	private final AlertaCriticoService alertaCriticoService;

	@Transactional
	public LeituraSensorResponseDTO registrarTelemetria(LeituraSensorRequestDTO dto) {
		validarUmidade(dto.getUmidadeLida());

		Estufa estufa = estufaService.buscarEntidadeComPlanta(dto.getIdEstufa());

		LeituraSensor leitura = new LeituraSensor();
		leitura.setEstufa(estufa);
		leitura.setTemperaturaLida(dto.getTemperaturaLida());
		leitura.setUmidadeLida(dto.getUmidadeLida());
		leitura.setDtHrLeitura(LocalDateTime.now());

		LeituraSensor salva = leituraSensorRepository.save(leitura);

		Optional<AlertaCritico> alertaGerado = alertaCriticoService.registrarAlertaTemperatura(
				estufa, dto.getTemperaturaLida());

		return toResponse(salva, alertaGerado);
	}

	@Transactional(readOnly = true)
	public List<LeituraSensorResponseDTO> listarPorEstufa(Long idEstufa) {
		estufaService.buscarEntidade(idEstufa);
		return leituraSensorRepository.findByEstufaIdEstufaOrderByDtHrLeituraDesc(idEstufa).stream()
				.map(l -> toResponse(l, Optional.empty()))
				.toList();
	}

	@Transactional(readOnly = true)
	public LeituraSensorResponseDTO buscarPorId(Long id) {
		LeituraSensor leitura = leituraSensorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Leitura de sensor não encontrada com id: " + id));
		return toResponse(leitura, Optional.empty());
	}

	private void validarUmidade(Double umidade) {
		if (umidade == null || umidade < 0.0 || umidade > 100.0) {
			throw new BusinessException("A umidade lida deve estar entre 0 e 100");
		}
	}

	private LeituraSensorResponseDTO toResponse(LeituraSensor leitura, Optional<AlertaCritico> alerta) {
		return new LeituraSensorResponseDTO(
				leitura.getIdLeitor(),
				leitura.getEstufa().getIdEstufa(),
				leitura.getEstufa().getNomeEstufa(),
				leitura.getTemperaturaLida(),
				leitura.getUmidadeLida(),
				leitura.getDtHrLeitura(),
				alerta.isPresent(),
				alerta.map(AlertaCritico::getIdAlerta).orElse(null));
	}
}
