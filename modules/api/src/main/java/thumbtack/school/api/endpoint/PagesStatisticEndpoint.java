package thumbtack.school.api.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thumbtack.school.api.service.PageStatisticService;

@RestController
@RequestMapping("/pages")
public class PagesStatisticEndpoint extends AbstractController<PageStatisticService> {
    public PagesStatisticEndpoint(PageStatisticService service) {
        super(service);
    }
}
