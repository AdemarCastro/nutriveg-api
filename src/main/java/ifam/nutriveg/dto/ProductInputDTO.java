package ifam.nutriveg.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInputDTO {

    @NotBlank(message = "O nome do produto é obrigatório.")
    private String name;

    private String description;

    @NotNull(message = "O preço é obriatório.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero.")
    private BigDecimal price;

    @NotBlank(message = "O nome da categoria é obrigatório.")
    private String foodCategoryName;

    private boolean availability;

    @NotNull(message = "É necessário indicar se o produto é vegetariano.")
    private Boolean vegetarian;

    private String image;

}
