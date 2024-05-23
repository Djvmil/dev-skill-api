package com.jcdecaux.devskill.api;

import com.jcdecaux.devskill.dto.LanguageDto;
import com.jcdecaux.devskill.service.language.LanguageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageRessource {

    private final LanguageService languageService;

    public LanguageRessource(LanguageService languageService){
        this.languageService = languageService;
    }

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<LanguageDto> getAll() {
        return languageService.getAll();
    }

    @GetMapping("/{id}")
    public LanguageDto getById(@PathVariable @Positive Long id) {
        return languageService.getById(id);
    }

    @PostMapping
    public LanguageDto create(@Valid @RequestBody LanguageDto dto) {
        return languageService.create(dto);
    }

    @PutMapping("/{id}")
    public LanguageDto update(@PathVariable @Positive Long id, @Valid @RequestBody  LanguageDto dto) {
        return languageService.update(id, dto);
    }
}
