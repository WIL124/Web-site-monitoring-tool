package thumbtack.school.tracking.dao;

import thumbtack.school.tracking.model.User;

import java.util.List;

public interface Dao {
    void createTable(String tableName);

    void put(User user);

    void put(User user, long ts);

    List<User> getAll();
}
