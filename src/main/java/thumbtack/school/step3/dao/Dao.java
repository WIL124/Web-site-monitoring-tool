package thumbtack.school.step3.dao;

import thumbtack.school.step3.User;

import java.io.IOException;

public interface Dao {
    void createTable(String tableName);
    void put(User user) throws IOException;
    void put(User user, long ts);
}
