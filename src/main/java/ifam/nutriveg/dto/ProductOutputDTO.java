package ifam.nutriveg.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOutputDTO {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private String foodCategoryName;

    private boolean availability;

    private boolean isVegetarian;

    private String imageUrl;

}
