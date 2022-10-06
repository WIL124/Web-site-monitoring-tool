package thumbtack.school.tracking.daoimpl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thumbtack.school.tracking.dao.Dao;
import thumbtack.school.tracking.repo.HbaseRepository;
import thumbtack.school.tracking.model.User;

import java.util.List;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class DaoImpl implements Dao {
    @Autowired
    private HbaseRepository repository;

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
