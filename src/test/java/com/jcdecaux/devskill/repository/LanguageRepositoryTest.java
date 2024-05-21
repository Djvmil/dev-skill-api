package com.jcdecaux.devskill.repository;

import com.jcdecaux.devskill.entity.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@DataJpaTest
public class LanguageRepositoryTest {
    @Autowired
    LanguageRepository repository;

    @Test
    public void shouldSaveAndFetchLanguage() {
        //GIVEN
        Language exceptedLanguage = new Language();
        exceptedLanguage.setId(1L);
        exceptedLanguage.setName("Java");

        //WHEN
        repository.save(exceptedLanguage);
        var actualLanguage = repository.findByName(exceptedLanguage.getName());

        //THEN
        assertThat(actualLanguage,  is(Optional.of(exceptedLanguage)));
    }

}
