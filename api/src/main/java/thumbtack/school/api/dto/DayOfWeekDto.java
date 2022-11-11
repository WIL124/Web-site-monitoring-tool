package thumbtack.school.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DayOfWeekDto {
    private String name;
    private long count;
}
