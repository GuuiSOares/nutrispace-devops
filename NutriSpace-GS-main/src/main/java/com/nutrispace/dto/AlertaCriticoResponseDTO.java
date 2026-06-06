package com.nutrispace.dto;

import java.time.LocalDateTime;

import com.nutrispace.model.AlertaCritico.StatusAlerta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertaCriticoResponseDTO {

	private Long idAlerta;
	private Long idEstufa;
	private String nomeEstufa;
	private String nomePlanta;
	private String descricaoIncidente;
	private StatusAlerta statusAlerta;
	private LocalDateTime dtHrAlerta;
}
