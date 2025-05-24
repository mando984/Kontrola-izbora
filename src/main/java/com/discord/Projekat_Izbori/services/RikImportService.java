package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.exceptions.DataIntegrityException;
import com.discord.Projekat_Izbori.exceptions.InvalidJsonFormatException;
import com.discord.Projekat_Izbori.exceptions.MissingRequiredFieldException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Service
public class RikImportService {

    private final ObjectMapper objectMapper;
    private final String filePath;

    public RikImportService(ObjectMapper objectMapper,
                            @Value("${rik.data.file.path}") String filePath) {
        this.objectMapper = objectMapper;
        this.filePath = filePath;
    }

    public List<VotingRowDTO> importData() {
        ClassPathResource resource = new ClassPathResource(filePath);
        List<VotingRowDTO> votingRows;

        try (InputStream inputStream = resource.getInputStream()) {
            votingRows = objectMapper.readValue(inputStream, new TypeReference<List<VotingRowDTO>>() {});
        } catch (FileNotFoundException e) {
            throw new InvalidJsonFormatException("JSON file not found at path: " + filePath, e);
        } catch (JsonProcessingException e) {
            throw new InvalidJsonFormatException("Error parsing JSON file: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new InvalidJsonFormatException("Error reading JSON file: " + e.getMessage(), e);
        }
        validateVotingRows(votingRows);

        return votingRows;
    }

    private void validateVotingRows(List<VotingRowDTO> votingRows) {
        if (votingRows == null || votingRows.isEmpty()) {
            throw new DataIntegrityException("JSON file contains no voting rows or is empty.");
        }

        for (int i = 0; i < votingRows.size(); i++) {
            VotingRowDTO row = votingRows.get(i);
            // Provera obaveznih polja
            // Provera obaveznih polja (numberOfVoters)
            if (Objects.isNull(row.getNumberOfVoters())) {
                throw new MissingRequiredFieldException("Missing numberOfVoters in row " + i);
            }
            // Dodatna provera za numberOfVoters (ne sme biti negativan)
            if (row.getNumberOfVoters() < 0) { // <--- OVO BI TREBALO DA BACA IZUZETAK
                throw new DataIntegrityException("Negative numberOfVoters in row " + i);
            }

            if (Objects.isNull(row.getDistrictId())) {
                throw new MissingRequiredFieldException("Missing districtId in row " + i);
            }
            if (Objects.isNull(row.getDistrictName()) || row.getDistrictName().trim().isEmpty()) {
                throw new MissingRequiredFieldException("Missing or empty districtName in row " + i);
            }
            if (Objects.isNull(row.getMunicipalityId())) {
                throw new MissingRequiredFieldException("Missing municipalityId in row " + i);
            }
            if (Objects.isNull(row.getMunicipalityName()) || row.getMunicipalityName().trim().isEmpty()) {
                throw new MissingRequiredFieldException("Missing or empty municipalityName in row " + i);
            }
            if (Objects.isNull(row.getPollingPlaceId())) {
                throw new MissingRequiredFieldException("Missing pollingPlaceId in row " + i);
            }
            if (Objects.isNull(row.getPollingPlaceName()) || row.getPollingPlaceName().trim().isEmpty()) {
                throw new MissingRequiredFieldException("Missing or empty pollingPlaceName in row " + i);
            }
            // Ovo je tvoj test case: numberOfVoters ne sme biti null
            if (Objects.isNull(row.getNumberOfVoters())) {
                throw new MissingRequiredFieldException("Missing numberOfVoters in row " + i);
            }
            // Dodatna provera za numberOfVoters (ne sme biti negativan)
            if (row.getNumberOfVoters() < 0) {
                throw new DataIntegrityException("Negative numberOfVoters in row " + i);
            }
            if (Objects.isNull(row.getSettlement()) || row.getSettlement().trim().isEmpty()) {
                throw new MissingRequiredFieldException("Missing or empty settlement in row " + i);
            }
            if (Objects.isNull(row.getStreet()) || row.getStreet().trim().isEmpty()) {
                throw new MissingRequiredFieldException("Missing or empty street in row " + i);
            }

            // Možeš dodati i druge logičke provere ovde ako su ID-evi u određenim opsezima itd.
        }
    }
}