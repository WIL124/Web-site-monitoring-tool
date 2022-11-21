package thumbtack.school.api.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import thumbtack.school.api.service.StatisticService;
import thumbtack.school.postgres.dto.StatisticDto;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Validated
public abstract class AbstractController<S extends StatisticService> implements CommonController {
    protected S service;

    @GetMapping
    public List<StatisticDto> getAll(@RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate from,
                                     @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate to) {
        return service.getAllGroupedByNameAndCreatedAtBetween(from, to);
    }
}
