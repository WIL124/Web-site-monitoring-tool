package thumbtack.school.common.model;

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
@Table(name = "pages")
public class PageStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "url")
    private String url;
    @Column(name = "count")
    private long count;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    public PageStatistic(String url, long count) {
        this.url = url;
        this.count = count;
    }
}
