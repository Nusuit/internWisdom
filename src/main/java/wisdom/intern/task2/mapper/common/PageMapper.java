package wisdom.intern.task2.mapper.common;

import org.springframework.data.domain.Pageable;

public interface PageMapper {
    Pageable customPage(Integer pageNo, Integer pageSize, String sortBy, String sortType);
}
