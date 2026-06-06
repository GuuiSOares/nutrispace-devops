package com.nutrispace.dto;

public record PlantaResponseRecord(
		Long idPlanta,
		String nomePlanta,
		Double tempMinIdeal,
		Double tempMaxIdeal,
		Double umiMinIdeal) {
}
