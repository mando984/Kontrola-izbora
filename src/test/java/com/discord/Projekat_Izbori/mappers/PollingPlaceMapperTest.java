package com.discord.Projekat_Izbori.mappers;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.models.PollingPlace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PollingPlaceMapperTest {

    private PollingPlaceMapper pollingPlaceMapper;

    @BeforeEach
    void setUp(){
        pollingPlaceMapper = new PollingPlaceMapper();
    }

    @Test
    void shouldMapAllFields(){
        //Arrange
        VotingRowDTO dto = new VotingRowDTO(4, "zapadnobacki", 1, "Sombor",
                2, "Miletic mz", 800,
                "Lemes","O", 803979, "Oktobarska");
        PollingPlace pollingPlace = pollingPlaceMapper.mapFrom(dto);
        assertEquals("Miletic mz", pollingPlace.getPollingPlaceName());
        assertEquals(800, pollingPlace.getOfficialRegisteredVoters());
        assertEquals(2, pollingPlace.getId());
        assertEquals("Oktobarska", pollingPlace.getStreetName());
        assertEquals(0, pollingPlace.getBallotCount());
        assertEquals(0, pollingPlace.getPollingStationPaperVoters());
    }

    // 2. Proveriti da li  mapper vraca greske za null vrednost polja name
    @Test
    void shouldHandelNullFieldPollingPlaceName(){
        //Arrange
        VotingRowDTO dto = new VotingRowDTO(4, "zapadnobacki", 1, "Sombor",
                2, null, 800,
                "Lemes","O", 803979, "Oktobarska");
        PollingPlace pollingPlace = pollingPlaceMapper.mapFrom(dto);

        assertNull(pollingPlace.getPollingPlaceName());

    }

    @Test
    void shouldHandleNullFieldOfficialRegisteredVoters() {
        VotingRowDTO dto = new VotingRowDTO(4, "zapadnobacki", 1, "Sombor",
                2, "Miletic mz", null,
                "Lemes", "O", 803979, "Oktobarska");
        PollingPlace pollingPlace = pollingPlaceMapper.mapFrom(dto);

        assertNull(pollingPlace.getOfficialRegisteredVoters());
    }

    @Test
    void sholdeHandleEmptyField(){
        VotingRowDTO dto = new VotingRowDTO(4, "zapadnobacki", 1, "Sombor",
                2, "", 800,
                "Lemes", "O", 803979, "");
        PollingPlace pollingPlace = pollingPlaceMapper.mapFrom(dto);

        Assertions.assertEquals("", pollingPlace.getPollingPlaceName());
        Assertions.assertEquals("", pollingPlace.getStreetName());
    }


}
