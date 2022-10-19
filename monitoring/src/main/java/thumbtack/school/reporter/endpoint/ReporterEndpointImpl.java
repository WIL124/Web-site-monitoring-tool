package thumbtack.school.reporter.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import thumbtack.school.reporter.service.impl.ReporterServiceImpl;

@RestController
@AllArgsConstructor
public class ReporterEndpointImpl {
    ReporterServiceImpl service;

    //TODO Are the endpoint needed to us? OR only execution by cron?
    @GetMapping
    public void moveDataFromHbaseToPostgres() {
        service.getReport(0, Long.MAX_VALUE);
    }
}
