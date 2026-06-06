package com.nutrispace.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.nutrispace.controller.PlantaController;
import com.nutrispace.controller.PlantaModelAssembler;
import com.nutrispace.dto.PlantaResponseRecord;
import com.nutrispace.config.JwtAuthenticationFilter;
import com.nutrispace.config.JwtService;
import com.nutrispace.service.PlantaService;

@WebMvcTest(controllers = PlantaController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(PlantaModelAssembler.class)
@AutoConfigureMockMvc(addFilters = false)
class PlantaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PlantaService plantaService;

	@MockBean
	private JwtService jwtService;

	@MockBean
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Test
	void deveListarPlantasComLinksHateoas() throws Exception {
		when(plantaService.listarTodasRecords()).thenReturn(List.of(
				new PlantaResponseRecord(1L, "Alface", 18.0, 26.0, 55.0)));

		mockMvc.perform(get("/plantas"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.plantaResponseRecordList[0].nomePlanta").value("Alface"))
				.andExpect(jsonPath("$._links.self.href").exists());
	}

	@Test
	void deveBuscarPlantaPorId() throws Exception {
		when(plantaService.buscarRecordPorId(1L))
				.thenReturn(new PlantaResponseRecord(1L, "Alface", 18.0, 26.0, 55.0));

		mockMvc.perform(get("/plantas/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nomePlanta").value("Alface"))
				.andExpect(jsonPath("$._links.self.href").exists());
	}
}
