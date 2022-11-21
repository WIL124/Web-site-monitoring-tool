package thumbtack.school.reporter.service.impl;

import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import thumbtack.school.hbase.model.User;
import thumbtack.school.postgres.dao.BrowserStatisticRepository;
import thumbtack.school.postgres.model.BrowserStatistic;
import thumbtack.school.reporter.service.AbstractStatisticService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BrowserStatisticService extends AbstractStatisticService<BrowserStatistic, BrowserStatisticRepository> {
    public BrowserStatisticService(BrowserStatisticRepository repository) {
        super(repository);
    }

    @Override
    public List<BrowserStatistic> getStatistic(List<User> users) {
        return getBrowserStatistic(users.stream()
                .map(this::getUserAgentsFromUser)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
    }

    private List<UserAgent> getUserAgentsFromUser(User user) {
        return user.getTimestampHeadersMap().values().stream()
                .map(headers -> headers.getFirst(HttpHeaders.USER_AGENT))
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
