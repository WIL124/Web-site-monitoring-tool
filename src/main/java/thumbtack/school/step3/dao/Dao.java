package thumbtack.school.step3.dao;

import thumbtack.school.step3.User;

import java.util.List;

public interface Dao {
    void createTable(String tableName);

    void put(User user);

    void put(User user, long ts);

    List<User> getAll();
}
