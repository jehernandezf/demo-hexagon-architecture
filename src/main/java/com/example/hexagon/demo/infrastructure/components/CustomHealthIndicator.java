package com.example.hexagon.demo.infrastructure.components;

import java.sql.Connection;

import javax.sql.DataSource;

import org.jspecify.annotations.Nullable;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    public CustomHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public @Nullable Health health() {
        Health health = null;
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) {
                health = Health.up().withDetail("database", "reachable").build();
            } else {
                health = Health.down().withDetail("database", "not reachable").build();
            }
        } catch (Exception e) {
            health = Health.down(e).build();
        }
        return health;
    }
}
