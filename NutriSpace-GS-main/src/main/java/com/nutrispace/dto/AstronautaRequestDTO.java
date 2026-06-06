package com.nutrispace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AstronautaRequestDTO {

	@NotBlank(message = "O nome é obrigatório")
	private String nome;

	@NotBlank(message = "O cargo é obrigatório")
	private String cargo;

	@NotBlank(message = "O e-mail é obrigatório")
	@Email(message = "E-mail inválido")
	private String email;

	@NotBlank(message = "A senha é obrigatória")
	private String senha;
}
