package best.bside.potenday.yumyum24.api;


import best.bside.potenday.yumyum24.payload.Response;
import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.responses.UserBookmarkInfo;
import best.bside.potenday.yumyum24.service.UserBookmarkService;
import best.bside.potenday.yumyum24.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Arrays;

@RestController
@RequestMapping(value = "/user/bookmark")
@RequiredArgsConstructor
public class UserBookmarkController {

    private final UserService userService;
    private final UserBookmarkService userBookmarkService;

    @Operation(summary = "사용자 찜 목록 정보 조회 API", description = "사용자 찜 목록 정보 조회 API")
    @GetMapping
    public ResponseEntity<Response<Page<UserBookmarkInfo>>> getUserBookmarkInfo(@RequestParam("category") @Nullable String category,
                                                                                @RequestParam("page") int page,
                                                                                @RequestParam("size") int size) {

        String name = userService.getUserName();

        if (!Arrays.asList("FOOD", "DRINK", "All", null).contains(category)) {
            throw new ValidationException("카테고리는 FOOD, DRINK 중 하나로 입력해주세요.");
        }
        final Page<UserBookmarkInfo> userBookmarkInfoPage = userBookmarkService.getUserBookmarkInfo(name, category, new PageInfo(page, size));
        // 2. 조회한 사용자를 바탕으로 찜 정보 조회
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, userBookmarkInfoPage));
    }

    @Operation(summary = "사용자 찜 등록/삭제 API", description = "사용자 찜 등록/삭제 API")
    @Transactional
    @PostMapping("/{comboItemId}")
    public ResponseEntity<Response<UserBookmarkInfo>> toggleBookmark(@PathVariable Long comboItemId) {

        String name = userService.getUserName();

        if (StringUtils.isBlank(name)) {
            throw new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다. 다시 로그인해주세요.");
        }
        final UserBookmarkInfo userBookmarkInfo = userBookmarkService.toggleUserBookmark(name, comboItemId);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, userBookmarkInfo));
    }
}
