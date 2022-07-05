package thumbtack.school.step3.endpoint;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackerEndpoint {
    @GetMapping(value = "/tracker")
    public ResponseEntity<byte[]> getTracker() {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new byte[]{0x1});
    }
}
