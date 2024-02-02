package best.bside.potenday.yumyum24.api;

import best.bside.potenday.yumyum24.payload.Response;
import best.bside.potenday.yumyum24.payload.domain.Page;
import best.bside.potenday.yumyum24.payload.domain.PageInfo;
import best.bside.potenday.yumyum24.payload.responses.ComboItemInfo;
import best.bside.potenday.yumyum24.payload.responses.RecommendComboItem;
import best.bside.potenday.yumyum24.service.ComboItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/combo-item")
@RequiredArgsConstructor
public class ComboItemController {

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
}
