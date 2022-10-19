package thumbtack.school.reporter.service.impl;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import thumbtack.school.reporter.service.GeolocationService;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Service
@NoArgsConstructor
@Slf4j
public class GeolocationServiceImpl implements GeolocationService {
    @Override
    public String getCountryFromIP(String ipAddress) {
        File database = new File("D:\\Projects\\step3\\monitoring\\src\\main\\resources\\GeoLite2-City.mmdb");
        try (DatabaseReader dbReader = new DatabaseReader.Builder(database).build()) {
            return dbReader.country(InetAddress.getByName(ipAddress)).getCountry().getName();
        } catch (IOException | GeoIp2Exception e) {
            log.warn(e.getMessage());
            return "unknown";
        }
    }
}
