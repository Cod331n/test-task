package ru.cod331n.mapper;


import lombok.experimental.UtilityClass;
import ru.cod331n.pet.Pet;
import ru.cod331n.pet.SimplePet;
import ru.cod331n.pet.dto.PetDTO;

@UtilityClass
public class PetMapper {
    public static PetDTO toDTO(Pet pet) {
        return new PetDTO(
                pet.id(),
                pet.age(),
                pet.name(),
                pet.aliases(),
                pet.type()
        );
    }

    public static Pet toPet(PetDTO dto) {
        return new SimplePet(
                dto.id(),
                dto.age(),
                dto.name(),
                dto.aliases(),
                dto.type()
        );
    }
}
