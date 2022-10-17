package thumbtack.school.reporter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import thumbtack.school.tracking.model.User;

@Data
@AllArgsConstructor
@Builder
public class SessionReport {
    private long timestamp;
    private User user;
    private String language;
    private String region;
    private String os;
    private String platform;
}
