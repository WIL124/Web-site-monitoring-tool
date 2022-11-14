package thumbtack.school.tracking.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import thumbtack.school.hbase.dao.HbaseDao;
import thumbtack.school.hbase.model.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class TrackerService {
    private static final String TABLE_NAME = "userTracker";
    private static final String IP_ADDRESS_HEADER_NAME = "IP address";
    private HbaseDao hbaseDao;

    @Async
    public void track(String userId, String ipAddress, HttpHeaders headers) {
        headers.add(IP_ADDRESS_HEADER_NAME, ipAddress);
        User user = new User(userId, new HashMap<>(Collections.singletonMap(System.currentTimeMillis(), headers)));
        hbaseDao.put(TABLE_NAME, user);
    }

    public void createTable(String tableName) throws ExecutionException, InterruptedException {
        hbaseDao.createTable(tableName);
    }
}
