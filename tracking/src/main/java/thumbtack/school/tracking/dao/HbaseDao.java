package thumbtack.school.tracking.dao;

import thumbtack.school.tracking.model.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface HbaseDao {
    void createTable(String tableName) throws ExecutionException, InterruptedException;

    void put(String tableName, User user);

    List<User> getAll(String tableName);
}
