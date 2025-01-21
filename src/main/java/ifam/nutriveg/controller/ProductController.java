package ifam.nutriveg.controller;

import ifam.nutriveg.dto.ProductInputDTO;
import ifam.nutriveg.dto.ProductOutputDTO;
import ifam.nutriveg.exceptions.ProductNotFoundException;
import ifam.nutriveg.mapper.ProductMapper;
import ifam.nutriveg.model.Product;
import ifam.nutriveg.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ProductOutputDTO>> getAllProducts() {
        try {
            List<ProductOutputDTO> productList = productService.getAllProducts().stream()
                    .map(ProductMapper::toOutputDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOutputDTO> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductOutputDTO productOutputDTO = ProductMapper.toOutputDTO(product);
            return new ResponseEntity<>(productOutputDTO, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ProductOutputDTO> createProduct(@RequestBody ProductInputDTO productInputDTO) {
        try {
            Product product = ProductMapper.toModel(productInputDTO);
            Product savedProduct = productService.createProduct(product);
            ProductOutputDTO productOutputDTO = ProductMapper.toOutputDTO(savedProduct);
            return new ResponseEntity<>(productOutputDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOutputDTO> updateProduct(@PathVariable Long id, @RequestBody ProductInputDTO productInputDTO) {
        try {
            ProductOutputDTO updatedProductDTO = productService.updateProduct(productInputDTO, id);
            return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
