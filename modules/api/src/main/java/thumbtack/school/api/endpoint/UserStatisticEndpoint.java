package thumbtack.school.api.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import thumbtack.school.api.service.UserStatisticService;
import thumbtack.school.postgres.model.UserStatistic;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserStatisticEndpoint {
    private UserStatisticService service;

    @GetMapping
    public List<UserStatistic> getAll(@RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                      @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        return service.getAll(from, to);
    }
}
