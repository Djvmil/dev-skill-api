package com.jcdecaux.devskill.repository;

import com.jcdecaux.devskill.entity.Developer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@DataJpaTest
public class DeveloperRepositoryTest {

    @Autowired
    DeveloperRepository repository;

    @Test
    public void shouldSaveAndFetchDeveloper() {
        //GIVEN
        Developer exceptedDeveloper = new Developer();
        exceptedDeveloper.setId(1L);
        exceptedDeveloper.setName("John Doe");
        exceptedDeveloper.setEmail("john.doe@devskill.com");

        //WHEN
        repository.save(exceptedDeveloper);
        var actualLanguage = repository.findByEmail(exceptedDeveloper.getEmail());

        //THEN
        assertThat(actualLanguage,  is(Optional.of(exceptedDeveloper)));
    }

}
