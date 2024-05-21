package com.jcdecaux.devskill.service;

import com.jcdecaux.devskill.common.FAKE_DATA;
import com.jcdecaux.devskill.dto.LanguageDto;
import com.jcdecaux.devskill.entity.Language;
import com.jcdecaux.devskill.exceptions.DuplicateException;
import com.jcdecaux.devskill.exceptions.NotFoundException;
import com.jcdecaux.devskill.repository.LanguageRepository;
import com.jcdecaux.devskill.service.language.LanguageServiceImpl;
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
public class LanguageServiceTest {

    @Spy
    ModelMapper modelMapper = new ModelMapper();

    @Mock
    private LanguageRepository repository;

    @InjectMocks
    private LanguageServiceImpl service;

    @BeforeAll
    public static void setup(){
        MockitoAnnotations.openMocks(LanguageServiceTest.class);
    }

    @Test
    public void shouldSaveAndFetchLanguage() {
        //GIVEN
        Language exceptedLanguage = FAKE_DATA.languages.getFirst();
        LanguageDto exceptedLanguageDto = modelMapper.map(exceptedLanguage, LanguageDto.class);

        when(repository.save(Mockito.any(Language.class))).thenReturn(exceptedLanguage);
        when(repository.findByName(exceptedLanguage.getName())).thenReturn(Optional.empty());
        when(repository.findById(exceptedLanguage.getId())).thenReturn(Optional.of(exceptedLanguage));

        //WHEN
        service.create(exceptedLanguageDto);
        LanguageDto actualLanguageDto = service.getById(exceptedLanguage.getId());

        //THEN
        assertThat(actualLanguageDto.getId(),  is(exceptedLanguageDto.getId()));
        assertThat(actualLanguageDto.getName(),  is(exceptedLanguageDto.getName()));
    }

    @Test
    public void shouldReturnExceptionIfNameLanguageAlreadyExist() {
        //GIVEN
        Language exceptedLanguage = FAKE_DATA.languages.getFirst();
        LanguageDto exceptedLanguageDto = modelMapper.map(exceptedLanguage, LanguageDto.class);

        when(repository.findByName(exceptedLanguage.getName())).thenReturn(Optional.of(exceptedLanguage));

        //WHEN
        Executable executable = () -> {
            service.create(exceptedLanguageDto);
        };

        //THEN
        assertThrows(DuplicateException.class, executable, "The name language already exists");
    }

    @Test
    public void shouldReturnExceptionWhenUpdateIfLanguageIdNotExist() {
        //GIVEN
        Language exceptedLanguage = FAKE_DATA.languages.getFirst();
        LanguageDto exceptedLanguageDto = modelMapper.map(exceptedLanguage, LanguageDto.class);

        when(repository.findById(exceptedLanguage.getId())).thenReturn(Optional.empty());

        //WHEN
        Executable executable = () -> service.update(exceptedLanguage.getId(), exceptedLanguageDto);

        //THEN
        assertThrows(NotFoundException.class, executable, "Language with this id doesn't exist");
    }

    @Test
    public void shouldUpdateAndFetchLanguage() {
        //GIVEN
        Language exceptedLanguage = FAKE_DATA.languages.getFirst();
        Language exceptedLanguageUpdated = FAKE_DATA.languages.getFirst();
        exceptedLanguageUpdated.setName("Robert C Martin");
        LanguageDto exceptedLanguageDto = modelMapper.map(exceptedLanguage, LanguageDto.class);

        when(repository.findById(exceptedLanguage.getId())).thenReturn(Optional.of(exceptedLanguage));
        when(repository.save(Mockito.any(Language.class))).thenReturn(exceptedLanguageUpdated);

        //WHEN
        service.update(exceptedLanguageDto.getId(), exceptedLanguageDto);
        LanguageDto actualLanguageDto  = service.create(exceptedLanguageDto);

        //THEN
        assertThat(actualLanguageDto.getId(),  is(exceptedLanguageDto.getId()));
        assertThat(actualLanguageDto.getName(),  is(exceptedLanguageDto.getName()));
    }

}
