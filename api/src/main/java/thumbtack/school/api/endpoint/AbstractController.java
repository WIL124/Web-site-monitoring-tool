package thumbtack.school.api.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import thumbtack.school.api.dto.IntervalRequest;
import thumbtack.school.api.dto.StatisticDto;
import thumbtack.school.api.service.StatisticService;

import javax.validation.Valid;
import java.util.List;

@Service
@AllArgsConstructor
public abstract class AbstractController<S extends StatisticService>
        implements CommonController {
    protected S service;

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
    public List<StatisticDto> getForIntervalGrouped(@RequestBody @Valid IntervalRequest intervalRequest) {
        return service.getForIntervalGrouped(intervalRequest);
    }
}
