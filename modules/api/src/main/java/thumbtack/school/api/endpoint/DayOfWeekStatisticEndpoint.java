package thumbtack.school.api.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thumbtack.school.api.service.DayOfWeekStatisticService;

@RestController
@RequestMapping("/days")
public class DayOfWeekStatisticEndpoint extends AbstractController<DayOfWeekStatisticService> {
    public DayOfWeekStatisticEndpoint(DayOfWeekStatisticService service) {
        super(service);
    }
}
