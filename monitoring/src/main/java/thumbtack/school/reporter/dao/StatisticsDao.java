package thumbtack.school.reporter.dao;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsDao extends JpaRepository<FileSystem.Statistics, Integer> {
}
