package thumbtack.school.api.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thumbtack.school.api.service.UserStatisticService;

@RestController
@RequestMapping("/users")
public class UserStatisticEndpoint extends AbstractController<UserStatisticService> {
    public UserStatisticEndpoint(UserStatisticService service) {
        super(service);
    }
}
