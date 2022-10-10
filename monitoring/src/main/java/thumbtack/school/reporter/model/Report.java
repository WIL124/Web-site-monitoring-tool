package thumbtack.school.reporter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Report {
    LocalDateTime dateTime;
    long users = 0;
    long visits = 0;
    Map<String, Long> languageMap = new HashMap<>();
    Map<String, Long> osMap = new HashMap<>();
    Map<String, Long> platformMap = new HashMap<>();
    Map<String, Long> browserMap = new HashMap<>();
    Map<String, Long> regionMap = new HashMap<>();
}
