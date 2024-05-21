package com.jcdecaux.devskill.service.language;

import com.jcdecaux.devskill.dto.LanguageDto;

import java.util.List;

public interface LanguageService {
    LanguageDto getById(Long id);
    List<LanguageDto> getAll();
    LanguageDto create(LanguageDto language);
    LanguageDto update(Long id, LanguageDto language);
}