package thumbtack.school.reporter.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.tracking.model.User;
import thumbtack.school.reporter.dao.HbaseDao;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReporterServiceImpl implements ReporterService {
    HbaseDao hbaseDao;

    @Override
    public void getReport() {
        getReport(LocalDateTime.now());
    }

    @Override
    public void getReport(LocalDateTime dateTime) {
        List<User> users = hbaseDao.getAll();
    }
}
