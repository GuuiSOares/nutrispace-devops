package com.nutrispace.model;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_NS_PLANTA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Planta {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ns_planta")
	@SequenceGenerator(name = "seq_ns_planta", sequenceName = "SEQ_NS_PLANTA", allocationSize = 1)
	@Column(name = "ID_PLANTA")
	private Long idPlanta;

	@Column(name = "NOME_PLANTA", length = 80)
	private String nomePlanta;

	@JdbcTypeCode(SqlTypes.NUMERIC)
	@Column(name = "TEMP_MIN_IDEAL")
	private Double tempMinIdeal;

	@JdbcTypeCode(SqlTypes.NUMERIC)
	@Column(name = "TEMP_MAX_IDEAL")
	private Double tempMaxIdeal;

	@JdbcTypeCode(SqlTypes.NUMERIC)
	@Column(name = "UMI_MIN_IDEAL")
	private Double umiMinIdeal;
}
