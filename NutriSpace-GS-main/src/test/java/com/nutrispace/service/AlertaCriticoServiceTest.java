package com.nutrispace.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nutrispace.model.AlertaCritico;
import com.nutrispace.model.Estufa;
import com.nutrispace.model.Planta;
import com.nutrispace.model.AlertaCritico.StatusAlerta;
import com.nutrispace.repository.AlertaCriticoRepository;
import com.nutrispace.service.AlertaCriticoService;
import com.nutrispace.service.EstufaService;

@ExtendWith(MockitoExtension.class)
class AlertaCriticoServiceTest {

	@Mock
	private AlertaCriticoRepository alertaCriticoRepository;

	@Mock
	private EstufaService estufaService;

	@InjectMocks
	private AlertaCriticoService alertaCriticoService;

	@Test
	void deveCriarAlertaQuandoTemperaturaAcimaDoMaximo() {
		Planta planta = new Planta();
		planta.setTempMinIdeal(18.0);
		planta.setTempMaxIdeal(28.0);

		Estufa estufa = new Estufa();
		estufa.setIdEstufa(1L);
		estufa.setNomeEstufa("Alpha");
		estufa.setPlanta(planta);

		when(alertaCriticoRepository.save(any(AlertaCritico.class))).thenAnswer(inv -> inv.getArgument(0));

		Optional<AlertaCritico> resultado = alertaCriticoService.registrarAlertaTemperatura(estufa, 35.0);

		assertThat(resultado).isPresent();
		assertThat(resultado.get().getStatusAlerta()).isEqualTo(StatusAlerta.ATIVO);
		assertThat(resultado.get().getDescricaoIncidente()).contains("acima do máximo");
		verify(alertaCriticoRepository).save(any(AlertaCritico.class));
	}

	@Test
	void naoDeveCriarAlertaQuandoTemperaturaDentroDaFaixa() {
		Planta planta = new Planta();
		planta.setTempMinIdeal(18.0);
		planta.setTempMaxIdeal(28.0);

		Estufa estufa = new Estufa();
		estufa.setPlanta(planta);

		Optional<AlertaCritico> resultado = alertaCriticoService.registrarAlertaTemperatura(estufa, 22.0);

		assertThat(resultado).isEmpty();
	}
}
