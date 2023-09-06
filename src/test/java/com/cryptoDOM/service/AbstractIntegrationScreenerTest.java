package com.cryptoDOM.service;

import com.cryptoDOM.entity.User;
import com.cryptoDOM.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;

import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@RunWith(SpringJUnit4ClassRunner.class)
@Testcontainers
@TestConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AbstractIntegrationScreenerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13.4")
            .withDatabaseName("screener")
            .withUsername("postgres")
            .withPassword("postgres")
            .withExposedPorts(5432);

    static {
        postgresContainer.start();
    }

    @Bean
    public String databaseUrl() {
        return postgresContainer.getJdbcUrl();
    }

    @Test
    public void shouldSaveUserEntityToDatabase() {
        User userEntity = new User(1L, "testName", "password", "email", null, null);
        userRepository.save(userEntity);
        User retrievedUserEntity = userRepository.findById(userEntity.getId()).orElse(null);

        boolean entitySaved = retrievedUserEntity != null;
        boolean usernamesMatch = userEntity.getUsername().equals(retrievedUserEntity != null ? retrievedUserEntity.getUsername() : null);

        Assert.assertTrue("Entity was not saved to the database", entitySaved);
        Assert.assertTrue("Entity property value does not match the expected value", usernamesMatch);

        if (entitySaved && usernamesMatch) {
            System.out.println("User entity saved successfully. Username: " + userEntity.getUsername());
        }
    }


    @Test
    public void shouldHaveFlywaySchemaHistoryTable() {
        boolean tableExists = jdbcTemplate.queryForObject(
                "SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'flyway_schema_history')",
                Boolean.class
        );

        if (tableExists) {
            System.out.println("Migration was applied successfully: 'flyway_schema_history' table exists.");
        } else {
            System.err.println("Migration may not have been applied: 'flyway_schema_history' table does not exist.");
        }

        Assert.assertTrue("The 'flyway_schema_history' table does not exist. Migrations may not have been applied.", tableExists);
    }
}
