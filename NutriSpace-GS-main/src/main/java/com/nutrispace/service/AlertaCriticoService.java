package com.nutrispace.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutrispace.dto.AlertaCriticoResponseDTO;
import com.nutrispace.exception.BusinessException;
import com.nutrispace.exception.ResourceNotFoundException;
import com.nutrispace.model.AlertaCritico;
import com.nutrispace.model.Estufa;
import com.nutrispace.model.Planta;
import com.nutrispace.model.AlertaCritico.StatusAlerta;
import com.nutrispace.repository.AlertaCriticoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlertaCriticoService {

	private final AlertaCriticoRepository alertaCriticoRepository;
	private final EstufaService estufaService;

	@Transactional(readOnly = true)
	public List<AlertaCriticoResponseDTO> listarAtivos() {
		return alertaCriticoRepository.findByStatusWithEstufa(StatusAlerta.ATIVO).stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public List<AlertaCriticoResponseDTO> listarPorEstufa(Long idEstufa) {
		estufaService.buscarEntidade(idEstufa);
		return alertaCriticoRepository.findByEstufaIdWithPlanta(idEstufa).stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public AlertaCriticoResponseDTO buscarPorId(Long id) {
		return toResponse(buscarEntidade(id));
	}

	@Transactional
	public AlertaCriticoResponseDTO resolver(Long id) {
		AlertaCritico alerta = buscarEntidade(id);
		if (alerta.getStatusAlerta() == StatusAlerta.RESOLVIDO) {
			throw new BusinessException("O alerta já está resolvido");
		}
		alerta.setStatusAlerta(StatusAlerta.RESOLVIDO);
		return toResponse(alertaCriticoRepository.save(alerta));
	}

	/**
	 * Cria alerta automático quando a temperatura está fora da faixa ideal da planta da estufa.
	 */
	@Transactional
	public Optional<AlertaCritico> registrarAlertaTemperatura(Estufa estufa, Double temperaturaLida) {
		Planta planta = estufa.getPlanta();
		if (planta == null || temperaturaLida == null) {
			return Optional.empty();
		}

		boolean acimaDoMaximo = temperaturaLida > planta.getTempMaxIdeal();
		boolean abaixoDoMinimo = temperaturaLida < planta.getTempMinIdeal();

		if (!acimaDoMaximo && !abaixoDoMinimo) {
			return Optional.empty();
		}

		String tipoViolacao = acimaDoMaximo ? "acima do máximo" : "abaixo do mínimo";
		String descricao = String.format(
				"Temperatura %.1f°C %s ideal na estufa '%s' (planta '%s'). Faixa permitida: %.1f°C a %.1f°C.",
				temperaturaLida,
				tipoViolacao,
				estufa.getNomeEstufa(),
				planta.getNomePlanta(),
				planta.getTempMinIdeal(),
				planta.getTempMaxIdeal());

		AlertaCritico alerta = new AlertaCritico();
		alerta.setEstufa(estufa);
		alerta.setDescricaoIncidente(descricao);
		alerta.setStatusAlerta(StatusAlerta.ATIVO);
		alerta.setDtHrAlerta(LocalDateTime.now());

		return Optional.of(alertaCriticoRepository.save(alerta));
	}

	@Transactional(readOnly = true)
	public AlertaCritico buscarEntidade(Long id) {
		return alertaCriticoRepository.findByIdWithEstufa(id)
				.orElseThrow(() -> new ResourceNotFoundException("Alerta crítico não encontrado com id: " + id));
	}

	private AlertaCriticoResponseDTO toResponse(AlertaCritico alerta) {
		Estufa estufa = alerta.getEstufa();
		return new AlertaCriticoResponseDTO(
				alerta.getIdAlerta(),
				estufa.getIdEstufa(),
				estufa.getNomeEstufa(),
				estufa.getPlanta() != null ? estufa.getPlanta().getNomePlanta() : null,
				alerta.getDescricaoIncidente(),
				alerta.getStatusAlerta(),
				alerta.getDtHrAlerta());
	}
}
