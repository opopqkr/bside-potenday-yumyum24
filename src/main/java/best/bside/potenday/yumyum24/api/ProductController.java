package best.bside.potenday.yumyum24.api;

import best.bside.potenday.yumyum24.domain.Product;
import best.bside.potenday.yumyum24.payload.requests.NewProduct;
import best.bside.potenday.yumyum24.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/product")
public class ProductController {

    // FIXME Service Layer 옮길 예정
    private final ProductRepository productRepository;

    @Operation(summary = "상품 목록 조회", description = "상품 목록 조회 API.")
    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        final List<Product> list = productRepository.findAll();

        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "상품 상세 조회", description = "상품 상세 조회 API.")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@RequestParam long id) {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new ValidationException("해당 상품을 찾을 수 없습니다."));

        return ResponseEntity.ok().body(product);
    }

    @Operation(summary = "상품 수정", description = "상품 수정 API.")
    @Transactional
    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(productRepository.save(product));
    }

    @Operation(summary = "상품 저장", description = "상품 저장 API.")
    @Transactional
    @PostMapping
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody NewProduct newProduct) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productRepository.save(newProduct.toEntity()));
    }

    @Operation(summary = "상품 삭제", description = "상품 삭제 API.")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@RequestParam long id) {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new ValidationException("해당 상품을 찾을 수 없습니다."));

        productRepository.deleteById(product.getId());
        return ResponseEntity.ok().build();
    }
}
