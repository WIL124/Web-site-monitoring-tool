package thumbtack.school.api.service;

import org.springframework.stereotype.Service;
import thumbtack.school.postgres.dao.PageStatisticRepository;
import thumbtack.school.postgres.model.PageStatistic;

@Service
public class PageStatisticService extends AbstractStatisticService<PageStatistic, PageStatisticRepository> {
    public PageStatisticService(PageStatisticRepository repository) {
        super(repository);
    }
}
