package corp.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.security.test.context.support.WithMockUser;
import java.util.List;
import java.util.Optional;

import corp.product.service.impl.UserServiceImpl;
import corp.product.repository.UserRepository;
import corp.product.converter.UserConverter;
import corp.product.data.User;
import corp.product.dto.UserDto;
import corp.product.data.Role;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "testuser", "password123", "John", "Smith", List.of(Role.ADMIN), List.of());
        testUserDto = new UserDto(1L, "testuser", "password123", "John", "Smith", List.of(Role.ADMIN));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateUser() {
        when(userRepository.findByUsername(testUserDto.getUsername())).thenReturn(Optional.empty());
        when(userConverter.convertFromDto(testUserDto)).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.create(testUserDto);

        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotCreateUserWhenUsernameExists() {
        when(userRepository.findByUsername(testUserDto.getUsername())).thenReturn(Optional.of(testUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.create(testUserDto));
        assertEquals("User with this username already exists.", exception.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldFindUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userConverter.convertFromEntity(testUser)).thenReturn(testUserDto);

        UserDto result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(testUserDto.getUsername(), result.getUsername());
        verify(userRepository).findById(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnAllUsers() {
        List<User> users = List.of(testUser);
        when(userRepository.findAll()).thenReturn(users);
        when(userConverter.createFromEntities(users)).thenReturn(List.of(testUserDto));

        List<UserDto> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testUserDto.getUsername(), result.get(0).getUsername());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        userService.delete(1L);

        verify(userRepository).deleteById(1L);
    }
}