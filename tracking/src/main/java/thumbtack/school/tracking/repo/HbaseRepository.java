package thumbtack.school.tracking.repo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Repository
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class HbaseRepository {
    @Autowired
    private CompletableFuture<AsyncConnection> asyncConnection;

    public void put(TableName tableName, CompletableFuture<Put> put) {
        put.thenCombine(asyncConnection, (putWithData, connection) ->
                connection.getTable(tableName).put(putWithData));
    }

    public CompletableFuture<List<Result>> getAll(TableName tableName) {
        CompletableFuture<AsyncTable<AdvancedScanResultConsumer>> tableFuture =
                asyncConnection.thenApply(connection -> connection.getTable(tableName));
        return tableFuture.thenCompose(table -> table.scanAll(new Scan().readAllVersions()));
    }

    public void createTable(TableDescriptor tableDescriptor, TableName tableName) throws ExecutionException, InterruptedException {
        AsyncAdmin asyncAdmin = asyncConnection.get().getAdmin();
        if (!asyncAdmin.tableExists(tableName).get()) {
            asyncAdmin.createTable(tableDescriptor);
        }
    }
}
