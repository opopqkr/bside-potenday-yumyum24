package best.bside.potenday.yumyum24.api;

import best.bside.potenday.yumyum24.domain.ComboItemDescription;
import best.bside.potenday.yumyum24.payload.Response;
import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.requests.ReplyRequest;
import best.bside.potenday.yumyum24.payload.responses.ComboItemInfo;
import best.bside.potenday.yumyum24.payload.responses.RecommendComboItem;
import best.bside.potenday.yumyum24.payload.responses.ReplyInfo;
import best.bside.potenday.yumyum24.service.ComboItemService;
import best.bside.potenday.yumyum24.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/combo-item")
@RequiredArgsConstructor
public class ComboItemController {

    private final UserService userService;

    private final ComboItemService comboItemService;

    @Operation(summary = "꿀 조합 랜덤 조회", description = "꿀 조합 랜덤 조회 API.")
    @GetMapping("/random")
    public ResponseEntity<Response<List<ComboItemInfo>>> getRandomComboItem() {
        final List<ComboItemInfo> randomComboItems = comboItemService.getRandomComboItem();
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, randomComboItems));
    }

    @Operation(summary = "꿀 조합 추천(조합 + 조합) 조회",
            description = "꿀 조합 추천(조합 + 조합) 조회 API.")
    @GetMapping("/recommend")
    public ResponseEntity<Response<RecommendComboItem>> getRecommendComboItem() {
        final RecommendComboItem randomComboItem = comboItemService.getRecommendComboItem();
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, randomComboItem));
    }

    @Operation(summary = "꿀 조합 목록 조회", description = "꿀 조합 목록 조회 API.")
    @GetMapping("/list")
    public ResponseEntity<Response<Page<ComboItemInfo>>> getComboItem(@RequestParam("category") String category,
                                                                      @RequestParam("sortBy") String sortBy,
                                                                      @RequestParam("page") int page,
                                                                      @RequestParam("size") int size) {

        final Page<ComboItemInfo> comboItemInfoPage
                = comboItemService.getComboItem(category, sortBy, new PageInfo(page, size));
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, comboItemInfoPage));
    }

    @Operation(summary = "꿀 조합 레시피 조회", description = "꿀 조합 레시피 조회 API.")
    @GetMapping("/{id}")
    public ResponseEntity<Response<List<ComboItemDescription>>> getComboItemDescription(@PathVariable("id") Long id) {
        final List<ComboItemDescription> comboItemDescription = comboItemService.getComboItemDescription(id);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, comboItemDescription));
    }

    @Operation(summary = "꿀 조합 댓글 조회", description = "꿀 조합 댓글 조회 API.")
    @GetMapping("/{comboItemId}/reply")
    public ResponseEntity<Response<Page<ReplyInfo>>> getComboItemReply(@PathVariable("comboItemId") Long comboItemId,
                                                                       @RequestParam("page") int page,
                                                                       @RequestParam("size") int size) {
        String email = userService.getEmail();

        final Page<ReplyInfo> comboItemReplyPage = comboItemService.getComboItemReply(email, comboItemId, new PageInfo(page, size));
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, comboItemReplyPage));
    }

    @Operation(summary = "꿀 조합 댓글 등록", description = "꿀 조합 댓글 등록 API.")
    @PostMapping("/{comboItemId}/reply")
    public ResponseEntity<Response<Void>> writeReply(@PathVariable("comboItemId") Long comboItemId,
                                                     @RequestBody ReplyRequest replyRequest) {
        String email = userService.getEmail();
        comboItemService.writeReply(email, comboItemId, replyRequest);
        return ResponseEntity.ok(new Response<>(HttpStatus.CREATED));
    }

    @Operation(summary = "꿀 조합 댓글 수정", description = "꿀 조합 댓글 수정 API.")
    @PutMapping("/{replyId}")
    public ResponseEntity<Response<Void>> updateReply(@PathVariable("replyId") Long replyId,
                                                      @RequestBody ReplyRequest replyRequest) {
        comboItemService.updateReply(replyId, replyRequest);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK));
    }

    @Operation(summary = "꿀 조합 댓글 삭제", description = "꿀 조합 댓글 삭제 API.")
    @DeleteMapping("/{comboItemId}/{replyId}")
    public ResponseEntity<Response<Void>> deleteReply(@PathVariable("comboItemId") Long comboItemId,
                                                      @PathVariable("replyId") Long replyId) {

        comboItemService.deleteReply(comboItemId, replyId);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK));
    }
}
