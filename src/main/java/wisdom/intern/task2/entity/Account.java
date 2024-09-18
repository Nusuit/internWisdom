package wisdom.intern.task2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="account")

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 20)
    private String username; // ánh xạ với VARCHAR(20)
    @Column(nullable = false, length = 256)
    private String password; // ánh xạ với VARCHAR(256)
    @Column(nullable = false, length = 20)
    private String role; // ánh xạ với VARCHAR(20)
}
