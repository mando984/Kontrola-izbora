package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.mappers.SettlementMapper;
import com.discord.Projekat_Izbori.models.Municipality;
import com.discord.Projekat_Izbori.models.Settlement;
import com.discord.Projekat_Izbori.models.SettlementType;
import com.discord.Projekat_Izbori.repositories.SettlementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SettlementServiceTest {
    @Mock
    private SettlementRepository settlementRepository;
    @Mock
    private SettlementMapper settlementMapper;
    @InjectMocks
    private SettlementService settlementService;

    private VotingRowDTO votingRowDTO1;
    private VotingRowDTO votingRowDTO2;
    private Municipality municipality;

    @BeforeEach
    void setUp() {
        // Kreiraj konkretne DTO objekte za testove
        votingRowDTO1 = new VotingRowDTO(1, "Test District 1", 1, "Test Municipality 1", 1, "Test PP 1", 100,
                "Test Settlement 1", "G", 1, "Test Street 1");
        votingRowDTO2 = new VotingRowDTO(2, "Test District 2", 2, "Test Municipality 2", 2, "Test PP 2", 200,
                "Test Settlement 2", "O", 2, "Test Street 2");

        municipality = new Municipality();
        municipality.setId(votingRowDTO1.getMunicipalityId());
        municipality.setMunicipalityName(votingRowDTO1.getMunicipalityName());
        municipality.setTotalVotersByMunicipality(5000);
    }

    @Test
    void shouldReturnExistingSettlementWhenFound(){
        Settlement existingSettement = new Settlement();
        existingSettement.setId(votingRowDTO1.getSettlementId());
        existingSettement.setSettlementName(votingRowDTO1.getSettlementName());
        existingSettement.setVotersBySettlement(2000);
        existingSettement.setMunicipality(municipality);
        existingSettement.setSettlementType(SettlementType.VILLAGE);

        when(settlementRepository.findById(votingRowDTO1.getSettlementId()))
                .thenReturn(Optional.of(existingSettement));

        Settlement settlementResult = settlementService.findOrCreateSettlement(votingRowDTO1, municipality);

        assertEquals(existingSettement.getId(),settlementResult.getId());
        assertEquals(existingSettement.getSettlementName(), settlementResult.getSettlementName());
        assertEquals(existingSettement.getSettlementType(), settlementResult.getSettlementType());
        assertNotNull(settlementResult);
        verify(settlementRepository, times(1))
                .findById(settlementResult.getId());
        verify(settlementMapper, never()).mapFrom(votingRowDTO1);
        verify(settlementRepository, never()).save(any(Settlement.class));
    }

    @Test
    void shouldCreateAndReturnNewSettlementWhenNotFound() {
        Settlement newSettement = new Settlement();
        newSettement.setId(votingRowDTO1.getSettlementId());
        newSettement.setSettlementName(votingRowDTO1.getSettlementName());
        newSettement.setSettlementType(SettlementType.VILLAGE);
        newSettement.setVotersBySettlement(3000);
        newSettement.setMunicipality(municipality);

        Settlement savedSettlement = new Settlement();
        savedSettlement.setId(votingRowDTO1.getSettlementId());
        savedSettlement.setSettlementName(votingRowDTO1.getSettlementName());
        savedSettlement.setSettlementType(SettlementType.VILLAGE);
        savedSettlement.setVotersBySettlement(3000);
        savedSettlement.setMunicipality(municipality);
        when(settlementRepository.findById(votingRowDTO1.getSettlementId()))
                .thenReturn(Optional.empty());
        when(settlementRepository.save(any(Settlement.class)))
                .thenReturn(savedSettlement);
        when(settlementMapper.mapFrom(any(VotingRowDTO.class)))
                .thenReturn(newSettement);
        //ACT
        Settlement settlementResult = settlementService.findOrCreateSettlement(votingRowDTO1, municipality);
        // ASSERT
        assertNotNull(settlementResult);
        assertEquals(newSettement.getSettlementName(), settlementResult.getSettlementName());
        assertEquals(newSettement.getSettlementType(), settlementResult.getSettlementType());
        assertEquals(newSettement.getVotersBySettlement(), settlementResult.getVotersBySettlement());
        verify(settlementRepository, times(1)).findById(votingRowDTO1.getSettlementId());
        verify(settlementMapper, times(1)).mapFrom(votingRowDTO1);
        verify(settlementRepository, times(1)).save(newSettement);
    }






}
