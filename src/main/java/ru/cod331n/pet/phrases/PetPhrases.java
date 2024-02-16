package ru.cod331n.pet.phrases;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;
import ru.cod331n.Main;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@UtilityClass
public class PetPhrases {
    public String getRandomPhrase(PhrasesType type) {
        String name = type.name().toLowerCase(Locale.ROOT) + "Phrases.json";
        URL filepath = Main.class.getClassLoader().getResource(name);

        if (filepath == null) {
            return getRandomPhrase(new ArrayList<>());
        }

        try (FileReader reader = new FileReader(filepath.getPath())) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<String>>() {}.getType();
            List<String> phrases = gson.fromJson(reader, listType);

            return getRandomPhrase(phrases);
        } catch (IOException e) {
            e.printStackTrace();
            return getRandomPhrase(new ArrayList<>());
        }
    }

    private String getRandomPhrase(List<String> phrases) {
        if (phrases == null || phrases.isEmpty()) {
            return "No phrases set.";
        }

        Random random = new Random();
        int index = random.nextInt(phrases.size());
        return phrases.get(index);
    }
}
