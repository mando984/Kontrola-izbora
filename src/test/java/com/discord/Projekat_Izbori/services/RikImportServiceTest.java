package com.discord.Projekat_Izbori.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RikImportServiceTest {

    @Mock // Mockiramo ObjectMapper
    private ObjectMapper mockObjectMapper;

    @Mock // Mockiramo ResourceLoader
    private ResourceLoader mockResourceLoader;

    @Mock // Mockiramo Resource (koji će biti vraćen od ResourceLoader-a)
    private Resource mockResource;

    @InjectMocks // Injektuje mock-ovane zavisnosti u RikImportService
    private RikImportService rikImportService;

    // Fiksna putanja fajla za testiranje (možeš je menjati po potrebi testa)
    private final String TEST_FILE_PATH = "test-data/valid.json";


}