package ru.cod331n.pet.async;

import ru.cod331n.pet.Pet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PetScheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void schedulePet(Pet pet, int startDelay) {
        scheduler.scheduleAtFixedRate(pet::sayPhrase, startDelay, 60, TimeUnit.SECONDS);
    }

    public void stopScheduler() {
        scheduler.shutdown();
    }
}
