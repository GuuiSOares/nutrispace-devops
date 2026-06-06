package com.nutrispace.dto;

import java.time.LocalDateTime;

import com.nutrispace.model.Colheita.QualidadeColheita;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColheitaRequestDTO {

	@NotNull(message = "O identificador da estufa é obrigatório")
	private Long idEstufa;

	@NotNull(message = "A quantidade em kg é obrigatória")
	@Positive(message = "A quantidade deve ser maior que zero")
	private Double quantidadeKg;

	private LocalDateTime dtColheita;

	@NotNull(message = "A qualidade da colheita é obrigatória")
	private QualidadeColheita qualidade;
}
