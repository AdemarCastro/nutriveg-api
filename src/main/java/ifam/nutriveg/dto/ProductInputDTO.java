package ifam.nutriveg.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class ProductInputDTO {

    @NotBlank(message = "O nome do produto é obrigatório.")
    @Schema(description = "Nome do produto.", example = "Sanduíche Vegano")
    private String name;

    @Schema(description = "Descrição detalhada do produto.", example = "Delicioso sanduíche vegano com ingredientes orgânicos.")
    private String description;

    @NotNull(message = "O preço é obriatório.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero.")
    @Schema(description = "Preço do produto em reais (R$). Deve ser maior que zero.", example = "19.99")
    private BigDecimal price;

    @NotBlank(message = "O nome da categoria é obrigatório.")
    @Schema(description = "Nome da categoria de alimentos à qual o produto pertence.", example = "Lanches")
    private String foodCategoryName;

    @Schema(description = "Indica se o produto está disponível para compra.", example = "true")
    private Boolean availability;

    @NotNull(message = "É necessário indicar se o produto é vegetariano.")
    @Schema(description = "Indica se o produto é vegetariano. Use 'true' para vegetariano e 'false' para não vegetariano.", example = "true")
    private Boolean isVegetarian;

    @Schema(description = "URL da imagem do produto.", example = "https://images.pexels.com/photos/5713770/pexels-photo-5713770.jpeg")
    private String image;

    public ProductInputDTO() {
    }

    public ProductInputDTO(String name, String description, BigDecimal price, String foodCategoryName, Boolean availability, Boolean isVegetarian, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.foodCategoryName = foodCategoryName;
        this.availability = availability;
        this.isVegetarian = isVegetarian;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFoodCategoryName() {
        return foodCategoryName;
    }

    public void setFoodCategoryName(String foodCategoryName) {
        this.foodCategoryName = foodCategoryName;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Boolean getIsVegetarian() {
        return isVegetarian;
    }

    public void setIsVegetarian(Boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
