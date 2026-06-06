package com.nutrispace.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Raiz", description = "Ponto de entrada HATEOAS da API")
public class ApiRootController {

	@GetMapping("/")
	@Operation(summary = "Links de navegação HATEOAS")
	public RepresentationModel<?> root() {
		RepresentationModel<?> model = new RepresentationModel<>();
		model.add(linkTo(methodOn(PlantaController.class).listar()).withRel("plantas"));
		model.add(linkTo(methodOn(EstufaController.class).listar()).withRel("estufas"));
		model.add(linkTo(methodOn(AstronautaController.class).listar()).withRel("astronautas"));
		model.add(linkTo(methodOn(AlertaCriticoController.class).listarAtivos()).withRel("alertas-ativos"));
		model.add(linkTo(AuthController.class).slash("login").withRel("login"));
		model.add(linkTo(ApiRootController.class).slash("swagger-ui.html").withRel("documentacao"));
		return model;
	}
}
