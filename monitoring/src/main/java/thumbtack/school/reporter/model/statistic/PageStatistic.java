package thumbtack.school.reporter.model.statistic;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PageStatistic {
    private long id;
    private String url;
    private long count;
    private LocalDateTime createdAt;
    public PageStatistic(String url, long count) {
        this.url = url;
        this.count = count;
    }
}
