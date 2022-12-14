package thumbtack.school.tracking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import thumbtack.school.hbase.dao.HbaseDao;
import thumbtack.school.hbase.model.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
//@AllArgsConstructor
public class TrackerService {
    public TrackerService(@Autowired HbaseDao hbaseDao) {
        this.hbaseDao = hbaseDao;
        this.random = new Random();
    }

    private static final String TABLE_NAME = "userTracker";
    private static final String IP_ADDRESS_HEADER_NAME = "IP address";
    private HbaseDao hbaseDao;
    private Random random;  //test

    @Async
    public void track(String userId, String ipAddress, HttpHeaders headers) {
        headers.add(IP_ADDRESS_HEADER_NAME, ipAddress);
        User user = new User(userId, new HashMap<>(Collections.singletonMap(System.currentTimeMillis(), headers)));
        hbaseDao.put(TABLE_NAME, user);
    }

    public void createTable(String tableName) throws ExecutionException, InterruptedException {
        hbaseDao.createTable(tableName);
    }

    //////////////test methods///////////////////////////
    public void insertTestData() {
        for (int i = 0; i < 5; i++) { //create 5 users
            HttpHeaders headers = new HttpHeaders();
            headers.add(IP_ADDRESS_HEADER_NAME,  //randomIp
                    randomIp());
            headers.add(HttpHeaders.USER_AGENT, RandomUserAgent.getRandomUserAgent());
            headers.add(HttpHeaders.REFERER, getRandomReferer());
            User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .timestampHeadersMap(Collections.singletonMap(getRandomTime(), headers))
                    .build();
            CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> hbaseDao.put(TABLE_NAME, user));
            try {
                cf.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String randomIp() {
        return new StringBuilder().append(random.nextInt(255)).append(".")
                .append(random.nextInt(255)).append(".")
                .append(random.nextInt(255)).append(".")
                .append(random.nextInt(255)).toString();
    }

    private String getRandomReferer() {
        switch (random.nextInt(4)) {
            case 1:
                return "http://localhost:8080/index.html";
            case 2:
                return "http://localhost:8080/funny-cats.html";
            case 3:
                return "http://localhost:8080/cute-cats.html";
            case 4:
                return "http://localhost:8080/angry-cats.html";
        }
        return null;
    }

    private long getRandomTime() {
        return System.currentTimeMillis() + random.nextInt();
    }
}
