package thumbtack.school.step3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thumbtack.school.step3.repo.UserTrackerRepository;

@SpringBootTest
class Step3ApplicationTests {
    @Autowired
    UserTrackerRepository userTrackerRepository;
    @Test
    void contextLoads() {
        userTrackerRepository.getAll();
    }
}
