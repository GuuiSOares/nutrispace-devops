package com.nutrispace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservatorioResponseDTO {

	private Long idReservatorio;
	private Long idEstufa;
	private Double capacidadeMaxLitros;
	private Double nivelAtualPercentual;
}
