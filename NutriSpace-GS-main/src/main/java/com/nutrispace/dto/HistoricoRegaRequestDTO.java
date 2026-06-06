package com.nutrispace.dto;

import java.time.LocalDateTime;

import com.nutrispace.model.HistoricoRega.TipoRega;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoRegaRequestDTO {

	@NotNull(message = "O identificador da estufa é obrigatório")
	private Long idEstufa;

	@NotNull(message = "O tipo de rega é obrigatório")
	private TipoRega tipoRega;

	private LocalDateTime dtHrRega;
}
