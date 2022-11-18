package thumbtack.school.postgres.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "days_of_week")
public class DayOfWeekStatistic extends AbstractStatistic {
    public DayOfWeekStatistic(String name, long count) {
        super(name, count);
    }
}
