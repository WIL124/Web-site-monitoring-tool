package thumbtack.school.tracking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import thumbtack.school.tracking.service.TrackerService;

import java.util.concurrent.ExecutionException;

@Component
@AllArgsConstructor
@Slf4j
public class TableCreator implements CommandLineRunner {
    private TrackerService service;
    private static final String TABLE_NAME = "userTracker";

    @Override
    public void run(String... args) throws ExecutionException, InterruptedException {
        log.debug("Start TableCreator");
        service.createTable(TABLE_NAME);
    }
}
