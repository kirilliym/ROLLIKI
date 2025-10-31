package dev.kochki.rolliki.mapper;

import dev.kochki.rolliki.model.dto.UserDTO;
import dev.kochki.rolliki.model.entity.User;
import dev.kochki.rolliki.model.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword()
        );
    }

    public User mapToEntity(UserDTO dto) {
        return new User(
                dto.getId(),
                dto.getUsername(),
                dto.getPassword()
        );
    }

    public UserResponse mapToResponse(UserDTO dto) {
        return new UserResponse(
                dto.getId(),
                dto.getUsername()
        );
    }

    public UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername()
        );
    }
}