package thumbtack.school.hbase.dao.impl;

import lombok.AllArgsConstructor;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.springframework.stereotype.Repository;
import thumbtack.school.hbase.dao.HbaseDao;
import thumbtack.school.hbase.mapper.UserMapper;
import thumbtack.school.hbase.model.User;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

@Repository
@AllArgsConstructor
public class HbaseDaoImpl implements HbaseDao {
    private final UserMapper userMapper;
    private final Configuration configuration;
    private static final String COLUMN_FAMILY_NAME = "data";

    @Override
    public void createTable(String tableName) throws ExecutionException, InterruptedException {
        TableName table = TableName.valueOf(tableName);
        ColumnFamilyDescriptor cfDescriptor =
                new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(toBytes(COLUMN_FAMILY_NAME))
                        .setMaxVersions(10000).setTimeToLive(2 * 30 * 24 * 60 * 60 * 100);
        TableDescriptor tableDescriptor = new TableDescriptorBuilder.ModifyableTableDescriptor(table)
                .setColumnFamily(cfDescriptor);
        AsyncAdmin asyncAdmin = createConnection().get().getAdmin();
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
        }).thenCombine(createConnection(), (putWithData, connection) ->
                connection.getTable(TableName.valueOf(tableName)).put(putWithData));
    }

    @Override
    public List<User> getAllUsersWithTimeRange(String tableName, long minRange, long maxRange) {
        try {
            return createConnection().thenApply(connection -> connection.getTable(TableName.valueOf(tableName)))
                    .thenCompose(table -> table.scanAll(fullScanWithTimeRange(minRange, maxRange)))
                    .thenApply(resultList -> resultList.stream().map(userMapper::fromResult).collect(Collectors.toList())).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private CompletableFuture<AsyncConnection> createConnection() {
        return ConnectionFactory.createAsyncConnection(configuration);
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
