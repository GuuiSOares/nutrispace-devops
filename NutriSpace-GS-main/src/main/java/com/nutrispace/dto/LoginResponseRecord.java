package com.nutrispace.dto;

public record LoginResponseRecord(
		Long idAstronauta,
		String nome,
		String cargo,
		String email,
		String token,
		String tipoToken) {
}
