package com.nutrispace.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nutrispace.config.JwtService;

class JwtServiceTest {

	private JwtService jwtService;

	@BeforeEach
	void setUp() {
		jwtService = new JwtService(
				"NutriSpaceFIAP2026SecretKeyComNoMinimo256BitsParaAssinaturaJWT!!",
				3600000);
	}

	@Test
	void deveGerarEValidarToken() {
		String token = jwtService.gerarToken("astro@fiap.com", 1L);

		assertThat(jwtService.tokenValido(token)).isTrue();
		assertThat(jwtService.extrairEmail(token)).isEqualTo("astro@fiap.com");
		assertThat(jwtService.extrairIdAstronauta(token)).isEqualTo(1L);
	}
}
