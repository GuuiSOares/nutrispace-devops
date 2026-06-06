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

import com.nutrispace.dto.ColheitaRequestDTO;
import com.nutrispace.dto.ColheitaResponseDTO;
import com.nutrispace.service.ColheitaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/colheitas")
@RequiredArgsConstructor
public class ColheitaController {

	private final ColheitaService colheitaService;

	@PostMapping
	public ResponseEntity<ColheitaResponseDTO> registrar(@Valid @RequestBody ColheitaRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(colheitaService.registrar(dto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ColheitaResponseDTO> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(colheitaService.buscarPorId(id));
	}

	@GetMapping("/estufa/{idEstufa}")
	public ResponseEntity<List<ColheitaResponseDTO>> listarPorEstufa(@PathVariable Long idEstufa) {
		return ResponseEntity.ok(colheitaService.listarPorEstufa(idEstufa));
	}
}
