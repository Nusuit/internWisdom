package wisdom.intern.task2.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")

public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer ProductId;

    @Column(nullable = false)
    private String name; // ánh xạ với VARCHAR(255)
    @Column(nullable = false)
    private BigDecimal price; // ánh xạ với DECIMAL(10,2)
    @Column(nullable = true)
    private String description; // ánh xạ với TEXT

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_Id",referencedColumnName = "categoryId", nullable = false)
    private wisdom.intern.task2.entity.Category category;
}
