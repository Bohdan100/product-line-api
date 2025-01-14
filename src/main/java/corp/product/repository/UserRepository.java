package corp.product.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import corp.product.data.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u")
    @NonNull
    List<User> findAll();

    @Query("SELECT u FROM User u WHERE u.id = :id")
    User getOne(@Param("id") long id);
}
