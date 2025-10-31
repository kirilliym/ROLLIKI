package dev.kochki.rolliki.service;

import dev.kochki.rolliki.mapper.UserMapper;
import dev.kochki.rolliki.model.dto.UserDTO;
import dev.kochki.rolliki.model.entity.User;
import dev.kochki.rolliki.model.request.UserRequest;
import dev.kochki.rolliki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO register(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.username())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(userRequest.username());
        user.setPassword(userRequest.password()); // В реальном приложении здесь должно быть хэширование пароля

        User savedUser = userRepository.save(user);
        return userMapper.mapToDTO(savedUser);
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> login(UserRequest userRequest) {
        Optional<User> user = userRepository.findByUsername(userRequest.username());

        if (user.isPresent() && user.get().getPassword().equals(userRequest.password())) {
            return Optional.of(userMapper.mapToDTO(user.get()));
        }

        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::mapToDTO);
    }

    @Transactional(readOnly = true)
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }
}