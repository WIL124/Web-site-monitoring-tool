package thumbtack.school.step3.daoimpl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thumbtack.school.step3.User;
import thumbtack.school.step3.dao.Dao;
import thumbtack.school.step3.repo.UserTrackerRepository;

import java.util.List;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class DaoImpl implements Dao {
    @Autowired
    private UserTrackerRepository repository;

    @Override
    public void createTable(String tableName) {

    }

    @Override
    public void put(User user) {
        repository.put(user);
    }

    @Override
    public void put(User user, long ts) {

    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }
}
