package thumbtack.school.postgres.dao;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.model.CountryStatistic;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CountryStatisticRepository extends CommonRepository<CountryStatistic> {
    List<CountryStatistic> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT new CountryStatistic(b.name, SUM(b.count)) " +
            "FROM CountryStatistic as b " +
            "WHERE b.createdAt >= :#{#fromDate} " +
            "AND b.createdAt <= :#{#toDate} GROUP BY b.name")
    List<CountryStatistic> findByCreatedAtBetweenAndGroupedByName(@Param("fromDate") LocalDateTime fromDate,
                                                                  @Param("toDate") LocalDateTime to);

    @Query("SELECT new CountryStatistic(b.name, sum(b.count)) " +
            "FROM CountryStatistic as b GROUP BY b.name")
    List<CountryStatistic> selectAllGroupedByName();

    default String getCountryFromIP(String ipAddress, DatabaseReader databaseReader) throws IOException, GeoIp2Exception {
        return databaseReader.country(InetAddress.getByName(ipAddress)).getCountry().getName();
    }
}
