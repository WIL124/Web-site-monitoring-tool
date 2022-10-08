package thumbtack.school.tracking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private String id;
    private Map<Long, HttpHeaders> timestampHeadersMap; //map of user's sessions
}
