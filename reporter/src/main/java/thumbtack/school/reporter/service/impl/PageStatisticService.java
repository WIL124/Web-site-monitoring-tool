package thumbtack.school.reporter.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.reporter.dao.PageStatisticRepository;
import thumbtack.school.reporter.model.PageStatistic;
import thumbtack.school.reporter.service.StatisticService;
import thumbtack.school.common.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PageStatisticService implements StatisticService<PageStatistic> {
    private PageStatisticRepository repository;

    @Override
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

    @Override
    public void saveAll(List<PageStatistic> statistic) {
        repository.saveAll(statistic);
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
