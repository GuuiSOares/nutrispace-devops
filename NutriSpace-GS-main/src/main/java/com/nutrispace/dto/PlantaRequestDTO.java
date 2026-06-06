package com.nutrispace.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantaRequestDTO {

	@NotBlank(message = "O nome da planta é obrigatório")
	private String nomePlanta;

	@NotNull(message = "A temperatura mínima ideal é obrigatória")
	private Double tempMinIdeal;

	@NotNull(message = "A temperatura máxima ideal é obrigatória")
	private Double tempMaxIdeal;

	@NotNull(message = "A umidade mínima ideal é obrigatória")
	@DecimalMin(value = "0.0", message = "A umidade mínima ideal deve ser entre 0 e 100")
	@DecimalMax(value = "100.0", message = "A umidade mínima ideal deve ser entre 0 e 100")
	private Double umiMinIdeal;
}
