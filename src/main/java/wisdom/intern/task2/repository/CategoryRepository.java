package wisdom.intern.task2.repository;

import wisdom.intern.task2.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
