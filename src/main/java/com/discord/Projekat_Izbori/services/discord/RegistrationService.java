package com.discord.Projekat_Izbori.services.discord;

import com.discord.Projekat_Izbori.dto.input.RegistrationRequestDto;
import com.discord.Projekat_Izbori.models.PendingRegistration;
import com.discord.Projekat_Izbori.repositories.PendingRegistrationRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RegistrationService {
    private final PendingRegistrationRepository pendingRegistrationRepository;
    private final Validator validator;

    public RegistrationService(PendingRegistrationRepository pendingRegistrationRepository, Validator validator) {
        this.pendingRegistrationRepository = pendingRegistrationRepository;
        this.validator = validator;
    }

    public boolean processRegistration(@Valid RegistrationRequestDto requestDto){

        // 1. Ručno pokreni validaciju DTO-a
        Set<ConstraintViolation<RegistrationRequestDto>> violations = validator.validate(requestDto);

        if (!violations.isEmpty()) {
            // Ako postoje greške validacije, baci ConstraintViolationException
            // Ovo će biti uhvaćeno u tvom RegistrationModalHandler-u
            throw new ConstraintViolationException(violations);
        }

        // Konvertovanje DTO u Entitet
        PendingRegistration pendingRegistration = new PendingRegistration();
        pendingRegistration.setFirstName(requestDto.getFirstName());
        pendingRegistration.setLastName(requestDto.getLastName());
        pendingRegistration.setPhoneNumber(requestDto.getPhoneNumber());
        pendingRegistration.setMunicipalityName(requestDto.getMunicipalityName());
        pendingRegistration.setSettlementName(requestDto.getSettlementName());
        pendingRegistration.setDiscordUserId(requestDto.getDiscordUserId());
        pendingRegistration.setDiscordUsername(requestDto.getDiscordUsername());


        // Čuvanje u privremenu tabelu (bazu)
        try {
            pendingRegistrationRepository.save(pendingRegistration);
            System.out.println("Podaci uspešno sačuvani u privremenu tabelu: " + pendingRegistration);
            return true;
        } catch (Exception e) {
            System.err.println("Greška prilikom čuvanja privremene registracije: " + e.getMessage());
            // Možeš logovati detaljnije grešku
            return false;
        }



    }
}
