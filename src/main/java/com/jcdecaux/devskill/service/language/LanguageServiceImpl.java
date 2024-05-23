package com.jcdecaux.devskill.service.language;

import com.jcdecaux.devskill.dto.LanguageDto;
import com.jcdecaux.devskill.entity.Language;
import com.jcdecaux.devskill.exceptions.DevSkillException;
import com.jcdecaux.devskill.exceptions.DuplicateException;
import com.jcdecaux.devskill.exceptions.NotFoundException;
import com.jcdecaux.devskill.repository.LanguageRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final ModelMapper modelMapper;

    public LanguageServiceImpl(LanguageRepository languageRepository, ModelMapper modelMapper){
        this.languageRepository = languageRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public LanguageDto getById(Long id) {

        return languageRepository.findById(id)
                .map(lang -> modelMapper.map(lang, LanguageDto.class))
                .orElseThrow(() -> {
                    log.info("Language with id = {} doesn't exist", id);
                    return new NotFoundException(String.format("Language with id = %d doesn't exist", id));
                });
    }

    @Override
    public List<LanguageDto> getAll() {

        return languageRepository.findAll().stream()
                .map(language -> modelMapper.map(language, LanguageDto.class))
                .toList();
    }

    @Override
    public LanguageDto create(LanguageDto languageDto) {
        languageRepository.findByName(languageDto.getName())
                .ifPresent(language -> {
                    throw new DuplicateException(String.format("The language whose name is %s already exists", languageDto.getName()));
                });

        languageDto.setDateCreated(LocalDateTime.now());
        Language language = modelMapper.map(languageDto, Language.class);

        try {
            Language languageCreated = languageRepository.save(language);
            languageDto.setId(languageCreated.getId());

            return languageDto;
        }catch (Exception ex) {

            log.error("An error occurred when adding a language.", ex);
            throw new DevSkillException("An error occurred when adding a language.");
        }
    }

    @Override
    public LanguageDto update(Long id, LanguageDto languageDto) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Language with id = {} doesn't exist", id);
                    return new NotFoundException(String.format("Language with id = %d doesn't exist", id));
                });

        language.setName(languageDto.getName());

        try {
            languageRepository.save(language);
            languageDto.setId(id);

            return languageDto;
        }catch (Exception ex){
            log.error("An error occurred when modifying the language.", ex);
            throw new DevSkillException("An error occurred when modifying the language.");
        }

    }
}