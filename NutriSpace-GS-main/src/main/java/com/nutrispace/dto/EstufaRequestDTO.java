package com.nutrispace.dto;

import com.nutrispace.model.Estufa.StatusBomba;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstufaRequestDTO {

	@NotBlank(message = "O nome da estufa é obrigatório")
	private String nomeEstufa;

	@NotNull(message = "O status da bomba é obrigatório")
	private StatusBomba statusBomba;

	@NotNull(message = "O identificador da planta é obrigatório")
	private Long idPlanta;

	@NotNull(message = "O identificador do astronauta responsável é obrigatório")
	private Long idAstronauta;
}
