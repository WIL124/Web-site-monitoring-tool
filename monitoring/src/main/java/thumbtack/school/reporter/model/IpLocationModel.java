package thumbtack.school.reporter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IpLocationModel {
    private String ip;
    private String ip_number;
    private String ip_version;
    private String country_name;
    private String country_code2;
    private String isp;
}
