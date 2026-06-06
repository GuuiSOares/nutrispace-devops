package com.nutrispace.model;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "TB_NS_RESERVATORIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservatorio {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ns_reservatorio")
	@SequenceGenerator(name = "seq_ns_reservatorio", sequenceName = "SEQ_NS_RESERVATORIO", allocationSize = 1)
	@Column(name = "ID_RESERVATORIO")
	private Long idReservatorio;

	@JdbcTypeCode(SqlTypes.NUMERIC)
	@Column(name = "CAPACIDADE_MAX_LITROS")
	private Double capacidadeMaxLitros;

	@JdbcTypeCode(SqlTypes.NUMERIC)
	@Column(name = "NIVEL_ATUAL_PERCENTUAL")
	private Double nivelAtualPercentual;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTUFA", nullable = false)
	private Estufa estufa;
}
