package thumbtack.school.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.reporter.dao.DayOfWeekStatisticRepository;
import thumbtack.school.reporter.model.DayOfWeekStatistic;

import java.util.List;

@Service
@AllArgsConstructor
public class DayOfWeekService {
    private DayOfWeekStatisticRepository repository;
    public List<DayOfWeekStatistic> getAll(){
        return repository.findAll();
    }
}
