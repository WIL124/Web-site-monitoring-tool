package thumbtack.school.reporter.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import thumbtack.school.reporter.service.ReporterServiceImpl;

@RestController
@AllArgsConstructor
public class ReporterEndpointImpl {
    ReporterServiceImpl service;

    @GetMapping
    public void moveDataFromHbaseToPostgres() {
        service.getReport();
    }
}
