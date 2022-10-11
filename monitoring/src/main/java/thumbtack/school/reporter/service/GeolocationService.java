package thumbtack.school.reporter.service;

import org.springframework.stereotype.Service;

@Service
public interface GeolocationService {
    String getCountryFromIP(String ipAddress);
}
