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
@Table(name = "TB_NS_ALERTA_CRITICO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertaCritico {

	public enum StatusAlerta {
		ATIVO,
		RESOLVIDO
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ns_alerta")
	@SequenceGenerator(name = "seq_ns_alerta", sequenceName = "SEQ_NS_ALERTA", allocationSize = 1)
	@Column(name = "ID_ALERTA")
	private Long idAlerta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTUFA", nullable = false)
	private Estufa estufa;

	@Column(name = "DESCRICAO_INCIDENTE", length = 300)
	private String descricaoIncidente;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS_ALERTA", length = 20)
	private StatusAlerta statusAlerta;

	@Column(name = "DT_HR_ALERTA")
	private LocalDateTime dtHrAlerta;
}
