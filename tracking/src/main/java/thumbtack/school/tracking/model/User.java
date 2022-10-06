package thumbtack.school.tracking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private String id;
    private String ipAddress;
    private HttpHeaders headers;
}
