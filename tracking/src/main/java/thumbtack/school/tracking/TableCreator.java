package thumbtack.school.tracking;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class TableCreator implements CommandLineRunner {
    @Autowired
    private CompletableFuture<AsyncConnection> asyncConnectionCompletableFuture;

    private static final String TABLE_NAME = "userTracker";
    private static final String COLUMN_FAMILY_NAME = "data";

    @Override
    public void run(String... args) throws Exception {
        log.info("Start TableCreator");
        AsyncAdmin asyncAdmin = asyncConnectionCompletableFuture.get().getAdmin();
        if (!asyncAdmin.tableExists(TableName.valueOf(TABLE_NAME)).get()) {
            System.out.println("4");
            ColumnFamilyDescriptor cfDescriptor =
                    new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(toBytes(COLUMN_FAMILY_NAME))
                            .setMaxVersions(10000).setTimeToLive(2 * 30 * 24 * 60 * 60 * 100);
            asyncAdmin.createTable(new TableDescriptorBuilder.ModifyableTableDescriptor(TableName.valueOf(TABLE_NAME))
                    .setColumnFamily(cfDescriptor));
        }
    }
}
