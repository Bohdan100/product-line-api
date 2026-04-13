package corp.product.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import corp.product.dto.UserDto;
import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDto> getAllUsers();
    UserDto findById(long id);
    void create(UserDto userDto);
    void update(UserDto userDto);
    void delete(long id);
}