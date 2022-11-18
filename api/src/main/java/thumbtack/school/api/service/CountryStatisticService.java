package thumbtack.school.api.service;

import org.springframework.stereotype.Service;
import thumbtack.school.api.mapper.StatisticMapper;
import thumbtack.school.postgres.dao.CountryStatisticRepository;
import thumbtack.school.postgres.model.CountryStatistic;

@Service
public class CountryStatisticService extends AbstractStatisticService<CountryStatistic, CountryStatisticRepository> {
    public CountryStatisticService(CountryStatisticRepository repository, StatisticMapper statisticMapper, StatisticMapper mapper) {
        super(repository, statisticMapper, mapper);
    }
}
