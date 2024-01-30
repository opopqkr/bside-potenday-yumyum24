package best.bside.potenday.yumyum24.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/reply")
    public ResponseEntity<String> addReply() {
        return ResponseEntity.ok("Reply completed");
    }

    @PostMapping("/like")
    public ResponseEntity<String> like() {
        return ResponseEntity.ok("Like completed");
    }

}
