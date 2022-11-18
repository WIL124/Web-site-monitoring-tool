package thumbtack.school.postgres.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
abstract public class AbstractStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "count")
    private long count;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public AbstractStatistic(String name, long count) {
        this.name = name;
        this.count = count;
    }
}
