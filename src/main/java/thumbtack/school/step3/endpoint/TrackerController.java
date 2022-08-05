package thumbtack.school.step3.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import thumbtack.school.step3.service.TrackerService;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class TrackerController {
    TrackerService trackerService;
    private static final byte[] pixel = new byte[]{0x1};

    @GetMapping(value = "/img")
    public ResponseEntity<byte[]> getPixel(@CookieValue(value = "userId", required = false) String userId,
                                           @RequestHeader HttpHeaders headers,
                                           HttpServletResponse response) {
        if (userId == null) {
            response.setStatus(303);
            response.setHeader("Location", "/img");
            response.setHeader("Set-Cookie", "userId=" + UUID.randomUUID());
            return ResponseEntity.status(303).build();
        }
        trackerService.track(userId, headers);
        headers.forEach((s, strings) -> System.out.println(s + "  :  " + strings));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(pixel);
    }
}
