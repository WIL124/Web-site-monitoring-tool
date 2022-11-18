package thumbtack.school.reporter.service.impl;

import org.springframework.stereotype.Service;
import thumbtack.school.hbase.model.User;
import thumbtack.school.postgres.dao.PageStatisticRepository;
import thumbtack.school.postgres.model.PageStatistic;
import thumbtack.school.reporter.service.AbstractStatisticService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PageStatisticService extends AbstractStatisticService<PageStatistic, PageStatisticRepository> {

    public PageStatisticService(PageStatisticRepository repository) {
        super(repository);
    }

    public List<PageStatistic> getStatistic(List<User> users) {
        List<String> pages = users.stream()
                .map(user -> user.getTimestampHeadersMap().values().stream()
                        .map(headers -> headers.getFirst("referer"))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return collectPagesFromList(pages);
    }

    private List<PageStatistic> collectPagesFromList(List<String> pages) {
        Map<String, Long> map = new HashMap<>();
        for (String page : pages) {
            map.merge(page, 1L, Long::sum);
        }
        return map.entrySet().stream()
                .map(entry -> new PageStatistic(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
