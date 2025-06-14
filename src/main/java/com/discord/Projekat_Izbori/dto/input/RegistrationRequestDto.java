package com.discord.Projekat_Izbori.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequestDto {

    @NotBlank(message = "Ime ne sme biti prazno.")
    @Size(max = 50, message = "Ime ne sme preći 50 karaktera.")
    //regex za proveru da sadrži samo slova, razmake i crtice
    @Pattern(regexp = "^[\\p{L}\\s\\-]+$", message = "Ime sme sadržati samo slova, razmake i crtice.")
    private String firstName;

    @NotBlank(message = "Prezime ne sme biti prazno.")
    @Size(max = 50, message = "Prezime ne sme preći 50 karaktera.")
    @Pattern(regexp = "^[\\p{L}\\s\\-]+$", message = "Prezime sme sadržati samo slova, razmake i crtice.")
    private String lastName;

    @NotBlank(message = "Broj telefona ne sme biti prazan.")
    @Size(min = 7, max = 15, message = "Broj telefona mora imati između 7 i 15 karaktera.")
    // Primer regexa: dozvoljava cifre, razmake, +, -
    @Pattern(regexp = "^[\\d\\s\\+\\-]+$", message = "Broj telefona sme sadržati samo cifre, razmake, '+' i '-'.")
    private String phoneNumber;

    @NotBlank(message = "Naziv opštine ne sme biti prazan.")
    @Size(max = 100, message = "Naziv opštine ne sme preći 100 karaktera.")
    @Pattern(regexp = "^[\\p{L}\\s\\-]+$", message = "Naziv opštine sme sadržati samo slova, razmake i crtice.")
    private String municipalityName;

    @NotBlank(message = "Naziv naselja ne sme biti prazan.")
    @Size(max = 100, message = "Naziv naselja ne sme preći 100 karaktera.")
    @Pattern(regexp = "^[\\p{L}\\s\\-]+$", message = "Naziv naselja sme sadržati samo slova, razmake i crtice.")
    private String settlementName;
    private String discordUserId; // Dodaj i Discord User ID za dalju obradu
    private String discordUsername; // I username
}
