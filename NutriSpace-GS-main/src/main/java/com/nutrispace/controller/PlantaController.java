package com.nutrispace.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

import com.nutrispace.dto.PlantaRequestDTO;
import com.nutrispace.controller.PlantaModelAssembler;
import com.nutrispace.dto.PlantaResponseRecord;
import com.nutrispace.service.PlantaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/plantas")
@RequiredArgsConstructor
@Tag(name = "Plantas", description = "CRUD de plantas cultivadas nas estufas")
@SecurityRequirement(name = "bearerAuth")
public class PlantaController {

	private final PlantaService plantaService;
	private final PlantaModelAssembler plantaAssembler;

	@GetMapping
	@Operation(summary = "Listar todas as plantas")
	public ResponseEntity<CollectionModel<EntityModel<PlantaResponseRecord>>> listar() {
		return ResponseEntity.ok(plantaAssembler.toCollectionModel(plantaService.listarTodasRecords()));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar planta por ID")
	public ResponseEntity<EntityModel<PlantaResponseRecord>> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(plantaAssembler.toModel(plantaService.buscarRecordPorId(id)));
	}

	@PostMapping
	@Operation(summary = "Cadastrar nova planta")
	public ResponseEntity<EntityModel<PlantaResponseRecord>> criar(@Valid @RequestBody PlantaRequestDTO dto) {
		PlantaResponseRecord criada = plantaService.criarRecord(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(plantaAssembler.toModel(criada));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar planta")
	public ResponseEntity<EntityModel<PlantaResponseRecord>> atualizar(
			@PathVariable Long id,
			@Valid @RequestBody PlantaRequestDTO dto) {
		return ResponseEntity.ok(plantaAssembler.toModel(plantaService.atualizarRecord(id, dto)));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Excluir planta")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		plantaService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
