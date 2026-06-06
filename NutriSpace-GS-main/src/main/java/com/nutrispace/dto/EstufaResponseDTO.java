package com.nutrispace.dto;

import com.nutrispace.model.Estufa.StatusBomba;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstufaResponseDTO {

	private Long idEstufa;
	private String nomeEstufa;
	private StatusBomba statusBomba;
	private Long idReservatorio;
	private Double nivelReservatorioPercentual;
	private String nomePlanta;
	private Long idPlanta;
	private Long idAstronauta;
	private String nomeAstronauta;
}
