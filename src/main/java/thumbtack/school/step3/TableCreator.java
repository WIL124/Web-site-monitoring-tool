package thumbtack.school.step3;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

@Component
@AllArgsConstructor
@Slf4j
public class TableCreator implements CommandLineRunner {
    @Autowired
    private AsyncConnection asyncConnection;

    private static final TableName TABLE_NAME = TableName.valueOf("userTracker");

    @Override
    public void run(String... args) throws Exception {
        log.info("Start TableCreator");
        if (!asyncConnection.getAdmin().isTableAvailable(TABLE_NAME).get()) {
            ColumnFamilyDescriptor cfDescriptor = new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(toBytes("data"))
                    .setMaxVersions(10000)
                    .setTimeToLive(2 * 30 * 24 * 60 * 60 * 100);
            TableDescriptor tableDescriptor = new TableDescriptorBuilder.ModifyableTableDescriptor(TABLE_NAME)
                    .setColumnFamily(cfDescriptor);
            asyncConnection.getAdmin().createTable(tableDescriptor);
            log.info("Table " + TABLE_NAME.getNameAsString() + " created");
        } else {
            log.info("Table " + TABLE_NAME.getNameAsString() + " already exist");
        }
    }
}
