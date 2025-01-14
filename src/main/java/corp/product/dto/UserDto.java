package corp.product.dto;

import corp.product.data.Role;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = "roles")
public class UserDto implements Serializable {
    private Long id;

    @Size(min = 3, max = 15, message = "Login must contain from 3 to 15 characters!")
    private String username;

    @Size(min = 3, max = 15, message = "Password must be between 3 and 15 characters long!")
    private String password;

    @Size(min = 1, max = 25, message = "The name must contain from 1 to 25 characters!")
    private String name;

    @Size(min = 1, max = 25, message = "The surname must contain from 1 to 25 characters!")
    private String surname;

    @Size(min = 1, message = "Select a user role!")
    @NotNull(message = "Please select at least one role!")
    private Collection<Role> roles;
}


