package com.nutrispace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

	private Long idAstronauta;
	private String nome;
	private String cargo;
	private String email;
}
