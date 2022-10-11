package thumbtack.school.reporter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thumbtack.school.reporter.dao.IpLocationDao;
import thumbtack.school.reporter.model.IpLocationModel;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class GeolocationServiceImpl implements GeolocationService {
    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    @Autowired
    private IpLocationDao ipLocationDao;

    @Override
    public String getCountryFromIP(String ipAddress) {
        IpLocationModel ipLocationModel;
        try {
            ipLocationModel = objectMapper.readValue(ipLocationDao.getResponse(ipAddress).getBody(), IpLocationModel.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (ipLocationModel == null) return "unknown";
        return ipLocationModel.getCountry_name() == null ? "unknown" : ipLocationModel.getCountry_name();
    }
}
