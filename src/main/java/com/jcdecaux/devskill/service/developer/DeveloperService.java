package com.jcdecaux.devskill.service.developer;

import com.jcdecaux.devskill.dto.AddLanguageDto;
import com.jcdecaux.devskill.dto.DeveloperDto;

import java.util.List;

public interface DeveloperService {
    DeveloperDto getById(Long id);
    List<DeveloperDto> getAll();
    DeveloperDto create(DeveloperDto developerDto);
    DeveloperDto update(Long id, DeveloperDto developerDto);
    DeveloperDto addLanguageToDeveloper(AddLanguageDto request);
    List<DeveloperDto> findDevelopersByLanguage(String languageName);
}