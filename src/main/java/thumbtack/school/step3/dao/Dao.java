package thumbtack.school.step3.dao;

import thumbtack.school.step3.User;

public interface Dao {
    void createTable(String tableName);

    void put(User user);

    void put(User user, long ts);
}
