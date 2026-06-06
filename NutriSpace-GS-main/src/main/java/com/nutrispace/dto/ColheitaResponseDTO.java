package com.nutrispace.dto;

import java.time.LocalDateTime;

import com.nutrispace.model.Colheita.QualidadeColheita;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColheitaResponseDTO {

	private Long idColheita;
	private Long idEstufa;
	private String nomeEstufa;
	private Double quantidadeKg;
	private LocalDateTime dtColheita;
	private QualidadeColheita qualidade;
}
