package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.exceptions.DataIntegrityException;
import com.discord.Projekat_Izbori.exceptions.ImportFileNotFoundException;
import com.discord.Projekat_Izbori.exceptions.InvalidJsonFormatException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;



@Service
public class RikImportService {

    private final ObjectMapper objectMapper;
    private final String filePath;
    private final ResourceLoader resourceLoader;

    public RikImportService(ObjectMapper objectMapper,
                            @Value("${rik.data.file.path}") String filePath,
                            ResourceLoader resourceLoader) {
        this.objectMapper = objectMapper;
        this.filePath = filePath;
        this.resourceLoader = resourceLoader;
    }

    public List<VotingRowDTO> importData() {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path for RIK data must not be null or blank.");
        }

        Resource resource = resourceLoader.getResource("classpath:" + filePath);
        if (!resource.exists()) {
            throw new ImportFileNotFoundException("File not found at path: " + filePath);
        }

        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new InvalidJsonFormatException("Failed to parse JSON from file: " + filePath, e);
        } catch (IOException e) {
            throw new DataIntegrityException("Unexpected IO error while reading the file.", e);
        }
    }
}
