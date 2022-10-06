package thumbtack.school.tracking.mapper;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import thumbtack.school.tracking.model.User;

import java.util.Arrays;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

@Component
public class UserMapper {
    private static final String COLUMN_FAMILY = "data";
    private static final String IP_ADDRESS_COLUMN_NAME = "IP address";
    public User fromResult(Result result) {
        MultiValueMap<String, String> map = new HttpHeaders();
        for (Cell cell: result.listCells()) {
            String qualifier = qualifierFromCell(cell);
            String value = valueFromCell(cell);
            map.add(qualifier, value);
        }
        return new User(convertFromBytes(result.getRow()), convertFromBytes(result.getValue(toBytes(COLUMN_FAMILY), toBytes(IP_ADDRESS_COLUMN_NAME))), new HttpHeaders(map));
    }
    private static String valueFromCell(Cell cell){
        return convertFromBytes(Arrays.copyOfRange(cell.getValueArray(), cell.getValueOffset(), cell.getValueOffset() + cell.getValueLength()));
    }
    private static String qualifierFromCell(Cell cell){
        return convertFromBytes(Arrays.copyOfRange(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierOffset() + cell.getQualifierLength()));
    }
    private static String convertFromBytes(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Character.toChars(b));
        }
        return sb.toString();
    }
}
