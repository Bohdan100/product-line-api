package corp.product.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;

import corp.product.repository.UserRepository;
import corp.product.converter.UserConverter;
import corp.product.dto.UserDto;
import corp.product.data.User;
import corp.product.exception.types.ResourceNotFoundException;
import corp.product.exception.types.EntityAlreadyExistsException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserConverter converter;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public List<UserDto> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(converter::convertFromEntity)
                .collect(Collectors.toList());
    }

    public UserDto findById(long id) {
        return userRepository.findById(id)
                .map(converter::convertFromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
    }

    @Transactional
    public void create(UserDto userDto) {
        userRepository.findByUsername(userDto.getUsername()).ifPresent(u -> {
            throw new EntityAlreadyExistsException("User with username '" + userDto.getUsername() + "' already exists.");
        });

        User user = converter.convertFromDto(userDto);
        userRepository.save(user);
    }

    @Transactional
    public void update(UserDto userDto) {
        User existingUser = userRepository.findById(userDto.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User with ID " + userDto.getId() + " not found"));

        if (!existingUser.getUsername().equals(userDto.getUsername())) {
            userRepository.findByUsername(userDto.getUsername()).ifPresent(u -> {
                throw new EntityAlreadyExistsException("Username '" + userDto.getUsername() + "' is already taken.");
            });
        }

        existingUser.setUsername(userDto.getUsername());
        existingUser.setPassword(userDto.getPassword());
        existingUser.setName(userDto.getName());
        existingUser.setSurname(userDto.getSurname());
        existingUser.getRoles().clear();
        existingUser.getRoles().addAll(userDto.getRoles());

        userRepository.save(existingUser);
    }

    @Transactional
    public void delete(long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete: User not found with id " + id);
        }
        userRepository.deleteById(id);
    }
}