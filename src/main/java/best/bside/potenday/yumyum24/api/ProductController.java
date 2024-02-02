package best.bside.potenday.yumyum24.api;

import best.bside.potenday.yumyum24.payload.Response;
import best.bside.potenday.yumyum24.payload.responses.TopProduct;
import best.bside.potenday.yumyum24.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/product")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "인기 상품 Top3 목록 조회", description = "인기 상품 Top3 목록 조회 API.")
    @GetMapping("/top3")
    public ResponseEntity<Response<List<TopProduct>>> getTopProducts() {
        final List<TopProduct> topProducts = productService.getTopProducts();
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK, topProducts));
    }
}
