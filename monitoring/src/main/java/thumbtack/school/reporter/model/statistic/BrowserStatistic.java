package thumbtack.school.reporter.model.statistic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "browsers")
public class BrowserStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "visitors_count")
    private long count;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public BrowserStatistic(String name, long count) {
        this.name = name;
        this.count = count;
    }
}
