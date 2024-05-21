package com.jcdecaux.devskill.dto;

import com.jcdecaux.devskill.entity.Language;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperDto {
    private Long id;

    @NotBlank(message = "The name is required.")
    private String name;

    @NotBlank(message = "The email address is required.")
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;

    private List<Language> languages;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated = LocalDateTime.now();
}