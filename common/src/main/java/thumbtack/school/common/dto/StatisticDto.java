package thumbtack.school.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticDto {
    private String name;
    private long count;
}
