package thumbtack.school.step3.repo;

import lombok.NoArgsConstructor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.AsyncConnection;
import org.apache.hadoop.hbase.client.Put;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import thumbtack.school.step3.User;

import java.io.IOException;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

@Repository
@NoArgsConstructor
public class UserTrackerRepository {
    @Autowired
    private AsyncConnection asyncConnection;
    private static final String TABLE_NAME = "userTracker";
    private static final String COLUMN_FAMILY = "data";

    public void put(User user) throws IOException {
        Put put = new Put(toBytes(user.getId() + 1));
        user.getHeaders().forEach((name, values) ->
                values.forEach(value -> put.addColumn(toBytes(COLUMN_FAMILY), toBytes(name), toBytes(value))));
        asyncConnection.getTable(TableName.valueOf(TABLE_NAME)).put(put);
        asyncConnection.close();
    }
}
