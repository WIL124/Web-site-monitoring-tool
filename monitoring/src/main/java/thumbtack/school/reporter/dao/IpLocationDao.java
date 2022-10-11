package thumbtack.school.reporter.dao;

import org.springframework.http.ResponseEntity;
import thumbtack.school.reporter.model.IpLocationModel;

public interface IpLocationDao {
    ResponseEntity<String> getResponse(String ipAddress);
}
