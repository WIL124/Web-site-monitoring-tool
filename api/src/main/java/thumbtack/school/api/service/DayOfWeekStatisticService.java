package thumbtack.school.api.service;

import org.springframework.stereotype.Service;
import thumbtack.school.api.mapper.StatisticMapper;
import thumbtack.school.postgres.dao.DayOfWeekStatisticRepository;
import thumbtack.school.postgres.model.DayOfWeekStatistic;

@Service
public class DayOfWeekStatisticService extends AbstractStatisticService<DayOfWeekStatistic, DayOfWeekStatisticRepository> {
    public DayOfWeekStatisticService(DayOfWeekStatisticRepository repository, StatisticMapper statisticMapper, StatisticMapper mapper) {
        super(repository, statisticMapper, mapper);
    }
}
