package thumbtack.school.reporter.model.statistic;

import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
public class BrowserStatistic extends Statistic{
    public BrowserStatistic(Map<String, Integer> nameToCountMap) {
        super(nameToCountMap);
    }
}
