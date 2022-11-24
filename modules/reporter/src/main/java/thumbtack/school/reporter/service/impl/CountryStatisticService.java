package thumbtack.school.reporter.service.impl;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.stereotype.Service;
import thumbtack.school.hbase.model.User;
import thumbtack.school.postgres.dao.CountryStatisticRepository;
import thumbtack.school.postgres.model.CountryStatistic;
import thumbtack.school.reporter.service.AbstractStatisticService;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CountryStatisticService extends AbstractStatisticService<CountryStatistic, CountryStatisticRepository> {
    private final DatabaseReader databaseReader;
    private static final String IP_ADDRESS_HEADER_NAME = "IP address";

    public CountryStatisticService(CountryStatisticRepository repository, DatabaseReader databaseReader) {
        super(repository);
        this.databaseReader = databaseReader;
    }

    @Override
    public List<CountryStatistic> getStatistic(List<User> users) {
        List<String> ipAddresses = users.stream()
                .map(this::getIpAddressesFromUser)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return getCountryStatistic(ipAddresses);
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
                ip = databaseReader.country(InetAddress.getByName(ipAddress)).getCountry().getName();
            } catch (IOException | GeoIp2Exception e) {
                ip = "unknown";
            }
            map.merge(ip, 1L, Long::sum);
        }
        return map.entrySet().stream()
                .map(entry -> new CountryStatistic(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
