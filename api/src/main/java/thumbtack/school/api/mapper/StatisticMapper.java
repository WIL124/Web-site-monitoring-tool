package thumbtack.school.api.mapper;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import thumbtack.school.api.dto.StatisticDto;
import thumbtack.school.postgres.model.AbstractStatistic;

@Component
@AllArgsConstructor
public class StatisticMapper {
    public StatisticDto toDto(AbstractStatistic statistic) {
        return new StatisticDto(statistic.getName(), statistic.getCount());
    }
}
