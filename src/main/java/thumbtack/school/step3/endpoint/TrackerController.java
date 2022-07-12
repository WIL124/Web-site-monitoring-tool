package thumbtack.school.step3.endpoint;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackerController {
    private static final byte[] pixel = new byte[]{0x1};

    @GetMapping(value = "/img")
    public ResponseEntity<byte[]> getPixel() {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(pixel);
    }
}
