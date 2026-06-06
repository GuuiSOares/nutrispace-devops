package com.nutrispace.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutrispace.dto.ReservatorioRequestDTO;
import com.nutrispace.dto.ReservatorioResponseDTO;
import com.nutrispace.exception.BusinessException;
import com.nutrispace.exception.ResourceNotFoundException;
import com.nutrispace.model.Estufa;
import com.nutrispace.model.Reservatorio;
import com.nutrispace.repository.ReservatorioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservatorioService {

	private final ReservatorioRepository reservatorioRepository;
	private final EstufaService estufaService;

	@Transactional(readOnly = true)
	public List<ReservatorioResponseDTO> listarTodos() {
		return reservatorioRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public ReservatorioResponseDTO buscarPorId(Long id) {
		return toResponse(buscarEntidade(id));
	}

	@Transactional
	public ReservatorioResponseDTO criar(ReservatorioRequestDTO dto) {
		validarNivelPercentual(dto.getNivelAtualPercentual());
		validarEstufaSemReservatorio(dto.getIdEstufa(), null);

		Estufa estufa = estufaService.buscarEntidade(dto.getIdEstufa());
		Reservatorio reservatorio = new Reservatorio();
		aplicarDados(reservatorio, dto, estufa);
		return toResponse(reservatorioRepository.save(reservatorio));
	}

	@Transactional
	public ReservatorioResponseDTO atualizar(Long id, ReservatorioRequestDTO dto) {
		validarNivelPercentual(dto.getNivelAtualPercentual());
		Reservatorio reservatorio = buscarEntidade(id);

		if (!reservatorio.getEstufa().getIdEstufa().equals(dto.getIdEstufa())) {
			validarEstufaSemReservatorio(dto.getIdEstufa(), id);
		}

		Estufa estufa = estufaService.buscarEntidade(dto.getIdEstufa());
		aplicarDados(reservatorio, dto, estufa);
		return toResponse(reservatorioRepository.save(reservatorio));
	}

	@Transactional
	public void excluir(Long id) {
		if (!reservatorioRepository.existsById(id)) {
			throw new ResourceNotFoundException("Reservatório não encontrado com id: " + id);
		}
		reservatorioRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Reservatorio buscarEntidade(Long id) {
		return reservatorioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Reservatório não encontrado com id: " + id));
	}

	public void validarNivelPercentual(Double nivel) {
		if (nivel == null || nivel < 0.0 || nivel > 100.0) {
			throw new BusinessException("O nível atual do reservatório deve estar entre 0.0% e 100.0%");
		}
	}

	private void validarEstufaSemReservatorio(Long idEstufa, Long idReservatorioAtual) {
		reservatorioRepository.findByEstufaIdEstufa(idEstufa).ifPresent(existente -> {
			if (idReservatorioAtual == null || !existente.getIdReservatorio().equals(idReservatorioAtual)) {
				throw new BusinessException("A estufa informada já possui um reservatório vinculado");
			}
		});
	}

	private void aplicarDados(Reservatorio reservatorio, ReservatorioRequestDTO dto, Estufa estufa) {
		reservatorio.setCapacidadeMaxLitros(dto.getCapacidadeMaxLitros());
		reservatorio.setNivelAtualPercentual(dto.getNivelAtualPercentual());
		reservatorio.setEstufa(estufa);
	}

	private ReservatorioResponseDTO toResponse(Reservatorio reservatorio) {
		return new ReservatorioResponseDTO(
				reservatorio.getIdReservatorio(),
				reservatorio.getEstufa().getIdEstufa(),
				reservatorio.getCapacidadeMaxLitros(),
				reservatorio.getNivelAtualPercentual());
	}
}
