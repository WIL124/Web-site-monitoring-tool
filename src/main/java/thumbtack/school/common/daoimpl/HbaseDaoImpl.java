package thumbtack.school.common.daoimpl;

import lombok.AllArgsConstructor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.springframework.stereotype.Repository;
import thumbtack.school.common.dao.HbaseDao;
import thumbtack.school.common.mapper.UserMapper;
import thumbtack.school.common.model.User;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

@Repository
@AllArgsConstructor
public class HbaseDaoImpl implements HbaseDao {
    private UserMapper userMapper;
    private CompletableFuture<AsyncConnection> asyncConnection;
    private static final String COLUMN_FAMILY_NAME = "data";

    @Override
    public void createTable(String tableName) throws ExecutionException, InterruptedException {
        TableName table = TableName.valueOf(tableName);
        ColumnFamilyDescriptor cfDescriptor =
                new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(toBytes(COLUMN_FAMILY_NAME))
                        .setMaxVersions(10000).setTimeToLive(2 * 30 * 24 * 60 * 60 * 100);
        TableDescriptor tableDescriptor = new TableDescriptorBuilder.ModifyableTableDescriptor(table)
                .setColumnFamily(cfDescriptor);
        AsyncAdmin asyncAdmin = asyncConnection.get().getAdmin();
        if (!asyncAdmin.tableExists(TableName.valueOf(tableName)).get()) {
            asyncAdmin.createTable(tableDescriptor);
        }
    }

    @Override
    public void put(String tableName, User user) {
        CompletableFuture.supplyAsync(() -> {
            Put put = new Put(toBytes(user.getId()));
            user.getTimestampHeadersMap().values().forEach(
                    header -> header.forEach((name, values) ->
                            values.forEach(value -> put.addColumn(toBytes(COLUMN_FAMILY_NAME), toBytes(name), toBytes(value)))));
            return put;
        }).thenCombine(asyncConnection, (putWithData, connection) ->
                connection.getTable(TableName.valueOf(tableName)).put(putWithData));
    }

    @Override
    public List<User> getAllUsersWithTimeRange(String tableName, long minRange, long maxRange) {
        try {
            return asyncConnection.thenApply(connection -> connection.getTable(TableName.valueOf(tableName)))
                    .thenCompose(table -> table.scanAll(fullScanWithTimeRange(minRange, maxRange)))
                    .thenApply(resultList -> resultList.stream().map(result -> userMapper.fromResult(result)).collect(Collectors.toList())).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private Scan fullScan() {
        return new Scan().readAllVersions();
    }

    private Scan fullScanWithTimeRange(long min, long max) {
        try {
            return fullScan().setTimeRange(min, max);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
