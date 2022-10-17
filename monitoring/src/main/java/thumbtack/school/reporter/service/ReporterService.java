package thumbtack.school.reporter.service;

import thumbtack.school.reporter.model.SessionReport;

import java.util.List;

public interface ReporterService {
    /**
     * Create and download report from hbase to postgres for dateTime
     */
    List<SessionReport> getReport(long tsFrom, long tsTo);
}
