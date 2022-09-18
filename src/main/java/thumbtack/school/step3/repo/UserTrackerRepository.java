package thumbtack.school.step3.repo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.AsyncConnection;
import org.apache.hadoop.hbase.client.Put;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import thumbtack.school.step3.User;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

@Repository
@NoArgsConstructor
@AllArgsConstructor
public class UserTrackerRepository {
    @Autowired
    private CompletableFuture<AsyncConnection> asyncConnection;
    private static final String TABLE_NAME = "userTracker";
    private static final String COLUMN_FAMILY = "data";
    @Autowired
    private Executor executor;

    public void put(User user) {
        CompletableFuture<Put> putCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Put put = new Put(toBytes(user.getId()));
            user.getHeaders()
                    .forEach((name, values) -> values
                            .forEach(value -> put.addColumn(toBytes(COLUMN_FAMILY), toBytes(name), toBytes(value))));
            return put;
        });
        executor.execute(() -> putCompletableFuture.thenCombine(asyncConnection, (putWithData, connection) ->
                connection.getTable(TableName.valueOf(TABLE_NAME)).put(putWithData)));
    }
}
