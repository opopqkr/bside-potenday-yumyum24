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

    // private final ProductRepository productRepository;

    @Operation(summary = "인기 상품 Top3 목록 조회", description = "인기 상품 Top3 목록 조회 API.")
    @GetMapping("/top3")
    public ResponseEntity<Response<List<TopProduct>>> getTopProducts() {
        final List<TopProduct> topProducts = productService.getTopProducts();
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK, topProducts));
    }

    /*
    @Operation(summary = "상품 목록 조회", description = "상품 목록 조회 API.")
    @GetMapping
    public ResponseEntity<Response<List<Product>>> getProducts() {
        final List<Product> list = productRepository.findAll();

        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK, list));
    }

    @Operation(summary = "상품 상세 조회", description = "상품 상세 조회 API.")
    @GetMapping("/{id}")
    public ResponseEntity<Response<Product>> getProduct(@RequestParam long id) {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new ValidationException("해당 상품을 찾을 수 없습니다."));

        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK, product));
    }

    @Operation(summary = "상품 수정", description = "상품 수정 API.")
    @Transactional
    @PutMapping
    public ResponseEntity<Response<Product>> updateProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(HttpStatus.OK, productRepository.save(product)));
    }

    @Operation(summary = "상품 저장", description = "상품 저장 API.")
    @Transactional
    @PostMapping
    public ResponseEntity<Response<Product>> saveProduct(@Valid @RequestBody NewProduct newProduct) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(HttpStatus.CREATED, productRepository.save(newProduct.toEntity())));
    }

    @Operation(summary = "상품 삭제", description = "상품 삭제 API.")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteProduct(@RequestParam long id) {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new ValidationException("해당 상품을 찾을 수 없습니다."));

        productRepository.deleteById(product.getProductId());
        return ResponseEntity.ok(new Response<>(HttpStatus.OK));
    }
    */
}
