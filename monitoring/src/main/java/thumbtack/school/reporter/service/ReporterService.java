package thumbtack.school.reporter.service;

import thumbtack.school.reporter.model.Report;

import java.time.LocalDateTime;

public interface ReporterService {
    /**
     * Create and download report from hbase to postgres for dateTime
     */
    Report getReport(LocalDateTime dateTime);
}
