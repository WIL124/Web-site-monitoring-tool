package thumbtack.school.reporter.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.tracking.dao.HbaseDao;
import thumbtack.school.tracking.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReporterServiceImpl implements ReporterService {
    private static final String TABLE_NAME = "userTracker";
    private HbaseDao hbaseDao;


    @Override
    public void getReport(LocalDateTime dateTime) {
        List<User> users = hbaseDao.getAll(TABLE_NAME);
    }
}
