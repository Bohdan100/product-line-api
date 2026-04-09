package corp.product.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import corp.product.data.Line;

@Repository
public interface LineRepository extends CrudRepository<Line, Long> {
    List<Line> findAllByOrderByIdAsc();
}