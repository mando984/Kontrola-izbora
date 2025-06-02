package com.discord.Projekat_Izbori.mappers;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.models.Settlement;
import com.discord.Projekat_Izbori.models.SettlementType;
import org.junit.jupiter.api.Test;

import static com.discord.Projekat_Izbori.mappers.SettlementMapper.mapFrom;
import static org.junit.jupiter.api.Assertions.*;

class SettlementMapperTest {



    // 1. Proveriti da li vraca sve podatke ispravno
    @Test
    void shouldMapAllFields(){
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                4, "zapadnobacki", 1, "Sombor",
                2, "Miletic mz", 800,
                "Lemes","O", 803979, "Oktobarska"
        );

        Settlement settlement = mapFrom(dto);

        assertEquals("Lemes", settlement.getSettlementName());
        assertEquals(803979, settlement.getId());
        assertEquals(0, settlement.getVotersBySettlement());
        assertEquals(SettlementType.VILLAGE, settlement.getSettlementType());
    }


    // 2. Proveriti da li  mapper vraca greske za null vrednost polja name
    @Test
    void shouldHandelNullFieldSettlementName(){
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                4, "zapadnobacki", 1, "Sombor",
                2, "Miletic mz", 800,
                null,"O", 803979, "Oktobarska"
        );

        Settlement settlement = mapFrom(dto);
        assertNull(settlement.getSettlementName());

    }

    @Test
    void shouldHandelNullFieldSettlementType(){
        VotingRowDTO dto = new VotingRowDTO(
                4, "zapadnobacki", 1, "Sombor",
                2, "Miletic mz", 800,
                "lemes",null, 803979, "Oktobarska"
        );
        Settlement settlement = mapFrom(dto);
        assertEquals(SettlementType.VILLAGE, settlement.getSettlementType());
    }


}