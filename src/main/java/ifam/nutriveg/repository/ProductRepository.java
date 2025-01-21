package ifam.nutriveg.repository;

import ifam.nutriveg.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Busca produtos por nome
    List<Product> findByNameContainingIgnoreCase(String name);

    // Busca produtos por disponibilidade
    List<Product> findByAvailabilityTrue();

    // Busca produtos vegetarianos
    List<Product> findByIsVegetarianTrue();

    // Busca produtos por categoria
    List<Product> findByFoodCategoryName(String foodCategoryName);

    // Busca produto especifico por nome e categoria
    Optional<Product> findByNameAndFoodCategoryName(String name, String foodCategoryName);
}
