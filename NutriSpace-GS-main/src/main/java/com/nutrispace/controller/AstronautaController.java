package com.nutrispace.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutrispace.dto.AstronautaRequestDTO;
import com.nutrispace.dto.AstronautaResponseDTO;
import com.nutrispace.service.AstronautaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/astronautas")
@RequiredArgsConstructor
public class AstronautaController {

	private final AstronautaService astronautaService;

	@GetMapping
	public ResponseEntity<List<AstronautaResponseDTO>> listar() {
		return ResponseEntity.ok(astronautaService.listarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AstronautaResponseDTO> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(astronautaService.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<AstronautaResponseDTO> criar(@Valid @RequestBody AstronautaRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(astronautaService.criar(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<AstronautaResponseDTO> atualizar(
			@PathVariable Long id,
			@Valid @RequestBody AstronautaRequestDTO dto) {
		return ResponseEntity.ok(astronautaService.atualizar(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		astronautaService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
