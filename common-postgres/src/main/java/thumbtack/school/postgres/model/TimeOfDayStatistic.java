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
@Table(name = "time_of_day")
public class TimeOfDayStatistic extends AbstractStatistic {
    public TimeOfDayStatistic(String name, long count){
        super(name, count);
    }
}
