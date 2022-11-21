package thumbtack.school.postgres.dao;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.dto.StatisticDto;
import thumbtack.school.postgres.model.CountryStatistic;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CountryStatisticRepository extends CommonRepository<CountryStatistic> {
    @Override
    @Query("select new thumbtack.school.postgres.dto.StatisticDto(statistic.name, SUM(statistic.count)) " +
            "from CountryStatistic as statistic where statistic.createdAt between ?1 and ?2 group by statistic.name")
    List<StatisticDto> getStatisticGrouped(LocalDateTime from, LocalDateTime to);
    default String getCountryFromIP(String ipAddress, DatabaseReader databaseReader) throws IOException, GeoIp2Exception {
        return databaseReader.country(InetAddress.getByName(ipAddress)).getCountry().getName();
    }
}
