package com.discord.Projekat_Izbori.dto.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class VotingRowDTO {

    @NotNull(message = "District ID cannot be null")
    @Min(value = 1, message = "District ID must be a positive number.")
    private Integer districtId;

    @NotBlank(message = "District name cannot be blank.")
    @Size(max = 100, message = "District name cannot exceed 100 characters.")
    private String districtName;

    @NotNull(message = "Munisipality ID cannor be null")
    @Min(value = 1, message = "Municipaliry ID must be a positive number")
    private Integer municipalityId;

    @NotBlank(message = "Municipality name cannot be blank.")
    @Size(max = 100, message = "Municipality name cannot exceed 100 characters.")
    private String municipalityName;

    @NotNull(message = "Polling place ID cannot be null.")
    @Min(value = 1, message = "Polling place ID must be a positive number.")
    private Integer pollingPlaceId;

    @NotBlank(message = "Polling place name cannot be blank.")
    @Size(max = 100, message = "Polling place name cannot exceed 100 characters.")
    private String pollingPlaceName;

    @NotNull(message = "Number of voters cannot be null.")
    @Min(value = 0, message = "Number of voters cannot be negative.")
    private Integer numberOfVoters;


    @NotBlank(message = "Settlement cannot be blank.")
    @Size(max = 100, message = "Settlement name cannot exceed 100 characters.")
    private String settlement;

    @NotBlank(message = "Street cannot be blank.")
    @Size(max = 200, message = "Street name cannot exceed 200 characters.")
    private String street;
}

