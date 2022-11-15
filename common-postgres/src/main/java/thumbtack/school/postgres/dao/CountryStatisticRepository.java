package thumbtack.school.postgres.dao;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import thumbtack.school.postgres.model.CountryStatistic;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CountryStatisticRepository extends JpaRepository<CountryStatistic, Long> {
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

    default String getCountryFromIP(String ipAddress) throws IOException, GeoIp2Exception {
        File database = ResourceUtils.getFile("classpath:GeoLite2-City.mmdb");
        try (DatabaseReader dbReader = new DatabaseReader.Builder(database).build()) {
            return dbReader.country(InetAddress.getByName(ipAddress)).getCountry().getName();
        }
    }
}
