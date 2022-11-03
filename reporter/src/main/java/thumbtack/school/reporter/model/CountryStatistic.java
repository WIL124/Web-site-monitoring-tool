package thumbtack.school.reporter.model;

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
@Table(name = "countries")
public class CountryStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "count", nullable = false)
    private long count;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    public CountryStatistic(String name, long count) {
        this.name = name;
        this.count = count;
    }
}
