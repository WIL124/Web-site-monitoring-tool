package thumbtack.school.postgres.dao;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.model.CountryStatistic;

import java.io.IOException;
import java.net.InetAddress;

@Repository
public interface CountryStatisticRepository extends CommonRepository<CountryStatistic> {

    default String getCountryFromIP(String ipAddress, DatabaseReader databaseReader) throws IOException, GeoIp2Exception {
        return databaseReader.country(InetAddress.getByName(ipAddress)).getCountry().getName();
    }
}
