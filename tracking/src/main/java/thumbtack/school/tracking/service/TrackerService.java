package thumbtack.school.tracking.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import thumbtack.school.tracking.dao.HbaseDao;
import thumbtack.school.tracking.model.User;

import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Async
public class TrackerService {
    private static final String TABLE_NAME = "userTracker";
    @Autowired
    private HbaseDao hbaseDao;

    @Async
    public void track(String userId, String ipAddress, HttpHeaders headers) {
        User user = new User(userId, ipAddress, headers);
        hbaseDao.put(TABLE_NAME, user);
    }

    public void createTable(String tableName) throws ExecutionException, InterruptedException {
        hbaseDao.createTable(tableName);
    }
}
