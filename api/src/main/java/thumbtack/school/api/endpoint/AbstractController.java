package thumbtack.school.api.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import thumbtack.school.api.service.StatisticService;
import thumbtack.school.postgres.dto.StatisticDto;

import java.util.List;

@Service
@AllArgsConstructor
public abstract class AbstractController<S extends StatisticService>
        implements CommonController {
    protected S service;

    @GetMapping
    public List<StatisticDto> getAll(@PathVariable(value = "from", required = false) String from,
                                     @PathVariable(value = "to", required = false) String to) {
        return service.getForIntervalGrouped(from, to);
    }
}
