package corp.product.converter;

import org.springframework.stereotype.Component;
import corp.product.data.User;
import corp.product.dto.UserDto;

@Component
public class UserConverter extends Converter<UserDto, User> {


    public UserConverter() {
        super(UserConverter::convertToEntity, UserConverter::convertToDto);
    }

    private static User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setRoles(userDto.getRoles());
        return user;
    }

    private static UserDto convertToDto(User user) {
        return new UserDto(user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getSurname(),
                user.getRoles());
    }
}
