package com.nutrispace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantaResponseDTO {

	private Long idPlanta;
	private String nomePlanta;
	private Double tempMinIdeal;
	private Double tempMaxIdeal;
	private Double umiMinIdeal;
}
