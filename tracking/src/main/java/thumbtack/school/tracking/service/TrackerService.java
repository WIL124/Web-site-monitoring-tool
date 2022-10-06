package thumbtack.school.tracking.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import thumbtack.school.tracking.model.User;
import thumbtack.school.tracking.dao.Dao;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Async
public class TrackerService {
    @Autowired
    private Dao dao;

    @Async
    public void track(String userId, String ipAddress, HttpHeaders headers) {
        User user = new User(userId, ipAddress, headers);
        dao.put(user);
    }
}
