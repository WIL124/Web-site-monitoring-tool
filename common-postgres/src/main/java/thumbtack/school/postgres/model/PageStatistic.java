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
@Table(name = "pages")
public class PageStatistic extends AbstractStatistic{
    public PageStatistic(String name, long count){
        super(name, count);
    }
}
