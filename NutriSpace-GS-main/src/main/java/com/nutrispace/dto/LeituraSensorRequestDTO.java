package com.nutrispace.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeituraSensorRequestDTO {

	@NotNull(message = "O identificador da estufa é obrigatório")
	private Long idEstufa;

	@NotNull(message = "A temperatura lida é obrigatória")
	private Double temperaturaLida;

	@NotNull(message = "A umidade lida é obrigatória")
	@DecimalMin(value = "0.0", message = "A umidade deve estar entre 0 e 100")
	@DecimalMax(value = "100.0", message = "A umidade deve estar entre 0 e 100")
	private Double umidadeLida;
}
