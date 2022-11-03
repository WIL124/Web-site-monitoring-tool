package thumbtack.school.reporter.dao;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thumbtack.school.reporter.model.CountryStatistic;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Repository
public interface CountryStatisticRepository extends JpaRepository<CountryStatistic, Long> {
    default String getCountryFromIP(String ipAddress) throws IOException, GeoIp2Exception {
        File database = new File("D:\\Projects\\step3\\monitoring\\src\\main\\resources\\GeoLite2-City.mmdb");
        try (DatabaseReader dbReader = new DatabaseReader.Builder(database).build()) {
            return dbReader.country(InetAddress.getByName(ipAddress)).getCountry().getName();
        }
    }
}