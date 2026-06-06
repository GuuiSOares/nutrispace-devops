package com.nutrispace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutrispace.dto.LoginRequestDTO;
import com.nutrispace.dto.LoginResponseRecord;
import com.nutrispace.service.AstronautaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Login com JWT")
public class AuthController {

	private final AstronautaService astronautaService;

	@PostMapping("/login")
	@Operation(summary = "Autenticar astronauta e obter token JWT")
	public ResponseEntity<LoginResponseRecord> login(@Valid @RequestBody LoginRequestDTO dto) {
		return ResponseEntity.ok(astronautaService.autenticar(dto));
	}
}
