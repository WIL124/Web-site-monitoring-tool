package thumbtack.school.reporter.model.statistic;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CountryStatistic {
    private long id;
    private String name;
    private long count;
    private LocalDateTime createdAt;
    public CountryStatistic(String name, long count) {
        this.name = name;
        this.count = count;
    }
}
