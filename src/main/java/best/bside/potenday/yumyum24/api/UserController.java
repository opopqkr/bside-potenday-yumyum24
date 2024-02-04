package best.bside.potenday.yumyum24.api;

import best.bside.potenday.yumyum24.payload.Response;
import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.requests.LoginRequest;
import best.bside.potenday.yumyum24.payload.responses.ComboItemInfo;
import best.bside.potenday.yumyum24.payload.responses.Profile;
import best.bside.potenday.yumyum24.service.BookmarkService;
import best.bside.potenday.yumyum24.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final BookmarkService bookmarkService;

    @Operation(summary = "로그인", description = "로그인 API.")
    @PostMapping("/login")
    public ResponseEntity<Response<String>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, userService.login(loginRequest)));
    }

    @Operation(summary = "프로필 조회", description = "프로필 조회 API.")
    @GetMapping("/profile")
    public ResponseEntity<Response<Profile>> getProfile() {
        String email = userService.getEmail();
        final Profile profile = userService.getProfile(email);

        return ResponseEntity.ok(new Response<>(HttpStatus.OK, profile));
    }

    @Operation(summary = "사용자 찜 목록 정보 조회 API", description = "사용자 찜 목록 정보 조회 API")
    @GetMapping("/bookmark")
    public ResponseEntity<Response<Page<ComboItemInfo>>> getUserBookmarkComboItemPageInfo(@RequestParam("category") @Nullable String category,
                                                                                          @RequestParam("page") int page,
                                                                                          @RequestParam("size") int size) {

        String email = userService.getEmail();

        final Page<ComboItemInfo> userBookmarkInfoPage
                = bookmarkService.getUserBookmarkComboItemPageInfo(email, category, new PageInfo(page, size));
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, userBookmarkInfoPage));
    }

    @Operation(summary = "찜 여부 조회 API", description = "찜 여부 조회 API")
    @GetMapping("/bookmark/{comboItemId}")
    public ResponseEntity<Response<Boolean>> isComboItemBookmark(@NotNull @PathVariable("comboItemId") Long comboItemId) {
        String email = userService.getEmail();
        final boolean isBookMark = bookmarkService.isComboItemBookmark(email, comboItemId);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, isBookMark));
    }

    @Operation(summary = "사용자 찜 등록 API", description = "사용자 찜 등록 API.")
    @PostMapping("/bookmark/{comboItemId}")
    public ResponseEntity<Response<Void>> addComboItemBookmark(@NotNull @PathVariable("comboItemId") Long comboItemId) {
        String email = userService.getEmail();

        bookmarkService.saveBookmark(email, comboItemId);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK));
    }

    @Operation(summary = "사용자 찜 삭제 API", description = "사용자 찜 삭제 API.")
    @DeleteMapping("/bookmark/{comboItemId}")
    public ResponseEntity<Response<Void>> deleteComboItemBookmark(@NotNull @PathVariable("comboItemId") Long comboItemId) {
        String email = userService.getEmail();

        bookmarkService.deleteBookmark(email, comboItemId);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK));
    }
}
