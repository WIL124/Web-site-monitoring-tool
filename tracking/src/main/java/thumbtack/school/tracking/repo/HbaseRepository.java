package thumbtack.school.tracking.repo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import thumbtack.school.tracking.mapper.UserMapper;
import thumbtack.school.tracking.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

@Repository
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class HbaseRepository {
    @Autowired
    private CompletableFuture<AsyncConnection> asyncConnection;
    private static final String TABLE_NAME = "userTracker";
    private static final String COLUMN_FAMILY = "data";
    private static final String IP_ADDRESS_COLUMN_NAME = "IP ADDRESS";
    @Autowired
    private UserMapper userMapper;

    public void put(User user) {
        log.debug("Put " + user);
        CompletableFuture.supplyAsync(() -> {
            Put put = new Put(toBytes(user.getId()));
            user.getHeaders()
                    .forEach((name, values) -> values
                            .forEach(value -> put.addColumn(toBytes(COLUMN_FAMILY), toBytes(name), toBytes(value))));
            put.addColumn(toBytes(COLUMN_FAMILY), toBytes(IP_ADDRESS_COLUMN_NAME), toBytes(user.getIpAddress()));
            return put;
        }).thenCombine(asyncConnection, (putWithData, connection) ->
                connection.getTable(TableName.valueOf(TABLE_NAME)).put(putWithData));
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        CompletableFuture<AsyncTable<AdvancedScanResultConsumer>> tableFuture =
                asyncConnection.thenApply(connection -> connection.getTable(TableName.valueOf(TABLE_NAME)));
        CompletableFuture<List<Result>> cfResults = tableFuture.thenCompose(table -> table.scanAll(new Scan()));
        try {
            cfResults.get().parallelStream().forEach(result ->
                    users.add(userMapper.fromResult(result)));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
