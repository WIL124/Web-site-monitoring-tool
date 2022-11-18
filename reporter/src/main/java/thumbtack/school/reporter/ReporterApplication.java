package thumbtack.school.reporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "thumbtack.school")
@EnableJpaRepositories(basePackages = "thumbtack.school")
@EntityScan(basePackages = "thumbtack.school.postgres.model")
@EnableScheduling
public class ReporterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReporterApplication.class, args);
    }
}
