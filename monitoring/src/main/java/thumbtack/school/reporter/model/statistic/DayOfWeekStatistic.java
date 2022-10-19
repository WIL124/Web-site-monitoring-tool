package thumbtack.school.reporter.model.statistic;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Data
public class DayOfWeekStatistic {
    private long id;
    private DayOfWeek dayOfWeek;
    private long count;
    private LocalDateTime createdAt;

    public DayOfWeekStatistic(DayOfWeek dayOfWeek, long count) {
        this.dayOfWeek = dayOfWeek;
        this.count = count;
    }
}
