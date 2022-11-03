package thumbtack.school.tracking;

import lombok.NoArgsConstructor;
import org.apache.hadoop.fs.Path;
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
        String path = this.getClass()
                .getClassLoader()
                .getResource("hbase-site.xml")
                .getPath();
        config.addResource(new Path(path));
        return config;
    }
    @Bean
    public ExecutorService taskExecutor() {
        return Executors.newCachedThreadPool();
    }
}
