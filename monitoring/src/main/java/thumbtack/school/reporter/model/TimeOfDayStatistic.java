package thumbtack.school.reporter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "time_of_day")
public class TimeOfDayStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "hour")
    private int hour;
    @Column(name = "count")
    private long count;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    public TimeOfDayStatistic(int hour, long count) {
        this.hour = hour;
        this.count = count;
    }
}
