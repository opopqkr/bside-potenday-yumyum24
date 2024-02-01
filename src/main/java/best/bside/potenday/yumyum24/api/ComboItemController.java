package best.bside.potenday.yumyum24.api;

import best.bside.potenday.yumyum24.payload.Response;
import best.bside.potenday.yumyum24.payload.responses.ComboItemInfo;
import best.bside.potenday.yumyum24.service.ComboItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        final List<ComboItemInfo> comboItemInfo = comboItemService.getRandomComboItem();
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, comboItemInfo));
    }
}
