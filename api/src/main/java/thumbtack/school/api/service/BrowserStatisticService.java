package thumbtack.school.api.service;

import org.springframework.stereotype.Service;
import thumbtack.school.api.mapper.StatisticMapper;
import thumbtack.school.postgres.dao.BrowserStatisticRepository;
import thumbtack.school.postgres.model.BrowserStatistic;

@Service
public class BrowserStatisticService extends AbstractStatisticService<BrowserStatistic, BrowserStatisticRepository> {
    public BrowserStatisticService(BrowserStatisticRepository repository, StatisticMapper statisticMapper, StatisticMapper mapper) {
        super(repository, statisticMapper, mapper);
    }
}
