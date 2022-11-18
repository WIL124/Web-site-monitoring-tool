package thumbtack.school.postgres;

import com.maxmind.geoip2.DatabaseReader;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class CommonPostgresConfig {
    @Bean
    public DatabaseReader databaseReader() throws IOException {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("GeoLite2-City.mmdb");
             DatabaseReader databaseReader = new DatabaseReader.Builder(inputStream).build()) {
            return databaseReader;
        }
    }
}
