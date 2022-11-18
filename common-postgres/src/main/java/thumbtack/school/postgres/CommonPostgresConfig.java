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
    public DatabaseReader databaseReader(File databaseFile) throws IOException {
        return new DatabaseReader.Builder(databaseFile).build();
    }

    @Bean
    public File databaseFile() {
        try (InputStream initialStream = this.getClass().getClassLoader().getResourceAsStream("GeoLite2-City.mmdb")) {
            File target = new File("classpath:db_tmp_file");
            FileUtils.copyInputStreamToFile(initialStream, target);
            return target;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
