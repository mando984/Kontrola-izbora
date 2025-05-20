package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.dto.input.VotingRawDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;



@Service
public class RikImportService {

    private final ObjectMapper objectMapper;
    private final String filePath;


    public RikImportService(ObjectMapper objectMapper,
                            @Value("${rik.data.file.path}") String filePath) {
        this.objectMapper = objectMapper;
        this.filePath = filePath;
    }


    public List<VotingRawDTO> importDate() throws IOException {
        ClassPathResource resource = new ClassPathResource(filePath);
        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, new TypeReference<List<VotingRawDTO>>() {});
        }
    }









}
