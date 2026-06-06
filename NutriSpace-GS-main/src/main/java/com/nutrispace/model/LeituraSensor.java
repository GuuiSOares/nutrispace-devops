package com.nutrispace.model;

import java.time.LocalDateTime;

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
@Table(name = "TB_NS_LEITURA_SENSOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeituraSensor {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ns_leitor")
	@SequenceGenerator(name = "seq_ns_leitor", sequenceName = "SEQ_NS_LEITOR", allocationSize = 1)
	@Column(name = "ID_LEITOR")
	private Long idLeitor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTUFA", nullable = false)
	private Estufa estufa;

	@JdbcTypeCode(SqlTypes.NUMERIC)
	@Column(name = "TEMPERATURA_LIDA")
	private Double temperaturaLida;

	@JdbcTypeCode(SqlTypes.NUMERIC)
	@Column(name = "UMIDADE_LIDA")
	private Double umidadeLida;

	@Column(name = "DT_HR_LEITURA")
	private LocalDateTime dtHrLeitura;
}
