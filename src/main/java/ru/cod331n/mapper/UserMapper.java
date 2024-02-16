package ru.cod331n.mapper;

import lombok.experimental.UtilityClass;
import ru.cod331n.user.SimpleUser;
import ru.cod331n.user.User;
import ru.cod331n.user.dto.UserDTO;

@UtilityClass
public class UserMapper {
    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.id(),
                user.age(),
                user.name(),
                user.sex()
        );
    }

    public User toUser(UserDTO dto) {
        return new SimpleUser(
                dto.id(),
                dto.age(),
                dto.name(),
                dto.sex()
        );
    }
}
