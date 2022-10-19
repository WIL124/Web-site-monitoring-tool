package thumbtack.school.reporter.model.statistic;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeOfDayStatistic {
    private long id;
    private int hour;
    private long count;
    private LocalDateTime createdAt;
    public TimeOfDayStatistic(int hour, long count) {
        this.hour = hour;
        this.count = count;
    }
}
