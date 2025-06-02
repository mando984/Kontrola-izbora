package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.exceptions.DataIntegrityException;
import com.discord.Projekat_Izbori.exceptions.ImportFileNotFoundException;
import com.discord.Projekat_Izbori.exceptions.InvalidJsonFormatException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RikImportService {

    private final ObjectMapper objectMapper;
    private final String filePath;
    private final ResourceLoader resourceLoader;
    private final Validator validator;

    public RikImportService(ObjectMapper objectMapper,
                            @Value("${rik.data.file.path}") String filePath,
                            ResourceLoader resourceLoader, Validator validator) {
        this.objectMapper = objectMapper;
        this.filePath = filePath;
        this.resourceLoader = resourceLoader;
        this.validator = validator;
    }

    public List<VotingRowDTO> importData() {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path for RIK data must not be null or blank.");
        }

        Resource resource = resourceLoader.getResource("classpath:" + filePath);
        if (!resource.exists()) {
            throw new ImportFileNotFoundException("File not found at path: " + filePath);
        }

        List<VotingRowDTO> votingRowDTOS;
        try (InputStream inputStream = resource.getInputStream()) {
            // Faza 1: Parsiranje JSON-a u DTO objekte
            votingRowDTOS = objectMapper.readValue(inputStream, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            // Hvatanje grešaka PARSIRANJA JSON-a (npr. "abc" umesto broja, pogrešna sintaksa)
            throw new InvalidJsonFormatException("Failed to parse JSON from file: " + filePath, e);
        } catch (IOException e) {
            // Hvatanje drugih I/O grešaka (npr. disk err
            throw new DataIntegrityException("Unexpected IO error while reading the file.", e);
        }
        // --- FAZA 2: VALIDACIJA PARSIRANIH DTO OBJEKATA ---
        for (int i = 0; i < votingRowDTOS.size(); i++){
            VotingRowDTO dto = votingRowDTOS.get(i);
            // Pokrećemo validaciju DTO objekta pomoću injektovanog Validatora
            Set<ConstraintViolation<VotingRowDTO>> violations = validator.validate(dto);
            if(!violations.isEmpty()) {
                String vialationMessages = violations.stream()
                        .map(violation -> {
                            Object invalidValue = violation.getInvalidValue();
                            String invalidValueString = (invalidValue != null) ? invalidValue.toString() : "null";
                            return violation.getPropertyPath() + " :" + violation.getMessage() + "(Provided value: '" + invalidValueString + "')";
                        }).collect(Collectors.joining("; "));
                // Bacamo specifičan izuzetak koji sumira sve validacione probleme
                throw new DataIntegrityException("Validation failed for VotingRowDTO at index " + i + " from file: " + filePath + ". Details: " + vialationMessages
                , new Throwable());
            }
        }
        return votingRowDTOS;
    }
}
