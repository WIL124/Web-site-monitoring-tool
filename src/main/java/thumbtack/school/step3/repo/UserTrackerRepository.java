package thumbtack.school.step3.repo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import thumbtack.school.step3.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

@Repository
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserTrackerRepository {
    @Autowired
    private CompletableFuture<AsyncConnection> asyncConnection;
    private static final String TABLE_NAME = "userTracker";
    private static final String COLUMN_FAMILY = "data";

    public void put(User user) {
        log.debug("Put " + user);
        CompletableFuture.supplyAsync(() -> {
            Put put = new Put(toBytes(user.getId()));
            user.getHeaders()
                    .forEach((name, values) -> values
                            .forEach(value -> put.addColumn(toBytes(COLUMN_FAMILY), toBytes(name), toBytes(value))));
            put.addColumn(toBytes(COLUMN_FAMILY), toBytes("IP address"), toBytes(user.getIpAddress()));
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
            {
                MultiValueMap<String, String> map = new HttpHeaders();
                for (Cell cell : result.listCells()) {
                    String qualifier = convertFromBytes(Arrays.copyOfRange(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierOffset() + cell.getQualifierLength()));
                    String value = convertFromBytes(Arrays.copyOfRange(cell.getValueArray(), cell.getValueOffset(), cell.getValueOffset() + cell.getValueLength()));
                    map.add(qualifier, value);
                }
                users.add(new User(convertFromBytes(result.getRow()), convertFromBytes(result.getValue(toBytes(COLUMN_FAMILY), toBytes("IP address"))), new HttpHeaders(map)));
            });
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    private static String convertFromBytes(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Character.toChars(b));
        }
        return sb.toString();
    }
}
