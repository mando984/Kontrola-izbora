package com.discord.Projekat_Izbori.mappers;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.models.District;
import com.discord.Projekat_Izbori.models.Municipality;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MunicipalityMapperValidationTest {

    private District district;
    private MunicipalityMapper municipalityMapper;
    private List<Municipality> municipalities;
    private Validator validator;

    @BeforeEach
    void setUp() {
        municipalities = new ArrayList<>();
        district = new District(5, "zapadnobacki", 70000, municipalities);
        municipalityMapper = new MunicipalityMapper();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldMapDtoToMunicipalityCorrectly() {
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                5, "zapadnobacki", 80381, "Sombor", 2,
                "Miletic mz", 800, "Lemes", "Oktobarska"
        );

        // ACT
        Municipality result = municipalityMapper.mapFrom(dto);

        // ASSERT
        Municipality expected = new Municipality(80381, "Sombor", 0, district, null);
        assertEquals(expected.getMunicipalityName(), result.getMunicipalityName());
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getTotalVotersByMunicipality(), result.getTotalVotersByMunicipality());
    }

    @Test
    void shouldReturnNullWhenMunicipalityIdIsNull() {
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                5, "zapadnobacki", null, "Sombor", 2,
                "Miletic mz", 800, "Lemes", "Oktobarska"
        );

        // ACT
        Municipality result = municipalityMapper.mapFrom(dto);

        // ASSERT
        assertNull(result.getId());
    }

    @Test
    void shouldFailValidationWhenMunicipalityIdIsNegative() {
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                5, "zapadnobacki", -5, "Sombor", 2,
                "Miletic mz", 800, "Lemes", "Oktobarska"
        );

        // ACT
        Set<ConstraintViolation<VotingRowDTO>> violations = validator.validate(dto);

        // ASSERT
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().equals("municipalityId")
                        && v.getMessage().toLowerCase().contains("positive")
        ));
    }

    @Test
    void shouldAcceptEmptyMunicipalityName() {
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                5, "zapadnobacki", 80381, "", 2,
                "Miletic mz", 800, "Lemes", "Oktobarska"
        );

        // ACT
        Municipality result = municipalityMapper.mapFrom(dto);

        // ASSERT
        assertTrue(StringUtils.isBlank(result.getMunicipalityName()));
    }

    @Test
    void shouldReturnNullWhenMunicipalityNameIsNull() {
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                5, "zapadnobacki", 80381, null, 2,
                "Miletic mz", 800, "Lemes", "Oktobarska"
        );

        // ACT
        Municipality result = municipalityMapper.mapFrom(dto);

        // ASSERT
        assertNull(result.getMunicipalityName());
    }
}
