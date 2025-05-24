package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.exceptions.DataIntegrityException;
import com.discord.Projekat_Izbori.exceptions.InvalidJsonFormatException;
import com.discord.Projekat_Izbori.exceptions.MissingRequiredFieldException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource; // Potrebno za pristup resursima

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RikImportServiceTest {

    private ObjectMapper objectMapper;

    // Primer validnog DTO objekta za poređenje u testovima
    private final VotingRowDTO VALID_DTO_EXAMPLE = new VotingRowDTO(
            5, "Zapadnobački okrug", 80381, "Sombor",
            1, "OŠ NIKOLA VUKIČEVIĆ  UČIONICA BROJ 7",
            1239, "SOMBOR", "VELjKA PETROVIĆA BR 4"
    );

    @BeforeEach
    void setUp() {
        // Koristimo pravi ObjectMapper za testiranje stvarne logike parsiranja
        objectMapper = new ObjectMapper();
    }

    @Test
    void testValidJsonFile() {
        // Instanciramo servis sa putanjom do validnog fajla
        RikImportService rikImportService = new RikImportService(objectMapper, "test-data/valid.json");

        List<VotingRowDTO> actual = rikImportService.importData();

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(VALID_DTO_EXAMPLE.getPollingPlaceName(), actual.get(0).getPollingPlaceName());
        assertEquals(VALID_DTO_EXAMPLE.getNumberOfVoters(), actual.get(0).getNumberOfVoters());
    }

    @Test
    void testEmptyJsonFile() {
        // Instanciramo servis sa putanjom do fajla sa praznom listom []
        RikImportService rikImportService = new RikImportService(objectMapper, "test-data/empty.json");

        // Očekujemo DataIntegrityException jer je lista prazna
        DataIntegrityException thrown = assertThrows(DataIntegrityException.class, () -> {
            rikImportService.importData();
        }, "Expected DataIntegrityException for empty JSON list, but didn't get it.");

        assertTrue(thrown.getMessage().contains("JSON file contains no voting rows or is empty."));
    }

    @Test
    void testJsonWithMissingRequiredField() {
        // Instanciramo servis sa putanjom do fajla gde numberOfVoters ima null vrednost
        RikImportService rikImportService = new RikImportService(objectMapper, "test-data/empty_field.json");

        // Očekujemo MissingRequiredFieldException
        MissingRequiredFieldException thrown = assertThrows(MissingRequiredFieldException.class, () -> {
            rikImportService.importData();
        }, "Expected MissingRequiredFieldException for null numberOfVoters, but didn't get it.");

        assertTrue(thrown.getMessage().contains("Missing numberOfVoters in row 0"));
    }

    @Test
    void testJsonWithNegativeNumberOfVoters() throws IOException {
        // Potrebno je da kreiraš "negative_voters.json" u src/test/resources/test-data
        // Sadržaj: [{"districtId":5, "numberOfVoters":-10, ...}]
        RikImportService rikImportService = new RikImportService(objectMapper, "test-data/negative_voters.json");

        // Očekujemo DataIntegrityException za negativan broj glasača
        DataIntegrityException thrown = assertThrows(DataIntegrityException.class, () -> {
            rikImportService.importData();
        }, "Expected DataIntegrityException for negative numberOfVoters, but didn't get it.");

        assertTrue(thrown.getMessage().contains("Negative numberOfVoters in row 0"));
    }

    @Test
    void testInvalidJsonFormat() throws IOException {
        // Potrebno je da kreiraš "invalid_format.json" u src/test/resources/test-data
        // Sadržaj: { "districtId": 5, "districtName": "Invalid JSON here..." (malformiran JSON)
        RikImportService rikImportService = new RikImportService(objectMapper, "test-data/invalid_format.json");

        // Očekujemo InvalidJsonFormatException
        InvalidJsonFormatException thrown = assertThrows(InvalidJsonFormatException.class, () -> {
            rikImportService.importData();
        }, "Expected InvalidJsonFormatException for malformed JSON, but didn't get it.");

        assertTrue(thrown.getMessage().contains("Error parsing JSON file"));
    }

    @Test
    void testFileNotFound() {
        // Namerno koristimo putanju do fajla koji ne postoji
        RikImportService rikImportService = new RikImportService(objectMapper, "test-data/non-existent-file.json");

        // Očekujemo InvalidJsonFormatException jer fajl nije pronađen
        InvalidJsonFormatException thrown = assertThrows(InvalidJsonFormatException.class, () -> {
            rikImportService.importData();
        }, "Expected InvalidJsonFormatException for file not found, but didn't get it.");

        assertTrue(thrown.getMessage().contains("JSON file not found at path: test-data/non-existent-file.json"));
    }
}