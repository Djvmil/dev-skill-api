package com.jcdecaux.devskill.repository;

import com.jcdecaux.devskill.common.FAKE_DATA;
import com.jcdecaux.devskill.entity.Language;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LanguageRepositoryTest {
    @Autowired
    LanguageRepository repository;

    @Test
    @Order(1)
    public void shouldSaveAndFetchLanguage() {
        //GIVEN
        Language exceptedLanguage = FAKE_DATA.languages.getFirst().toBuilder().build();


        //WHEN
        exceptedLanguage.setId(1L);
        repository.saveAndFlush(exceptedLanguage);
        var actualLanguage = repository.findById(exceptedLanguage.getId());

        //THEN
        assertThat(actualLanguage,  is(Optional.of(exceptedLanguage)));
    }

    @Test
    @Order(2)
    public void shouldReturnExceptionIfNameLanguageIsNull() {
        //GIVEN
        Language exceptedLanguage = FAKE_DATA.languages.getFirst().toBuilder().build();
        exceptedLanguage.setName(null);

        //WHEN
        Executable executable = () -> {
            repository.save(exceptedLanguage);
        };

        //THEN
        assertThrows(DataIntegrityViolationException.class, executable, "NULL not allowed for Name column");
    }

    @Test
    @Order(3)
    public void shouldReturnExceptionIfNameLanguageIsAlreadyExist() {
        //GIVEN
        Language exceptedLanguage = FAKE_DATA.languages.getFirst().toBuilder().build();

        //WHEN
        Executable executable = () -> {
            repository.save(exceptedLanguage);
            exceptedLanguage.setId(2L);
            repository.save(exceptedLanguage);
        };

        //THEN
        assertThrows(DataIntegrityViolationException.class, executable, "The name language already exists");
    }
}
