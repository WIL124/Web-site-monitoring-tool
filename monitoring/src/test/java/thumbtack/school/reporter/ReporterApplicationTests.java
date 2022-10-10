package thumbtack.school.reporter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thumbtack.school.reporter.service.ReporterServiceImpl;

import java.time.LocalDateTime;

@SpringBootTest
class ReporterApplicationTests {
    @Autowired
    ReporterServiceImpl reporterService;
    @Test
    void contextLoads() {
        reporterService.getReport(LocalDateTime.now());
    }

}
