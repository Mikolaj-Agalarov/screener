package cryptoDOM;

import com.google.gson.Gson;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;


@SpringBootApplication
@EnableScheduling
public class UserApplication {

//    @Bean
//    public Flyway flyway(DataSource dataSource) {
//        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
//        flyway.migrate();
//        return flyway;
//    }
    public static void main(String[] args) {

        SpringApplication.run(UserApplication.class, args);

        }




}


