package ru.cod331n.pet;

import ru.cod331n.pet.async.PetScheduler;

import java.util.Set;


public class SimplePet extends Pet {

    private final PetScheduler petScheduler = new PetScheduler();

    public SimplePet(long id, int age, String name, Set<String> aliases, PetType type) {
        super(id, age, name, aliases, type);

        petScheduler.schedulePet(this, 2);

        super.sayGreeting();
    }
}
