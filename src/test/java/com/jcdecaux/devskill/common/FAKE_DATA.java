package com.jcdecaux.devskill.common;

import com.jcdecaux.devskill.entity.Developer;
import com.jcdecaux.devskill.entity.Language;

import java.util.List;

public class FAKE_DATA {
    public static List<Developer> developers = List.of(
            Developer.builder()
                    .id(1L)
                    .email("john.doe@devskill.com")
                    .name("John Doe")
                    .build(),

            Developer.builder()
                    .email("djibril.diop@devskill.com")
                    .name("Djibril Diop")
                    .build()

    );
    public static List<Language> languages = List.of(
            Language.builder()
                    .id(1L)
                    .name("Kotlin")
                    .build(),

            Language.builder()
                    .name("C++")
                    .build()

    );
}
