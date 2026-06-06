package com.nutrispace.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeituraSensorResponseDTO {

	private Long idLeitor;
	private Long idEstufa;
	private String nomeEstufa;
	private Double temperaturaLida;
	private Double umidadeLida;
	private LocalDateTime dtHrLeitura;
	private boolean alertaCriticoGerado;
	private Long idAlertaGerado;
}
