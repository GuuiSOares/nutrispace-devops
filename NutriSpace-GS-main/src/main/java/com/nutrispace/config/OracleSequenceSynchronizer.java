package com.nutrispace.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Ajusta sequences Oracle quando o DDL inseriu IDs manualmente e a sequence ficou atrás do MAX(id).
 */
@Component
@Profile("oracle")
public class OracleSequenceSynchronizer implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(OracleSequenceSynchronizer.class);

	private record SequenceMapping(String table, String idColumn, String sequence) {
	}

	private static final SequenceMapping[] MAPPINGS = {
			new SequenceMapping("TB_NS_PLANTA", "ID_PLANTA", "SEQ_NS_PLANTA"),
			new SequenceMapping("TB_NS_ASTRONAUTA", "ID_ASTRONAUTA", "SEQ_NS_ASTRONAUTA"),
			new SequenceMapping("TB_NS_ESTUFA", "ID_ESTUFA", "SEQ_NS_ESTUFA"),
			new SequenceMapping("TB_NS_RESERVATORIO", "ID_RESERVATORIO", "SEQ_NS_RESERVATORIO"),
			new SequenceMapping("TB_NS_ALERTA_CRITICO", "ID_ALERTA", "SEQ_NS_ALERTA"),
			new SequenceMapping("TB_NS_LEITURA_SENSOR", "ID_LEITOR", "SEQ_NS_LEITOR"),
			new SequenceMapping("TB_NS_COLHEITA", "ID_COLHEITA", "SEQ_NS_COLHEITA"),
			new SequenceMapping("TB_NS_HISTORICO_REGA", "ID_REGA", "SEQ_NS_REGA")
	};

	private final JdbcTemplate jdbcTemplate;

	public OracleSequenceSynchronizer(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void run(ApplicationArguments args) {
		for (SequenceMapping mapping : MAPPINGS) {
			sincronizar(mapping);
		}
	}

	private void sincronizar(SequenceMapping mapping) {
		try {
			Long maxId = jdbcTemplate.queryForObject(
					"SELECT NVL(MAX(" + mapping.idColumn() + "), 0) FROM " + mapping.table(),
					Long.class);
			Long lastNumber = jdbcTemplate.queryForObject(
					"SELECT last_number FROM user_sequences WHERE sequence_name = ?",
					Long.class,
					mapping.sequence());

			if (lastNumber == null) {
				log.warn("Sequence {} não encontrada no schema", mapping.sequence());
				return;
			}

			if (maxId >= lastNumber) {
				long increment = maxId - lastNumber + 1;
				jdbcTemplate.execute("ALTER SEQUENCE " + mapping.sequence() + " INCREMENT BY " + increment);
				jdbcTemplate.queryForObject("SELECT " + mapping.sequence() + ".NEXTVAL FROM DUAL", Long.class);
				jdbcTemplate.execute("ALTER SEQUENCE " + mapping.sequence() + " INCREMENT BY 1");
				log.info("Sequence {} sincronizada (max {}={})", mapping.sequence(), mapping.idColumn(), maxId);
			}
		} catch (Exception ex) {
			log.warn("Falha ao sincronizar {}: {}", mapping.sequence(), ex.getMessage());
		}
	}
}
