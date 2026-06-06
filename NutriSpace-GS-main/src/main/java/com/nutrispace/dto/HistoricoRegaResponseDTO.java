package com.nutrispace.dto;

import java.time.LocalDateTime;

import com.nutrispace.model.HistoricoRega.TipoRega;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoRegaResponseDTO {

	private Long idRega;
	private Long idEstufa;
	private String nomeEstufa;
	private TipoRega tipoRega;
	private LocalDateTime dtHrRega;
}
