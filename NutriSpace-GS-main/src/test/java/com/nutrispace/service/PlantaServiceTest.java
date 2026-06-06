package com.nutrispace.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nutrispace.dto.PlantaRequestDTO;
import com.nutrispace.exception.BusinessException;
import com.nutrispace.model.Planta;
import com.nutrispace.repository.PlantaRepository;
import com.nutrispace.service.PlantaService;

@ExtendWith(MockitoExtension.class)
class PlantaServiceTest {

	@Mock
	private PlantaRepository plantaRepository;

	@InjectMocks
	private PlantaService plantaService;

	@Test
	void deveRejeitarTemperaturaMinimaMaiorQueMaxima() {
		PlantaRequestDTO dto = new PlantaRequestDTO("Alface", 30.0, 20.0, 50.0);

		assertThatThrownBy(() -> plantaService.criar(dto))
				.isInstanceOf(BusinessException.class)
				.hasMessageContaining("temperatura máxima");
	}

	@Test
	void deveCriarPlantaComDadosValidos() {
		PlantaRequestDTO dto = new PlantaRequestDTO("Tomate", 18.0, 28.0, 60.0);
		Planta salva = new Planta();
		salva.setIdPlanta(1L);
		salva.setNomePlanta("Tomate");
		salva.setTempMinIdeal(18.0);
		salva.setTempMaxIdeal(28.0);
		salva.setUmiMinIdeal(60.0);

		when(plantaRepository.save(any(Planta.class))).thenReturn(salva);

		var resultado = plantaService.criar(dto);

		assertThat(resultado.getNomePlanta()).isEqualTo("Tomate");
	}
}
