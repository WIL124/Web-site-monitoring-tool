package thumbtack.school.hbase.dao;

import thumbtack.school.hbase.model.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface HbaseDao {
    void createTable(String tableName) throws ExecutionException, InterruptedException;

    void put(String tableName, User user);

    List<User> getAllUsersWithTimeRange(String tableName, long minRange, long maxRange);
}
