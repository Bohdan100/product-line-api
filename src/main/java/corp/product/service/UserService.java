package corp.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import corp.product.repository.UserRepository;
import corp.product.converter.UserConverter;
import corp.product.dto.UserDto;
import corp.product.data.User;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final UserConverter converter;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return converter.createFromEntities(users);
    }

    public UserDto findById(long id) {
        User user = userRepository.getOne(id);
        return converter.convertFromEntity(user);
    }

    @Transactional
    public void create(UserDto userDto) {
        try {
            if (userRepository.findUserByUsername(userDto.getUsername()) != null) {
                throw new IllegalArgumentException("User with this username already exists.");
            }

            User user = converter.convertFromDto(userDto);
            userRepository.save(user);
        } catch (
                DataIntegrityViolationException e) {
            throw new IllegalArgumentException("User with this username already exists. Please choose a different one.");
        }
    }

    @Transactional
    public void update(UserDto userDto) {
        try {
            User existingUser = userRepository.findById(userDto.getId()).orElseThrow(() ->
                    new IllegalArgumentException("User with ID " + userDto.getId() + " not found"));

            if (!existingUser.getUsername().equals(userDto.getUsername()) &&
                    userRepository.findUserByUsername(userDto.getUsername()) != null) {
                throw new IllegalArgumentException("User with this username already exists.");
            }

            existingUser.setUsername(userDto.getUsername());
            existingUser.setPassword(userDto.getPassword());
            existingUser.setName(userDto.getName());
            existingUser.setSurname(userDto.getSurname());

            existingUser.getRoles().clear();
            existingUser.getRoles().addAll(userDto.getRoles());

            userRepository.save(existingUser);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("User with this username already exists. Please choose a different one.");
        }
    }

    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
