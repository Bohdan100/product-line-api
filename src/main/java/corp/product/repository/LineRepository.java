package corp.product.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import corp.product.data.Line;

@Repository
public interface LineRepository extends CrudRepository<Line, Long> {
    @Query(value = "select * from line where id = ?1", nativeQuery = true)
    Line getOne(Long id);

    @Query(value = "select * from Line order by id", nativeQuery = true)
    @NonNull
    List<Line> findAll();

}
