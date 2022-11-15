package thumbtack.school.api.mapper;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import thumbtack.school.api.dto.StatisticDto;
import thumbtack.school.postgres.model.*;

@Component
@AllArgsConstructor
public class StatisticMapper {
    public StatisticDto toDto(BrowserStatistic statistic) {
        return new StatisticDto(statistic.getName(), statistic.getCount());
    }

    public StatisticDto toDto(CountryStatistic browserStatistic) {
        return new StatisticDto(browserStatistic.getName(), browserStatistic.getCount());
    }

    public StatisticDto toDto(DayOfWeekStatistic statistic) {
        return new StatisticDto(statistic.getDayOfWeek(), statistic.getCount());
    }

    public StatisticDto toDto(PageStatistic statistic) {
        return new StatisticDto(statistic.getUrl(), statistic.getCount());
    }

    public StatisticDto toDto(TimeOfDayStatistic statistic) {
        return new StatisticDto(String.valueOf(statistic.getHour()), statistic.getCount());
    }
}
