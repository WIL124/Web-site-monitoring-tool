package thumbtack.school.step3;

import lombok.NoArgsConstructor;
import org.apache.hadoop.hbase.client.AsyncConnection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.ExecutionException;

@Configuration
@NoArgsConstructor
public class AppConfig {
    @Bean
    @Scope(value = "prototype")
    public AsyncConnection asyncConnection() throws ExecutionException, InterruptedException {
        return ConnectionFactory.createAsyncConnection(new org.apache.hadoop.conf.Configuration()).get();
    }
}
