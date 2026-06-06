package com.nutrispace.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutrispace.dto.HistoricoRegaRequestDTO;
import com.nutrispace.dto.HistoricoRegaResponseDTO;
import com.nutrispace.service.HistoricoRegaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/regas")
@RequiredArgsConstructor
public class HistoricoRegaController {

	private final HistoricoRegaService historicoRegaService;

	@PostMapping
	public ResponseEntity<HistoricoRegaResponseDTO> registrar(@Valid @RequestBody HistoricoRegaRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(historicoRegaService.registrar(dto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<HistoricoRegaResponseDTO> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(historicoRegaService.buscarPorId(id));
	}

	@GetMapping("/estufa/{idEstufa}")
	public ResponseEntity<List<HistoricoRegaResponseDTO>> listarPorEstufa(@PathVariable Long idEstufa) {
		return ResponseEntity.ok(historicoRegaService.listarPorEstufa(idEstufa));
	}
}
