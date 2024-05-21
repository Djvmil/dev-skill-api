package com.jcdecaux.devskill.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanguageDto {
    private Long id;

    @NotBlank(message = "The name is required.")
    private String name;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated = LocalDateTime.now();
}