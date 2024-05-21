package com.jcdecaux.devskill.api;

import com.jcdecaux.devskill.dto.AddLanguageDto;
import com.jcdecaux.devskill.dto.DeveloperDto;
import com.jcdecaux.devskill.exceptions.RequiredException;
import com.jcdecaux.devskill.service.developer.DeveloperService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/developers")
public class DeveloperRessource {

    @Autowired
    private DeveloperService developerService;

    @GetMapping
    public List<DeveloperDto> getAll() {
        return developerService.getAll();
    }

    @GetMapping("/{id}")
    public DeveloperDto getById(@PathVariable @Positive Long id) {
        return developerService.getById(id);
    }

    @PostMapping
    public DeveloperDto create(@Valid @RequestBody DeveloperDto dto) {

        if (dto.getName() == null || dto.getName().isBlank())
            throw new RequiredException("Developer name is required");

        if (dto.getEmail() == null || dto.getEmail().isBlank())
            throw new RequiredException("Developer email is required");

        return developerService.create(dto);
    }

    @PutMapping("/{id}")
    public DeveloperDto update(@PathVariable @Positive Long id, @Valid @RequestBody DeveloperDto dto) {
        return developerService.update(id, dto);
    }

    @PostMapping("/add-language")
    public DeveloperDto addLanguageToDeveloper(@Valid @RequestBody AddLanguageDto dto) {

        return developerService.addLanguageToDeveloper(dto);
    }

    @GetMapping("/by-language/{languageName}")
    public List<DeveloperDto> findDevelopersByLanguage(@PathVariable String languageName) {
        return developerService.findDevelopersByLanguage(languageName);
    }
}