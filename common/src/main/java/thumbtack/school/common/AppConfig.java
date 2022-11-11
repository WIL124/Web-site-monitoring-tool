package thumbtack.school.common;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AppConfig {
    @Bean
    org.apache.hadoop.conf.Configuration configuration() {
        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "hbase");
        config.set("hbase.zookeeper.property.clientPort", "2181");
        return config;
    }

    @Bean
    public ExecutorService taskExecutor() {
        return Executors.newCachedThreadPool();
    }
}
