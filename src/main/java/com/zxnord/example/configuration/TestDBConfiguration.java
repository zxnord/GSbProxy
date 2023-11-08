package com.zxnord.example.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class TestDBConfiguration {

    @Bean
    public CommandLineRunner initDatabase(JdbcTemplate template) {
        return args -> {
            // Carga los datos de prueba en la base de datos H2
            loadDataForTesting(template);
        };
    }

    private void loadDataForTesting(JdbcTemplate template) {
      template.execute("CREATE TABLE Product (ProductID INT, ProductName VARCHAR(100))");
      template.execute("CREATE TABLE ProductDescription (ProductID INT, ProductDescription VARCHAR(800))");

      template.execute("INSERT INTO Product VALUES (520, 'Amix banded ghoul'), (712, 'Realistic crow'), (314, 'Bat signal')");
      template.execute("INSERT INTO ProductDescription VALUES (520, 'A groovy banded ghoul rockstar.'), (712, 'Great quality fake crow for halloween.'), (314, 'Great signal for any moment.')");
    }

}
