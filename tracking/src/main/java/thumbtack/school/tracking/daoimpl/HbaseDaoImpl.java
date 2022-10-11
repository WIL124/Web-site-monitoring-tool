package thumbtack.school.tracking.daoimpl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thumbtack.school.tracking.dao.HbaseDao;
import thumbtack.school.tracking.mapper.UserMapper;
import thumbtack.school.tracking.model.User;
import thumbtack.school.tracking.repo.HbaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class HbaseDaoImpl implements HbaseDao {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HbaseRepository repository;
    private static final String COLUMN_FAMILY_NAME = "data";

    @Override
    public void createTable(String tableName) throws ExecutionException, InterruptedException {
        TableName table = TableName.valueOf(tableName);
        ColumnFamilyDescriptor cfDescriptor =
                new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(toBytes(COLUMN_FAMILY_NAME))
                        .setMaxVersions(10000).setTimeToLive(2 * 30 * 24 * 60 * 60 * 100);
        TableDescriptor tableDescriptor = new TableDescriptorBuilder.ModifyableTableDescriptor(table)
                .setColumnFamily(cfDescriptor);
        repository.createTable(tableDescriptor, table);
    }

    @Override
    public void put(String tableName, User user) {
        CompletableFuture<Put> putCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Put put = new Put(toBytes(user.getId()));
            user.getTimestampHeadersMap().values().forEach(
                    header -> header.forEach((name, values) ->
                            values.forEach(value -> put.addColumn(toBytes(COLUMN_FAMILY_NAME), toBytes(name), toBytes(value)))));
            return put;
        });
        repository.put(TableName.valueOf(tableName), putCompletableFuture);
    }


    @Override
    public List<User> getAllUsers(String tableName) {
        List<User> users = new ArrayList<>();
        CompletableFuture<List<Result>> cfResults = repository.getAll(TableName.valueOf(tableName));
        try {
            cfResults.get().parallelStream().forEach(result ->
                    users.add(userMapper.fromResult(result)));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public List<User> getAllUsersWithTimeRange(String tableName, long minRange, long maxRange) {
        List<User> users = new ArrayList<>();
        CompletableFuture<List<Result>> cfResults = repository.getAllWithTimeRange(TableName.valueOf(tableName), minRange, maxRange);
        try {
            cfResults.get().parallelStream().forEach(result ->
                    users.add(userMapper.fromResult(result)));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
