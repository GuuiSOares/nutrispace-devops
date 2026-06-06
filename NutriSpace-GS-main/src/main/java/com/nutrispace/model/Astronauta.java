package com.nutrispace.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_NS_ASTRONAUTA")
@Getter
@Setter
@NoArgsConstructor
public class Astronauta {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ns_astronauta")
	@SequenceGenerator(name = "seq_ns_astronauta", sequenceName = "SEQ_NS_ASTRONAUTA", allocationSize = 1)
	@Column(name = "ID_ASTRONAUTA")
	private Long idAstronauta;

	@Column(name = "NOME", length = 50)
	private String nome;

	@Column(name = "CARGO", length = 50)
	private String cargo;

	@Column(name = "EMAIL", length = 60)
	private String email;

	@Column(name = "SENHA", length = 50)
	private String senha;
}
