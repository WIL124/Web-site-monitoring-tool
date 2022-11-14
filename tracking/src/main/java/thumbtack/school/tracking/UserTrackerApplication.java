package thumbtack.school.tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"thumbtack.school.tracking", "thumbtack.school.hbase"})
public class UserTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserTrackerApplication.class, args);
    }
}
