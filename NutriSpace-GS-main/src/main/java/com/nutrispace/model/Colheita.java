package com.nutrispace.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_NS_COLHEITA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Colheita {

	public enum QualidadeColheita {
		EXCELENTE,
		BOA,
		REGULAR,
		PREJUDICADA
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ns_colheita")
	@SequenceGenerator(name = "seq_ns_colheita", sequenceName = "SEQ_NS_COLHEITA", allocationSize = 1)
	@Column(name = "ID_COLHEITA")
	private Long idColheita;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTUFA", nullable = false)
	private Estufa estufa;

	@JdbcTypeCode(SqlTypes.NUMERIC)
	@Column(name = "QUANTIDADE_KG")
	private Double quantidadeKg;

	@Column(name = "DT_COLHEITA")
	private LocalDateTime dtColheita;

	@Enumerated(EnumType.STRING)
	@Column(name = "QUALIDADE", length = 20)
	private QualidadeColheita qualidade;
}
