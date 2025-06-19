package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.exceptions.InvalidJsonFormatException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class) // Obavezno za Mockito
class RikImportServiceTest {

    @Mock // Mockiramo ObjectMapper, jer ne želimo da testiramo Jackson, već RikImportService
    private ObjectMapper objectMapper;

    @Mock // Mockiramo ResourceLoader
    private ResourceLoader resourceLoader;

    @Mock // Mockiramo Validator
    private Validator validator;

    // Injektujemo mockove u RikImportService
    @InjectMocks
    private RikImportService rikImportService;

    // Dummy putanja za filePath (koja se sada ne koristi aktivno za učitavanje)
    private static final String DUMMY_FILE_PATH = "test-data/dummy.json";

    @BeforeEach
    void setUp() {
        // Kada inicijalizujemo RikImportService za testove, setujemo ga sa dummy putanjom.
        // Bitno je da ovaj konstruktor prima sve zavisnosti koje su mu potrebne.
        // Stvarna vrednost filePath neće uticati na testove gde sami obezbeđujemo InputStream.
        rikImportService = new RikImportService(objectMapper, DUMMY_FILE_PATH, resourceLoader, validator);
    }


    @Test
    void shouldImportValidDataSuccessfully() throws IOException {
        // ARRANGE
        String validJson = "[{\"districtId\":1,\"districtName\":\"Test\",\"municipalityId\":1,\"municipalityName\":\"Test\",\"pollingPlaceId\":1,\"pollingPlaceName\":\"Test\",\"numberOfVoters\":100,\"settlementName\":\"Test\",\"settlementType\":\"G\",\"settlementId\":1,\"streetName\":\"Test\"}]";
        InputStream inputStream = new ByteArrayInputStream(validJson.getBytes());

        // Mock ponašanja ResourceLoader-a
        Resource mockResource = mock(Resource.class);
        when(resourceLoader.getResource("classpath:" + DUMMY_FILE_PATH)).thenReturn(mockResource);
        when(mockResource.exists()).thenReturn(true);
        when(mockResource.getInputStream()).thenReturn(inputStream); // Vraćamo naš InputStream

        // Mock ponašanja ObjectMapper-a da vrati listu DTO-a
        List<VotingRowDTO> expectedDtos = List.of(new VotingRowDTO()); // Stvarni DTO objekti bi ovde trebalo da se kreiraju
        when(objectMapper.readValue(any(InputStream.class), any(com.fasterxml.jackson.core.type.TypeReference.class))).thenReturn(expectedDtos);

        // Mock ponašanja Validatora da ne pronađe nikakve greške
        when(validator.validate(any(VotingRowDTO.class))).thenReturn(Collections.emptySet());

        // ACT
        List<VotingRowDTO> result = rikImportService.importData();

        // ASSERT
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expectedDtos.size(), result.size());
        verify(objectMapper, times(1)).readValue(any(InputStream.class), any(com.fasterxml.jackson.core.type.TypeReference.class));
        verify(validator, times(expectedDtos.size())).validate(any(VotingRowDTO.class)); // Validator pozvan za svaki DTO
    }

    @Test
    void shouldThrowInvalidJsonFormatExceptionWhenParsingFails() throws IOException {
        // ARRANGE
        String invalidJson = "{\"districtId\":\"abc\"}"; // Neispravan JSON format
        InputStream inputStream = new ByteArrayInputStream(invalidJson.getBytes());

        Resource mockResource = mock(Resource.class);
        when(resourceLoader.getResource("classpath:" + DUMMY_FILE_PATH)).thenReturn(mockResource);
        when(mockResource.exists()).thenReturn(true);
        when(mockResource.getInputStream()).thenReturn(inputStream);

        // Mock ObjectMapper-a da baci JsonProcessingException
        when(objectMapper.readValue(any(InputStream.class), any(com.fasterxml.jackson.core.type.TypeReference.class)))
                .thenThrow(new com.fasterxml.jackson.core.JsonParseException(null, "Invalid JSON"));

        // ACT & ASSERT
        InvalidJsonFormatException thrown = assertThrows(
                InvalidJsonFormatException.class,
                () -> rikImportService.importData(),
                "Trebalo je da baci InvalidJsonFormatException zbog greške parsiranja"
        );

        assertTrue(thrown.getMessage().contains("Failed to parse JSON"));
        assertTrue(thrown.getCause() instanceof com.fasterxml.jackson.core.JsonParseException);
    }

    //  test za ImportFileNotFoundException (kada resource.exists() vrati false)
    @Test
    void shouldThrowImportFileNotFoundExceptionWhenResourceDoesNotExist() throws IOException {
        // ARRANGE
        Resource mockResource = mock(Resource.class);
        when(resourceLoader.getResource("classpath:" + DUMMY_FILE_PATH)).thenReturn(mockResource);
        when(mockResource.exists()).thenReturn(false); // Fajl ne postoji

        // ACT & ASSERT
        assertThrows(
                com.discord.Projekat_Izbori.exceptions.ImportFileNotFoundException.class,
                () -> rikImportService.importData(),
                "Trebalo je da baci ImportFileNotFoundException kada fajl ne postoji"
        );
    }
}