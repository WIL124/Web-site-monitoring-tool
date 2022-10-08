package thumbtack.school.reporter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Report {
    LocalDateTime dateTime;
    long users;
    long visits;
}
