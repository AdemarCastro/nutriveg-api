package ifam.nutriveg.mapper;

import ifam.nutriveg.dto.ProductInputDTO;
import ifam.nutriveg.dto.ProductOutputDTO;
import ifam.nutriveg.model.FoodCategory;
import ifam.nutriveg.model.Product;

public class ProductMapper {

    public static ProductOutputDTO toOutputDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductOutputDTO productOutputDTO = new ProductOutputDTO();
        productOutputDTO.setId(product.getId());
        productOutputDTO.setName(product.getName());
        productOutputDTO.setDescription(product.getDescription());
        productOutputDTO.setPrice(product.getPrice());
        productOutputDTO.setAvailability(product.getAvailability());
        productOutputDTO.setIsVegetarian(product.getIsVegetarian());
        productOutputDTO.setImageUrl(product.getImageUrl());

        if (product.getFoodCategory() != null) {
            productOutputDTO.setFoodCategoryName(product.getFoodCategory().getName());
        }

        return productOutputDTO;
    }

    public static Product toModel(ProductInputDTO productInputDTO) {
        if (productInputDTO == null) {
            return null;
        }

        Product product = new Product();
        product.setName(productInputDTO.getName());
        product.setDescription(productInputDTO.getDescription());
        product.setPrice(productInputDTO.getPrice());
        product.setAvailability(productInputDTO.getAvailability());
        product.setIsVegetarian(productInputDTO.getIsVegetarian());
        product.setImageUrl(productInputDTO.getImage());

        if (productInputDTO.getFoodCategoryName() != null) {
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setName(productInputDTO.getFoodCategoryName());
            // Aqui você poderia buscar a categoria no banco por nome, se necessário
            product.setFoodCategory(foodCategory);
        }

        return product;
    }

    public static Product toModelFromOutputDTO(ProductOutputDTO productOutputDTO) {
        if (productOutputDTO == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productOutputDTO.getId());
        product.setName(productOutputDTO.getName());
        product.setDescription(productOutputDTO.getDescription());
        product.setPrice(productOutputDTO.getPrice());
        product.setAvailability(productOutputDTO.getAvailability());
        product.setIsVegetarian(productOutputDTO.getIsVegetarian());
        product.setImageUrl(productOutputDTO.getImageUrl());

        // Se necessário, mapeie a categoria da saída para o modelo
        if (productOutputDTO.getFoodCategoryName() != null) {
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setName(productOutputDTO.getFoodCategoryName());
            product.setFoodCategory(foodCategory);
        }

        return product;
    }

    public static ProductInputDTO toInputDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductInputDTO productInputDTO = new ProductInputDTO();
        productInputDTO.setName(product.getName());
        productInputDTO.setDescription(product.getDescription());
        productInputDTO.setPrice(product.getPrice());
        productInputDTO.setAvailability(product.getAvailability());
        productInputDTO.setIsVegetarian(product.getIsVegetarian());
        productInputDTO.setImage(product.getImageUrl());

        // Mapeando a FoodCategory para o DTO de entrada
        if (product.getFoodCategory() != null) {
            productInputDTO.setFoodCategoryName(product.getFoodCategory().getName());
        }

        return productInputDTO;
    }
}
