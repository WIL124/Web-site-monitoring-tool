package thumbtack.school.reporter.dao;

import org.springframework.stereotype.Component;
import thumbtack.school.tracking.model.User;

import java.util.List;

@Component
public interface HbaseDao {
    List<User> getAll();
}
