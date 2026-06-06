package com.nutrispace.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import com.nutrispace.controller.PlantaController;
import com.nutrispace.dto.PlantaResponseRecord;

@Component
public class PlantaModelAssembler {

	public EntityModel<PlantaResponseRecord> toModel(PlantaResponseRecord planta) {
		return EntityModel.of(planta,
				linkTo(methodOn(PlantaController.class).buscar(planta.idPlanta())).withSelfRel(),
				linkTo(methodOn(PlantaController.class).listar()).withRel("plantas"));
	}

	public CollectionModel<EntityModel<PlantaResponseRecord>> toCollectionModel(Iterable<PlantaResponseRecord> plantas) {
		var models = new java.util.ArrayList<EntityModel<PlantaResponseRecord>>();
		plantas.forEach(p -> models.add(toModel(p)));
		return CollectionModel.of(models, linkTo(methodOn(PlantaController.class).listar()).withSelfRel());
	}
}
