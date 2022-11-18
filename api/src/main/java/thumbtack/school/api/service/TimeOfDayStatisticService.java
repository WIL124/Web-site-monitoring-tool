package thumbtack.school.api.service;

import org.springframework.stereotype.Service;
import thumbtack.school.api.mapper.StatisticMapper;
import thumbtack.school.postgres.dao.TimeOfDayStatisticRepository;
import thumbtack.school.postgres.model.TimeOfDayStatistic;

@Service
public class TimeOfDayStatisticService extends AbstractStatisticService<TimeOfDayStatistic, TimeOfDayStatisticRepository> {
    public TimeOfDayStatisticService(TimeOfDayStatisticRepository repository, StatisticMapper statisticMapper, StatisticMapper mapper) {
        super(repository, statisticMapper, mapper);
    }
}
