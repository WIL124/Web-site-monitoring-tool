package thumbtack.school.reporter.daoImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import thumbtack.school.reporter.dao.IpLocationDao;

@Repository
@AllArgsConstructor()
public class IpLocationDaoImpl implements IpLocationDao {
    private RestTemplate restTemplate = new RestTemplate();
    private static final String URL = "https://api.iplocation.net/?ip=";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ResponseEntity<String> getResponse(String ipAddress) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.5112.124 YaBrowser/22.9.2.1495 Yowser/2.5 Safari/537.36");
//        headers.add("Cookie", "ezosuibasgeneris-1=d40ef242-4132-432c-4383-dd5a0348709d; __utmzz=utmcsr=yandex.ru|utmcmd=referral|utmccn=(not set); _ga=GA1.2.640105412.1665417238; _gid=GA1.2.841992287.1665417238; __qca=P0-1680014786-1665417238006; active_template::309035=pub_site.1665417346; ezepvv=208; ezovuuidtime_309035=1665417347; PHPSESSID=qupg1ceou4n92lm5h5usnr921c");
        return restTemplate.exchange(URL + ipAddress,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class);
    }
}
