package ifam.nutriveg.service;

import ifam.nutriveg.dto.ProductInputDTO;
import ifam.nutriveg.dto.ProductOutputDTO;
import ifam.nutriveg.exceptions.GenericException;
import ifam.nutriveg.exceptions.ProductNotFoundException;
import ifam.nutriveg.mapper.ProductMapper;
import ifam.nutriveg.model.FoodCategory;
import ifam.nutriveg.model.Product;
import ifam.nutriveg.repository.FoodCategoryRepository;
import ifam.nutriveg.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final FoodCategoryRepository foodCategoryRepository;

    public ProductService(ProductRepository productRepository, FoodCategoryRepository foodCategoryRepository) {
        this.productRepository = productRepository;
        this.foodCategoryRepository = foodCategoryRepository;
    }

    public List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            throw new GenericException("Erro ao recuperar os produtos.", e);
        }
    }

    public Product getProductById(Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isEmpty()) {
                throw new ProductNotFoundException("Produto com ID " + id + " não encontrado.");
            }
            return product.get();
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GenericException("Erro ao buscar o produto com ID " + id + ".", e);
        }
    }

    public Product createProduct(Product product) {
        try {
            if (product.getFoodCategory() != null) {
                FoodCategory foodCategory = product.getFoodCategory();

                if (foodCategory.getId() == null) {
                    Optional<FoodCategory> existingCategory = foodCategoryRepository.findByName(foodCategory.getName());
                    if (existingCategory.isPresent()) {
                        product.setFoodCategory(existingCategory.get());
                    } else {
                        FoodCategory savedCategory = foodCategoryRepository.save(foodCategory);
                        product.setFoodCategory(savedCategory);
                    }
                } else {
                    FoodCategory existingCategory = foodCategoryRepository.findById(foodCategory.getId())
                            .orElseThrow(() -> new GenericException("Categoria não encontrada.", new Exception("Categoria com ID " + foodCategory.getId() + " não existe.")));
                    product.setFoodCategory(existingCategory);
                }
            }

            // Salva o produto com a categoria associada
            return productRepository.save(product);
        } catch (Exception e) {
            throw new GenericException("Erro ao criar o produto.", e);
        }
    }

    public ProductOutputDTO updateProduct(ProductInputDTO productInputDTO, Long id) {
        // Busca o produto existente pelo ID
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto com ID " + id + " não encontrado."));

        try {
            // Converte o DTO para o modelo Product utilizando o ProductMapper
            Product updatedProduct = ProductMapper.toModel(productInputDTO);

            // Atualiza os campos do produto existente com os dados do DTO
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setAvailability(updatedProduct.getAvailability());
            existingProduct.setIsVegetarian(updatedProduct.getIsVegetarian());
            existingProduct.setImageUrl(updatedProduct.getImageUrl());

            // Atualiza a categoria de comida, se fornecida no DTO
            if (updatedProduct.getFoodCategory() != null) {
                FoodCategory foodCategory = updatedProduct.getFoodCategory();

                if (foodCategory.getId() == null) {
                    // Se a categoria não tem ID, verifica se já existe no banco de dados
                    Optional<FoodCategory> existingCategory = foodCategoryRepository.findByName(foodCategory.getName());
                    if (existingCategory.isPresent()) {
                        existingProduct.setFoodCategory(existingCategory.get());
                    } else {
                        // Caso contrário, salva a nova categoria de alimento
                        FoodCategory savedCategory = foodCategoryRepository.save(foodCategory);
                        existingProduct.setFoodCategory(savedCategory);
                    }
                } else {
                    // Se já tem ID, apenas verifica se a categoria existe
                    FoodCategory existingCategory = foodCategoryRepository.findById(foodCategory.getId())
                            .orElseThrow(() -> new RuntimeException("Categoria de alimento com ID " + foodCategory.getId() + " não encontrada."));
                    existingProduct.setFoodCategory(existingCategory);
                }
            }

            // Salva o produto atualizado no banco de dados
            Product savedProduct = productRepository.save(existingProduct);

            // Retorna o DTO do produto atualizado utilizando o ProductMapper
            return ProductMapper.toOutputDTO(savedProduct);
        } catch (RuntimeException e) {
            log.error("Erro ao atualizar o produto com ID " + existingProduct.getId(), e);
            throw new RuntimeException("Erro ao atualizar o produto: " + e.getMessage());
        }
    }



    public void deleteProduct(Long id) {
        try {
            if (!productRepository.existsById(id)) {
                throw new ProductNotFoundException("Produto com ID " + id + " não encontrado.");
            }
            productRepository.deleteById(id);
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GenericException("Erro ao deletar o produto com ID " + id + ".", e);
        }
    }

}
