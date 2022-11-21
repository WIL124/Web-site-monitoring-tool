package thumbtack.school.postgres;

import com.maxmind.geoip2.DatabaseReader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class CommonPostgresConfig {
    @Bean
    @ConditionalOnNotWebApplication
    public DatabaseReader databaseReader() throws IOException {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("GeoLite2-City.mmdb")) {
            return new DatabaseReader.Builder(inputStream).build();
        }
    }
}
