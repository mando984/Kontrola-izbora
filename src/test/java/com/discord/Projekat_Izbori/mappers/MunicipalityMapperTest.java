package com.discord.Projekat_Izbori.mappers;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.models.Municipality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class MunicipalityMapperTest {

    private MunicipalityMapper municipalityMapper;

    @BeforeEach
    void setUp(){
        municipalityMapper = new MunicipalityMapper();
    }

    // 1. Provera da li mapper vraca sve podatke ispravno
    @Test
    public void shouldMapAllFields(){
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                4, "zapadnobacki", 1, "Sombor",
                2, "Miletic mz", 800,
                "Lemes","O", 803979, "Oktobarska"
        );
        // ACT
         Municipality municipality = municipalityMapper.mapFrom(dto);

         //Assert
         assertEquals(1,municipality.getId());
         assertEquals("Sombor", municipality.getMunicipalityName());
         assertEquals(0, municipality.getTotalVotersByMunicipality());

    }



    // 2. Da li mapper vraca greske za null vrednost polja id
    @Test
    public void shouldHandleNullIdField(){
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                4, "zapadnobacki", null, "Sombor",
                2, "Miletic mz", 800,
                "Lemes","O", 803979, "Oktobarska"
        );
        // ACT
        Municipality municipality = municipalityMapper.mapFrom(dto);

        assertNull(municipality.getId());
    }



    // 3. Da li mapper vraca greske za null vrednost polja name
    @Test
    public void shouldHandleNullMunicipalityNameField(){
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                4, "zapadnobacki", 1, null,
                2, "Miletic mz", 800,
                "Lemes","O", 803979, "Oktobarska"
        );
        // ACT
        Municipality municipality = municipalityMapper.mapFrom(dto);

        assertNull(municipality.getMunicipalityName());
    }



    // 4. Da li vraca vrednost za inicijalizaciju polja broj glasaca
    @Test
    public void shouldSetNonNullTotalVotersByDefault(){
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                4, "zapadnobacki", 1, "Sombor",
                2, "Miletic mz", 800,
                "Lemes",null, 803979, "Oktobarska"
        );
        // ACT
        Municipality municipality = municipalityMapper.mapFrom(dto);

        assertNotNull(municipality.getTotalVotersByMunicipality());
    }


}