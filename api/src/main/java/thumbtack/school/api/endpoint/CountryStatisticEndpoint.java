package thumbtack.school.api.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thumbtack.school.common.dto.StatisticDto;
import thumbtack.school.common.service.CountryStatisticService;

import java.util.List;

@RestController
@RequestMapping("/countries")
@AllArgsConstructor
public class CountryStatisticEndpoint {
    private CountryStatisticService service;
    @GetMapping
    public List<StatisticDto> getAll(){
        return service.getAll();
    }
}
