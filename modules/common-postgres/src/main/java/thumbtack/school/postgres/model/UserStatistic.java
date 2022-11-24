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
@Table(name = "users")
public class UserStatistic extends AbstractStatistic {
    public UserStatistic(String name, long count) {
        super(name, count);
    }
}
