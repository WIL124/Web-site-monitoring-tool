package thumbtack.school.reporter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thumbtack.school.reporter.service.ReporterService;

@SpringBootTest
class ReporterApplicationTests {
    @Autowired
    ReporterService reporterService;
    @Test
    void contextLoads() {
        reporterService.getReport(0, Long.MAX_VALUE);
    }

}
