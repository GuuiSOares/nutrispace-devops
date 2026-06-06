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

import com.nutrispace.dto.ReservatorioRequestDTO;
import com.nutrispace.dto.ReservatorioResponseDTO;
import com.nutrispace.service.ReservatorioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservatorios")
@RequiredArgsConstructor
public class ReservatorioController {

	private final ReservatorioService reservatorioService;

	@GetMapping
	public ResponseEntity<List<ReservatorioResponseDTO>> listar() {
		return ResponseEntity.ok(reservatorioService.listarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReservatorioResponseDTO> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(reservatorioService.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<ReservatorioResponseDTO> criar(@Valid @RequestBody ReservatorioRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(reservatorioService.criar(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ReservatorioResponseDTO> atualizar(
			@PathVariable Long id,
			@Valid @RequestBody ReservatorioRequestDTO dto) {
		return ResponseEntity.ok(reservatorioService.atualizar(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		reservatorioService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
