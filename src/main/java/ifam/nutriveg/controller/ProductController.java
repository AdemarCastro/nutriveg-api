package ifam.nutriveg.controller;

import ifam.nutriveg.dto.ProductOutputDTO;
import ifam.nutriveg.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductOutputDTO> getAllProducts() {
        try {
            List<ProductOutputDTO> productList = productService.getAllProducts().stream()
                    .map(ProductMapper::toOutputDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
