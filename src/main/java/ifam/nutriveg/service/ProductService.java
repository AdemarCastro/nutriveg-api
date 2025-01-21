package ifam.nutriveg.service;

import ifam.nutriveg.exceptions.GenericException;
import ifam.nutriveg.exceptions.ProductNotFoundException;
import ifam.nutriveg.model.Product;
import ifam.nutriveg.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            throw new GenericException("Erro ao recuperar os produtos.", e);
        }
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Produto com ID " + id + " não encontrado.");
        }
        return product.get();
    }



}
