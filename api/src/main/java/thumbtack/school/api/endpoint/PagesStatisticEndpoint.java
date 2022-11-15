package thumbtack.school.api.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thumbtack.school.api.dto.IntervalRequest;
import thumbtack.school.api.dto.StatisticDto;
import thumbtack.school.api.service.PageStatisticService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pages")
@AllArgsConstructor
public class PagesStatisticEndpoint {
    private PageStatisticService service;

    @GetMapping
    public List<StatisticDto> getAll() {
        return service.getAllGrouped();
    }

    @GetMapping("/raw")
    public List<StatisticDto> getAllRaw() {
        return service.getAllRaw();
    }

    @GetMapping("/interval")
    public List<StatisticDto> getForInterval(@RequestBody @Valid IntervalRequest intervalRequest) {
        return service.getForInterval(intervalRequest);
    }

    @GetMapping("/interval/grouped")
    List<StatisticDto> getForIntervalGrouped(@RequestBody @Valid IntervalRequest intervalRequest) {
        return service.getForIntervalGrouped(intervalRequest);
    }
}
