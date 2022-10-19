package thumbtack.school.reporter.model.statistic;

import lombok.Data;

import java.util.Map;

@Data
public abstract class Statistic {
    private int id;
    private Map<String, Integer> nameToCountMap; // name --> count

    public Statistic(Map<String, Integer> nameToCountMap) {
        this.nameToCountMap = nameToCountMap;
    }
}
