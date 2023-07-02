package com.example.academia.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TriggerCreator {
    private final JdbcTemplate jdbcTemplate;

    public TriggerCreator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTrigger() {
        String sql = "CREATE TRIGGER preencher_data_inicio BEFORE INSERT ON cliente FOR EACH ROW SET NEW.data_inicio = CURDATE()";
        jdbcTemplate.execute(sql);
    }
}
