package thumbtack.school.api.service;

import org.springframework.stereotype.Service;
import thumbtack.school.postgres.dao.BrowserStatisticRepository;
import thumbtack.school.postgres.model.BrowserStatistic;

@Service
public class BrowserStatisticService extends AbstractStatisticService<BrowserStatistic, BrowserStatisticRepository> {
    public BrowserStatisticService(BrowserStatisticRepository repository) {
        super(repository);
    }
}
