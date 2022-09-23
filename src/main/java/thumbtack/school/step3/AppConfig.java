package thumbtack.school.step3;

import lombok.NoArgsConstructor;
import org.apache.hadoop.hbase.client.AsyncConnection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@NoArgsConstructor
public class AppConfig {
    @Bean
    @Scope(value = "prototype")
    public CompletableFuture<AsyncConnection> asyncConnection() {
        return ConnectionFactory.createAsyncConnection(new org.apache.hadoop.conf.Configuration());
    }
    @Bean
    public ExecutorService taskExecutor() {
        return Executors.newCachedThreadPool();
    }
}
