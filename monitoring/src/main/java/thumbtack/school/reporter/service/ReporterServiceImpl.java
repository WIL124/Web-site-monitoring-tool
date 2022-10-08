package thumbtack.school.reporter.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thumbtack.school.reporter.model.Report;
import thumbtack.school.tracking.dao.HbaseDao;
import thumbtack.school.tracking.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReporterServiceImpl implements ReporterService {
    private static final String TABLE_NAME = "userTracker";
    @Autowired
    private HbaseDao hbaseDao;


    @Override
    public void getReport(LocalDateTime dateTime) {
        List<User> users = hbaseDao.getAllUsers(TABLE_NAME);
        long visits = users.stream().mapToLong(user -> user.getTimestampHeadersMap().size()).sum();
        Report report = new Report(dateTime, users.size(), visits);
    }
}
