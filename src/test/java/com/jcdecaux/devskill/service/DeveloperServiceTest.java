package com.jcdecaux.devskill.service;

import com.jcdecaux.devskill.common.FAKE_DATA;
import com.jcdecaux.devskill.dto.DeveloperDto;
import com.jcdecaux.devskill.entity.Developer;
import com.jcdecaux.devskill.exceptions.DuplicateException;
import com.jcdecaux.devskill.exceptions.NotFoundException;
import com.jcdecaux.devskill.repository.DeveloperRepository;
import com.jcdecaux.devskill.service.developer.DeveloperServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeveloperServiceTest {

    @Spy
    ModelMapper modelMapper = new ModelMapper();

    @Mock
    private DeveloperRepository repository;

    @InjectMocks
    private DeveloperServiceImpl service;

    @BeforeAll
    public static void setup(){
        MockitoAnnotations.openMocks(DeveloperServiceTest.class);
    }

    @Test
    public void shouldSaveAndFetchDeveloper() {
        //GIVEN
        Developer exceptedDeveloper = FAKE_DATA.developers.getFirst();
        DeveloperDto exceptedDeveloperDto = modelMapper.map(exceptedDeveloper, DeveloperDto.class);

        when(repository.save(Mockito.any(Developer.class))).thenReturn(exceptedDeveloper);
        when(repository.findByEmail(exceptedDeveloper.getEmail())).thenReturn(Optional.empty());
        when(repository.findById(exceptedDeveloper.getId())).thenReturn(Optional.of(exceptedDeveloper));

        //WHEN
        service.create(exceptedDeveloperDto);
        DeveloperDto actualDeveloperDto = service.getById(exceptedDeveloper.getId());

        //THEN
        assertThat(actualDeveloperDto.getId(),  is(exceptedDeveloperDto.getId()));
        assertThat(actualDeveloperDto.getName(),  is(exceptedDeveloperDto.getName()));
    }

    @Test
    public void shouldReturnExceptionIfEmailDeveloperAlreadyExist() {
        //GIVEN
        Developer exceptedDeveloper = FAKE_DATA.developers.getFirst();
        DeveloperDto exceptedDeveloperDto = modelMapper.map(exceptedDeveloper, DeveloperDto.class);

        when(repository.findByEmail(exceptedDeveloper.getEmail())).thenReturn(Optional.of(exceptedDeveloper));

        //WHEN
        Executable executable = () -> service.create(exceptedDeveloperDto);

        //THEN
        assertThrows(DuplicateException.class, executable, "The email developer already exists");
    }

    @Test
    public void shouldReturnExceptionWhenUpdateIfDeveloperIdNotExist() {
        //GIVEN
        Developer exceptedDeveloper = FAKE_DATA.developers.getFirst();
        DeveloperDto exceptedDeveloperDto = modelMapper.map(exceptedDeveloper, DeveloperDto.class);

        when(repository.findById(exceptedDeveloper.getId())).thenReturn(Optional.empty());

        //WHEN
        Executable executable = () -> service.update(exceptedDeveloper.getId(), exceptedDeveloperDto);

        //THEN
        assertThrows(NotFoundException.class, executable, "Developer with this id doesn't exist");
    }

    @Test
    public void shouldUpdateAndFetchDeveloper() {
        //GIVEN
        Developer exceptedDeveloper = FAKE_DATA.developers.getFirst();
        Developer exceptedDeveloperUpdated = exceptedDeveloper.toBuilder().name("Robert C Martin").build();
        DeveloperDto exceptedDeveloperDto = modelMapper.map(exceptedDeveloper, DeveloperDto.class);

        when(repository.findById(exceptedDeveloper.getId())).thenReturn(Optional.of(exceptedDeveloper));
        when(repository.save(Mockito.any(Developer.class))).thenReturn(exceptedDeveloperUpdated);

        //WHEN
        service.create(exceptedDeveloperDto);
        DeveloperDto actualDeveloperDto  = service.update(exceptedDeveloperDto.getId(), exceptedDeveloperDto);

        //THEN
        assertThat(actualDeveloperDto.getId(),  is(exceptedDeveloperDto.getId()));
        assertThat(actualDeveloperDto.getName(),  is(exceptedDeveloperDto.getName()));
    }

}
