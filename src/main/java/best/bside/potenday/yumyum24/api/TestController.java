package best.bside.potenday.yumyum24.api;

import best.bside.potenday.yumyum24.payload.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/reply")
    public ResponseEntity<Response<String>> addReply() {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, "Reply completed"));
    }

    @PostMapping("/like")
    public ResponseEntity<Response<String>> like() {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, "Like completed"));
    }

}
