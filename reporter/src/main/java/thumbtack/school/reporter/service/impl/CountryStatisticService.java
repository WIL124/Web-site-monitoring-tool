package thumbtack.school.reporter.service.impl;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import thumbtack.school.postgres.model.CountryStatistic;
import thumbtack.school.hbase.model.User;
import thumbtack.school.postgres.dao.CountryStatisticRepository;
import thumbtack.school.reporter.service.StatisticService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CountryStatisticService implements StatisticService<CountryStatistic> {
    private CountryStatisticRepository repository;
    private static final String IP_ADDRESS_HEADER_NAME = "IP address";

    @Override
    public List<CountryStatistic> getStatistic(List<User> users) {
        List<String> ipAddresses = users.stream()
                .map(this::getIpAddressesFromUser)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return getCountryStatistic(ipAddresses);
    }

    @Override
    public void saveAll(List<CountryStatistic> statistic) {
        repository.saveAll(statistic);
    }

    private List<String> getIpAddressesFromUser(User user) {
        return user.getTimestampHeadersMap().values().stream()
                .map(headers -> headers.getFirst(IP_ADDRESS_HEADER_NAME))
                .collect(Collectors.toList());
    }

    private List<CountryStatistic> getCountryStatistic(List<String> ipAddressList) {
        Map<String, Long> map = new HashMap<>();
        for (String ipAddress : ipAddressList) {
            String ip;
            try {
                ip = repository.getCountryFromIP(ipAddress);
            } catch (IOException | GeoIp2Exception e) {
                log.warn(e.getMessage());
                ip = "unknown";
            }
            map.merge(ip, 1L, Long::sum);
        }
        return map.entrySet().stream()
                .map(entry -> new CountryStatistic(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
