package thumbtack.school.common.mapper;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import thumbtack.school.common.model.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserMapper {

    public User fromResult(Result result) {
        Map<Long, HttpHeaders> timestampHeadersMap = new HashMap<>();
        for (Cell cell : result.listCells()) {
            long ts = cell.getTimestamp();
            String qualifier = qualifierFromCell(cell);
            String value = valueFromCell(cell);
            timestampHeadersMap.putIfAbsent(ts, new HttpHeaders());
            timestampHeadersMap.get(ts).add(qualifier, value);
        }
        return new User(convertFromBytes(result.getRow()), timestampHeadersMap);
    }

    private static String valueFromCell(Cell cell) {
        return convertFromBytes(Arrays.copyOfRange(cell.getValueArray(), cell.getValueOffset(), cell.getValueOffset() + cell.getValueLength()));
    }

    private static String qualifierFromCell(Cell cell) {
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
