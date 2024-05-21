package com.jcdecaux.devskill.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddLanguageDto {
    @NotNull(message = "The developer Id is required.")
    @Positive(message = "The developer Id must be greater than 0")
    private Long developerId;

    @NotNull(message = "The language Id is required.")
    @Positive(message = "The language Id must be greater than 0")
    private Long languageId;
}