package thumbtack.school.tracking;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import thumbtack.school.tracking.service.TrackerService;

import java.util.concurrent.ExecutionException;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class TableCreator implements CommandLineRunner {
    @Autowired
    private TrackerService service;
    private static final String TABLE_NAME = "userTracker";

    @Override
    public void run(String... args) throws ExecutionException, InterruptedException {
        log.info("Start TableCreator");
        service.createTable(TABLE_NAME);
    }
}
