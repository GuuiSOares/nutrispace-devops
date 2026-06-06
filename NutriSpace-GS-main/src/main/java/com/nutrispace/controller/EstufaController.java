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

import com.nutrispace.dto.EstufaRequestDTO;
import com.nutrispace.dto.EstufaResponseDTO;
import com.nutrispace.service.EstufaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/estufas")
@RequiredArgsConstructor
public class EstufaController {

	private final EstufaService estufaService;

	@GetMapping
	public ResponseEntity<List<EstufaResponseDTO>> listar() {
		return ResponseEntity.ok(estufaService.listarTodas());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EstufaResponseDTO> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(estufaService.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<EstufaResponseDTO> criar(@Valid @RequestBody EstufaRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(estufaService.criar(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EstufaResponseDTO> atualizar(
			@PathVariable Long id,
			@Valid @RequestBody EstufaRequestDTO dto) {
		return ResponseEntity.ok(estufaService.atualizar(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		estufaService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
