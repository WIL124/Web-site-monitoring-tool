package thumbtack.school.reporter.model.statistic;

import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
public class CountryStatistic extends Statistic{
    public CountryStatistic(Map<String, Integer> nameToCountMap) {
        super(nameToCountMap);
    }
}
