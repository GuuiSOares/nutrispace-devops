package com.nutrispace.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_NS_ESTUFA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estufa {

	public enum StatusBomba {
		LIGADA,
		DESLIGADA
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ns_estufa")
	@SequenceGenerator(name = "seq_ns_estufa", sequenceName = "SEQ_NS_ESTUFA", allocationSize = 1)
	@Column(name = "ID_ESTUFA")
	private Long idEstufa;

	@Column(name = "NOME_ESTUFA", length = 60)
	private String nomeEstufa;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS_BOMBA", length = 20)
	private StatusBomba statusBomba;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PLANTA", nullable = false)
	private Planta planta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ASTRONAUTA", nullable = false)
	private Astronauta astronauta;

	@OneToOne(mappedBy = "estufa", fetch = FetchType.LAZY)
	private Reservatorio reservatorio;
}
