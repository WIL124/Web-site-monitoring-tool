package thumbtack.school.reporter.service;

public interface ReporterService {
    /**
     * Create and download report from hbase to postgres for dateTime
     */
    void getReport(long tsFrom, long tsTo);
}
