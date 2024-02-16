package ru.cod331n.mapper;

import lombok.experimental.UtilityClass;
import ru.cod331n.pet.PetType;
import ru.cod331n.pet.dto.PetDTO;
import ru.cod331n.user.UserSex;
import ru.cod331n.user.dto.UserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;

@UtilityClass
public class ResultSetMapper {
    public UserDTO toUserDTO(ResultSet rs) {
        UserDTO userDTO;
        try {
            userDTO = new UserDTO(
                    rs.getLong("id"),
                    rs.getInt("age"),
                    rs.getString("name"),
                    UserSex.valueOf(rs.getString("sex"))
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return userDTO;
    }

    public PetDTO toPetDTO(ResultSet rs) {
        PetDTO petDTO;
        try {
            petDTO = new PetDTO(
                    rs.getLong("id"),
                    rs.getInt("age"),
                    rs.getString("name"),
                    new HashSet<>(Arrays.asList((String[]) rs.getArray("aliases").getArray())),
                    PetType.valueOf(rs.getString("type"))
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return petDTO;
    }
}
