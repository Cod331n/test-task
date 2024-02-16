package ru.cod331n.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.cod331n.user.UserSex;

@Data
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
public class UserDTO {
    private long id;
    private int age;
    private String name;
    private UserSex sex;
}
