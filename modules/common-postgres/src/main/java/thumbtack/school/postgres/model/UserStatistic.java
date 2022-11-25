package thumbtack.school.postgres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
@Table(name = "users")
public class UserStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    @Column(name = "users", nullable = false)
    private long users;
    @Column(name = "requests", nullable = false)
    private long requests;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public UserStatistic(long users, long requests) {
        this.users = users;
        this.requests = requests;
    }
}
