package thumbtack.school.postgres.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "days_of_week")
public class DayOfWeekStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String dayOfWeek;
    @Column(name = "count")
    private long count;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public DayOfWeekStatistic(String dayOfWeek, long count) {
        this.dayOfWeek = dayOfWeek;
        this.count = count;
    }
}
