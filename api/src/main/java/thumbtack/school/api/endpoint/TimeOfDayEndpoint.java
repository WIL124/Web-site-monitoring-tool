package thumbtack.school.api.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thumbtack.school.api.service.TimeOfDayStatisticService;

@RestController
@RequestMapping("/time")
public class TimeOfDayEndpoint extends AbstractController<TimeOfDayStatisticService> {
    public TimeOfDayEndpoint(TimeOfDayStatisticService service) {
        super(service);
    }
}
