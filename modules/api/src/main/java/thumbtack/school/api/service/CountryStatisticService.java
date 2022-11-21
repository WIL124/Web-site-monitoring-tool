package thumbtack.school.api.service;

import org.springframework.stereotype.Service;
import thumbtack.school.postgres.dao.CountryStatisticRepository;
import thumbtack.school.postgres.model.CountryStatistic;

@Service
public class CountryStatisticService extends AbstractStatisticService<CountryStatistic, CountryStatisticRepository> {
    public CountryStatisticService(CountryStatisticRepository repository) {
        super(repository);
    }
}
