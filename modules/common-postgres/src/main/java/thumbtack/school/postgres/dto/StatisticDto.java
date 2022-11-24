package thumbtack.school.postgres.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDto {
    private String name;
    private long count;
    private LocalDateTime createdAt;
    public StatisticDto(String name, long count) {
        this.name = name;
        this.count = count;
    }
}
