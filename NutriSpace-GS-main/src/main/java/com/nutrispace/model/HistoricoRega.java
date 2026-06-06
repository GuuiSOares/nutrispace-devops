package com.nutrispace.model;

import java.time.LocalDateTime;

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
@Table(name = "TB_NS_HISTORICO_REGA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoRega {

	public enum TipoRega {
		AUTOMATICA,
		MANUAL
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ns_rega")
	@SequenceGenerator(name = "seq_ns_rega", sequenceName = "SEQ_NS_REGA", allocationSize = 1)
	@Column(name = "ID_REGA")
	private Long idRega;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTUFA", nullable = false)
	private Estufa estufa;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO_REGA", length = 20)
	private TipoRega tipoRega;

	@Column(name = "DT_HR_REGA")
	private LocalDateTime dtHrRega;
}
