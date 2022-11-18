package thumbtack.school.api.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thumbtack.school.api.service.BrowserStatisticService;

@RestController
@RequestMapping("/browsers")
public class BrowsersStatisticEndpoint extends AbstractController<BrowserStatisticService> {
    public BrowsersStatisticEndpoint(BrowserStatisticService service) {
        super(service);
    }
}
