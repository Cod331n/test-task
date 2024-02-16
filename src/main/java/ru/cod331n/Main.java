package ru.cod331n;

import ru.cod331n.db.dao.PetDao;
import ru.cod331n.db.dao.UserDao;
import ru.cod331n.id.Id;
import ru.cod331n.mapper.PetMapper;
import ru.cod331n.mapper.UserMapper;
import ru.cod331n.pet.Pet;
import ru.cod331n.pet.PetType;
import ru.cod331n.pet.SimplePet;
import ru.cod331n.user.SimpleUser;
import ru.cod331n.user.User;
import ru.cod331n.user.UserSex;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        UserDao dao = new UserDao();
        PetDao dao2 = new PetDao();
        Pet pet = new SimplePet(Id.nextId() ,12, "Fluffy", Set.of("Fluff", "Fluffik"), PetType.DOG);
        User user = new SimpleUser(Id.nextId(), 42, "Misha", UserSex.MALE);
        System.out.println(user);
        dao.createTable();
        dao.save(UserMapper.toDTO(user));
        dao2.createTable();
        dao2.save(PetMapper.toDTO(pet));

        System.out.println(pet.id());
        System.out.println(user.id());
    }
}