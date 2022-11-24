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
@Table(name = "browsers")
public class BrowserStatistic extends AbstractStatistic {
    public BrowserStatistic(String name, long count) {
        super(name, count);
    }
}
