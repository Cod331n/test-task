package ru.cod331n.pet;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import ru.cod331n.pet.phrases.PetPhrases;
import ru.cod331n.pet.phrases.PhrasesType;

import java.util.Set;

@Data
@Accessors(fluent = true, chain = true)
public abstract class Pet {
    @NonNull
    private long id;
    @NonNull
    private int age;
    @NonNull
    private String name;
    @NonNull
    private Set<String> aliases;
    @NonNull
    private PetType type;
    public void sayPhrase() {
        System.out.println(name + ": " + PetPhrases.getRandomPhrase(PhrasesType.ROUTINE));
    }

    public void sayGreeting() {
        System.out.println(PetPhrases.getRandomPhrase(PhrasesType.GREETING));
    }
}
