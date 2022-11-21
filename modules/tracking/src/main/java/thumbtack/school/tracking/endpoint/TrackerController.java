package thumbtack.school.tracking.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import thumbtack.school.tracking.service.TrackerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class TrackerController {
    private TrackerService trackerService;
    private static final byte[] pixel = new byte[]{0x1};

    @GetMapping(value = "/img")
    public ResponseEntity<byte[]> getPixel(@CookieValue(value = "userId", required = false) String userId,
                                           @RequestHeader HttpHeaders headers,
                                           HttpServletResponse response, HttpServletRequest request) {
        if (userId == null) {
            return redirect(response);
        }
        trackerService.track(userId, request.getRemoteAddr(), headers);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(pixel);
    }

    private ResponseEntity<byte[]> redirect(HttpServletResponse response) {
        response.setStatus(303);
        response.setHeader("Location", "/img");
        response.setHeader("Set-Cookie", "userId=" + UUID.randomUUID());
        return ResponseEntity.status(303).build();
    }
}
