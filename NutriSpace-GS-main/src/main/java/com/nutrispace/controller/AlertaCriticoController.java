package com.nutrispace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutrispace.dto.AlertaCriticoResponseDTO;
import com.nutrispace.service.AlertaCriticoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/alertas")
@RequiredArgsConstructor
public class AlertaCriticoController {

	private final AlertaCriticoService alertaCriticoService;

	@GetMapping("/ativos")
	public ResponseEntity<List<AlertaCriticoResponseDTO>> listarAtivos() {
		return ResponseEntity.ok(alertaCriticoService.listarAtivos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AlertaCriticoResponseDTO> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(alertaCriticoService.buscarPorId(id));
	}

	@GetMapping("/estufa/{idEstufa}")
	public ResponseEntity<List<AlertaCriticoResponseDTO>> listarPorEstufa(@PathVariable Long idEstufa) {
		return ResponseEntity.ok(alertaCriticoService.listarPorEstufa(idEstufa));
	}

	@PatchMapping("/{id}/resolver")
	public ResponseEntity<AlertaCriticoResponseDTO> resolver(@PathVariable Long id) {
		return ResponseEntity.ok(alertaCriticoService.resolver(id));
	}
}
