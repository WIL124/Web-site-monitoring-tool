package thumbtack.school.reporter.model;

import lombok.Data;
import thumbtack.school.reporter.model.statistic.Statistic;

@Data
public class Report {
    private Statistic browserStatistic;
}
