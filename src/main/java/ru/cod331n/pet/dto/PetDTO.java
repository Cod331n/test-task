package ru.cod331n.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.cod331n.pet.PetType;

import java.util.Set;

@Data
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
public class PetDTO {
    private long id;
    private int age;
    private String name;
    private Set<String> aliases;
    private PetType type;
}
