package best.bside.potenday.yumyum24.api;

import best.bside.potenday.yumyum24.payload.Response;
import best.bside.potenday.yumyum24.payload.requests.LoginRequest;
import best.bside.potenday.yumyum24.payload.responses.Profile;
import best.bside.potenday.yumyum24.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그인", description = "로그인 API.")
    @PostMapping("/login")
    public ResponseEntity<Response<String>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, userService.login(loginRequest)));
    }

    @GetMapping("/profile")
    public ResponseEntity<Response<Profile>> getProfile() {
        Profile profile = userService.getProfile();
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, profile));
    }
}
