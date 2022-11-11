package thumbtack.school.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "thumbtack.school")
@EnableJpaRepositories(basePackages = "thumbtack.school.reporter.dao" )
@EntityScan(basePackages = "thumbtack.school.reporter.model")
@AutoConfigurationPackage
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}