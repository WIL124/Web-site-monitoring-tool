package thumbtack.school.api.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thumbtack.school.api.service.CountryStatisticService;

@RestController
@RequestMapping("/countries")
public class CountryStatisticEndpoint extends AbstractController<CountryStatisticService> {
    public CountryStatisticEndpoint(CountryStatisticService service) {
        super(service);
    }
}
