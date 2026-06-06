package com.nutrispace.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservatorioRequestDTO {

	@NotNull(message = "O identificador da estufa é obrigatório")
	private Long idEstufa;

	@NotNull(message = "A capacidade máxima em litros é obrigatória")
	@Positive(message = "A capacidade máxima deve ser maior que zero")
	private Double capacidadeMaxLitros;

	@NotNull(message = "O nível atual percentual é obrigatório")
	private Double nivelAtualPercentual;
}
