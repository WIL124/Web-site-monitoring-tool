package thumbtack.school.reporter.service;

import java.time.LocalDateTime;

public interface ReporterService {
    /**
     * Create and download report from hbase to postgres for current date/time
     */
    void getReport();

    /**
     * Create and download report from hbase to postgres for dateTime
     */
    void getReport(LocalDateTime dateTime);
}
