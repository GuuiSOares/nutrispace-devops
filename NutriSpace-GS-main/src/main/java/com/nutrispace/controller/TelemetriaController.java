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

import com.nutrispace.dto.LeituraSensorRequestDTO;
import com.nutrispace.dto.LeituraSensorResponseDTO;
import com.nutrispace.service.LeituraSensorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/telemetria")
@RequiredArgsConstructor
public class TelemetriaController {

	private final LeituraSensorService leituraSensorService;

	@PostMapping
	public ResponseEntity<LeituraSensorResponseDTO> registrar(@Valid @RequestBody LeituraSensorRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(leituraSensorService.registrarTelemetria(dto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<LeituraSensorResponseDTO> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(leituraSensorService.buscarPorId(id));
	}

	@GetMapping("/estufa/{idEstufa}")
	public ResponseEntity<List<LeituraSensorResponseDTO>> listarPorEstufa(@PathVariable Long idEstufa) {
		return ResponseEntity.ok(leituraSensorService.listarPorEstufa(idEstufa));
	}
}
