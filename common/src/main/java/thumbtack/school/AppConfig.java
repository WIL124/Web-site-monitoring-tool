package thumbtack.school;

import lombok.NoArgsConstructor;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.AsyncConnection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@NoArgsConstructor
@EnableAsync
public class AppConfig {
    @Bean
    @Scope(value = "prototype")
    public CompletableFuture<AsyncConnection> asyncConnection(org.apache.hadoop.conf.Configuration configuration) {
        return ConnectionFactory.createAsyncConnection(configuration);
    }
    @Bean
    org.apache.hadoop.conf.Configuration configuration(){
        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
        config.set("hbase.rpc.timeout", "1800000");
        config.set("hbase.client.scanner.timeout.period", "1800000");
        config.set("hbase.zookeeper.quorum", "hbase");
        config.set("hbase.zookeeper.property.clientPort", "2181");
        return config;
    }
    @Bean
    public ExecutorService taskExecutor() {
        return Executors.newCachedThreadPool();
    }
}
