package thumbtack.school.reporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import thumbtack.school.hbase.CommonHbaseConfig;
import thumbtack.school.postgres.CommonPostgresConfig;

@SpringBootApplication(scanBasePackages = "thumbtack.school")
@EnableJpaRepositories(basePackages = "thumbtack.school")
@EntityScan(basePackages = "thumbtack.school.postgres.model")
@EnableScheduling
@ImportAutoConfiguration({CommonPostgresConfig.class, CommonHbaseConfig.class})
public class ReporterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReporterApplication.class, args);
    }
}
