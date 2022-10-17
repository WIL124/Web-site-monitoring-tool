package thumbtack.school.reporter.dao;

import org.springframework.http.ResponseEntity;

public interface IpLocationDao {
    ResponseEntity<String> getResponseWithJson(String ipAddress);
}
