package thumbtack.school.reporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "thumbtack.school")
@EnableJpaRepositories
@EnableScheduling
public class ReporterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReporterApplication.class, args);
    }
}
