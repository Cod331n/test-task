package ru.cod331n.user;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(fluent = true, chain = true)
public abstract class User {
    @NonNull
    private long id;
    @NonNull
    private int age;
    @NonNull
    private String name;
    @NonNull
    private UserSex sex;

}
