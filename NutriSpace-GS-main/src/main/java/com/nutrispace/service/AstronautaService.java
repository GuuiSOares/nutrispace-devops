package com.nutrispace.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutrispace.dto.AstronautaRequestDTO;
import com.nutrispace.dto.AstronautaResponseDTO;
import com.nutrispace.dto.LoginRequestDTO;
import com.nutrispace.exception.BusinessException;
import com.nutrispace.exception.ResourceNotFoundException;
import com.nutrispace.model.Astronauta;
import com.nutrispace.dto.LoginResponseRecord;
import com.nutrispace.repository.AstronautaRepository;
import com.nutrispace.repository.EstufaRepository;
import com.nutrispace.config.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AstronautaService {

	private final AstronautaRepository astronautaRepository;
	private final EstufaRepository estufaRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public List<AstronautaResponseDTO> listarTodos() {
		return astronautaRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public AstronautaResponseDTO buscarPorId(Long id) {
		return toResponse(buscarEntidade(id));
	}

	@Transactional
	public AstronautaResponseDTO criar(AstronautaRequestDTO dto) {
		if (astronautaRepository.existsByEmail(dto.getEmail())) {
			throw new BusinessException("Já existe um astronauta cadastrado com este e-mail");
		}
		Astronauta astronauta = new Astronauta();
		aplicarDados(astronauta, dto);
		return toResponse(astronautaRepository.save(astronauta));
	}

	@Transactional
	public AstronautaResponseDTO atualizar(Long id, AstronautaRequestDTO dto) {
		if (astronautaRepository.existsByEmailAndIdAstronautaNot(dto.getEmail(), id)) {
			throw new BusinessException("Já existe outro astronauta cadastrado com este e-mail");
		}
		Astronauta astronauta = buscarEntidade(id);
		aplicarDados(astronauta, dto);
		return toResponse(astronautaRepository.save(astronauta));
	}

	@Transactional
	public void excluir(Long id) {
		if (!astronautaRepository.existsById(id)) {
			throw new ResourceNotFoundException("Astronauta não encontrado com id: " + id);
		}
		if (estufaRepository.existsByAstronautaIdAstronauta(id)) {
			throw new BusinessException("Não é possível excluir astronauta vinculado a estufa");
		}
		astronautaRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public LoginResponseRecord autenticar(LoginRequestDTO dto) {
		Astronauta astronauta = astronautaRepository.findByEmail(dto.getEmail())
				.orElseThrow(() -> new BusinessException("E-mail ou senha inválidos"));

		if (!senhaConfere(dto.getSenha(), astronauta.getSenha())) {
			throw new BusinessException("E-mail ou senha inválidos");
		}

		String token = jwtService.gerarToken(astronauta.getEmail(), astronauta.getIdAstronauta());
		return new LoginResponseRecord(
				astronauta.getIdAstronauta(),
				astronauta.getNome(),
				astronauta.getCargo(),
				astronauta.getEmail(),
				token,
				"Bearer");
	}

	@Transactional(readOnly = true)
	public Astronauta buscarEntidade(Long id) {
		return astronautaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Astronauta não encontrado com id: " + id));
	}

	private void aplicarDados(Astronauta astronauta, AstronautaRequestDTO dto) {
		astronauta.setNome(dto.getNome());
		astronauta.setCargo(dto.getCargo());
		astronauta.setEmail(dto.getEmail());
		if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
			astronauta.setSenha(dto.getSenha());
		}
	}

	/** Aceita senha do DDL (texto) ou BCrypt (cadastro via API). */
	private boolean senhaConfere(String senhaInformada, String senhaArmazenada) {
		if (senhaArmazenada != null && senhaArmazenada.startsWith("$2")) {
			return passwordEncoder.matches(senhaInformada, senhaArmazenada);
		}
		return senhaInformada != null && senhaInformada.equals(senhaArmazenada);
	}

	private AstronautaResponseDTO toResponse(Astronauta astronauta) {
		return new AstronautaResponseDTO(
				astronauta.getIdAstronauta(),
				astronauta.getNome(),
				astronauta.getCargo(),
				astronauta.getEmail());
	}
}
