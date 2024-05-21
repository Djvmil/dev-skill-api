package com.jcdecaux.devskill.service.developer;

import com.jcdecaux.devskill.dto.AddLanguageDto;
import com.jcdecaux.devskill.dto.DeveloperDto;
import com.jcdecaux.devskill.entity.Developer;
import com.jcdecaux.devskill.entity.Language;
import com.jcdecaux.devskill.exceptions.DevSkillException;
import com.jcdecaux.devskill.exceptions.DuplicateException;
import com.jcdecaux.devskill.exceptions.NotFoundException;
import com.jcdecaux.devskill.repository.DeveloperRepository;
import com.jcdecaux.devskill.repository.LanguageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DeveloperDto getById(Long id) {

        return developerRepository.findById(id)
                .map(lang -> modelMapper.map(lang, DeveloperDto.class))
                .orElseThrow(() -> new NotFoundException(String.format("Developer with id = %d doesn't exist", id)));
    }

    @Override
    public List<DeveloperDto> getAll() {

        return developerRepository.findAll()
                .stream()
                .map(developer -> modelMapper.map(developer, DeveloperDto.class))
                .toList();
    }

    @Override
    public DeveloperDto create(DeveloperDto developerDto) {
        if (developerRepository.findByEmail(developerDto.getEmail()).isPresent()) {
            throw new DuplicateException(String.format("The developer whose email is %s already exists", developerDto.getEmail()));
        }

        developerDto.setDateCreated(LocalDateTime.now());
        Developer developer = modelMapper.map(developerDto, Developer.class);

        try {
            Developer developerCreated = developerRepository.save(developer);
            developerDto.setId(developerCreated.getId());
            return developerDto;

        }catch (Exception ex){
            throw new DevSkillException("An error occurred when creating the developer");
        }

    }

    @Override
    public DeveloperDto update(Long id, DeveloperDto developerDto) {
        Developer developerItem = developerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Developer with id = %d doesn't exist", developerDto.getId())));

        developerItem.setName(developerDto.getName());
        developerItem.setEmail(developerDto.getEmail());

        try {
            developerRepository.save(developerItem);
            developerDto.setId(id);

            return developerDto;
        }catch (Exception ex){
            throw new DevSkillException("An error occurred when modifying the developer");
        }
    }

    @Override
    public DeveloperDto addLanguageToDeveloper(AddLanguageDto request) {

        Developer developer = developerRepository
                .findById(request.getDeveloperId())
                .orElseThrow(() -> new NotFoundException(String.format("Developer with id = %d doesn't exist", request.getDeveloperId())));

        Language language = languageRepository
                .findById(request.getDeveloperId())
                .orElseThrow(() -> new NotFoundException(String.format("Language with id = %d doesn't exist", request.getLanguageId())));

        if (developer.getLanguages().contains(language)) {
            throw new DuplicateException("The developer already has the language in these skills");
        }

        developer.getLanguages().add(language);

        try {
            developer.getLanguages().add(language);
            Developer developerSaved = developerRepository.save(developer);

            return modelMapper.map(developerSaved, DeveloperDto.class);
        }catch (Exception ex){
            throw new DevSkillException("An error occurred when adding a language to the developer.");
        }
    }

    @Override
    public List<DeveloperDto> findDevelopersByLanguage(String languageName) {
        return developerRepository.findAllByLanguages_Name(languageName).stream()
                .map(developer -> modelMapper.map(developer, DeveloperDto.class))
                .collect(Collectors.toList());
    }
}