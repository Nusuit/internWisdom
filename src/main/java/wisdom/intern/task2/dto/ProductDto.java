package wisdom.intern.task2.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductDto {
    private Integer productId;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer category_Id;
}
