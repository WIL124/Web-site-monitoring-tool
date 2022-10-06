package thumbtack.school.tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class UserTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserTrackerApplication.class, args);
    }
}
