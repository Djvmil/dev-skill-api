package com.jcdecaux.devskill.repository;

import com.jcdecaux.devskill.common.FAKE_DATA;
import com.jcdecaux.devskill.entity.Developer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.function.Executable;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeveloperRepositoryTest {

    @Autowired
    DeveloperRepository repository;

    @Test
    @Order(1)
    public void shouldSaveAndFetchDeveloper() {
        //GIVEN
        Developer exceptedDeveloper = FAKE_DATA.developers.getFirst();

        //WHEN
        repository.save(exceptedDeveloper);
        var actualDeveloper = repository.findByEmail(exceptedDeveloper.getEmail());

        //THEN
        assertThat(actualDeveloper, is(Optional.of(exceptedDeveloper)));
    }

    @Test
    @Order(2)
    public void shouldReturnExceptionIfNameDeveloperIsNull() {
        //GIVEN
        Developer exceptedDeveloper = FAKE_DATA.developers.getLast();
        exceptedDeveloper.setName(null);

        //WHEN
        Executable executable = () -> repository.save(exceptedDeveloper);

        //THEN
        assertThrows(DataIntegrityViolationException.class, executable, "NULL not allowed for Name column");
    }

    @Test
    @Order(3)
    public void shouldReturnExceptionIfEmailDeveloperIsNull() {
        //GIVEN
        Developer exceptedDeveloper = FAKE_DATA.developers.getLast();
        exceptedDeveloper.setEmail(null);

        //WHEN
        Executable executable = () -> repository.save(exceptedDeveloper);

        //THEN
        assertThrows(DataIntegrityViolationException.class, executable, "NULL not allowed for Email column");
    }

    @Test
    @Order(4)
    public void shouldReturnExceptionIfEmailDeveloperIsAlreadyExist() {
        //GIVEN
        Developer exceptedDeveloper = FAKE_DATA.developers.getLast();

        //WHEN
        Executable executable = () -> repository.save(exceptedDeveloper);

        //THEN
        assertThrows(DataIntegrityViolationException.class, executable, "The email address already exists");
    }

}
