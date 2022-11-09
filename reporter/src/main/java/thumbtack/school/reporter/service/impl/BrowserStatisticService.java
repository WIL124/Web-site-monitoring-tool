package thumbtack.school.reporter.service.impl;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import thumbtack.school.model.User;
import thumbtack.school.reporter.dao.BrowserStatisticRepository;
import thumbtack.school.reporter.model.BrowserStatistic;
import thumbtack.school.reporter.service.StatisticService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BrowserStatisticService implements StatisticService<BrowserStatistic> {
    private BrowserStatisticRepository repository;

    @Override
    public List<BrowserStatistic> getStatistic(List<User> users) {
        return getBrowserStatistic(users.stream()
                .map(this::getUserAgentsFromUser)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
    }

    @Override
    public void saveAll(List<BrowserStatistic> statistic) {
        repository.saveAll(statistic);
    }

    private List<UserAgent> getUserAgentsFromUser(User user) {
        return user.getTimestampHeadersMap().values().stream()
                .map(headers -> headers.getFirst("user-agent"))
                .map(UserAgent::parseUserAgentString).collect(Collectors.toList());
    }

    private List<BrowserStatistic> getBrowserStatistic(List<UserAgent> userAgentList) {
        Map<String, Long> map = new HashMap<>();
        for (UserAgent userAgent : userAgentList) {
            map.merge(userAgent.getBrowser().getName(), 1L, Long::sum);
        }
        return map.entrySet().stream()
                .map(entry -> new BrowserStatistic(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
