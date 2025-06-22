package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.mappers.MunicipalityMapper;
import com.discord.Projekat_Izbori.models.District;
import com.discord.Projekat_Izbori.models.Municipality;
import com.discord.Projekat_Izbori.repositories.MunicipalityRepository;
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
public class MunicipalityServiceTest {

    @Mock
    MunicipalityRepository municipalityRepository;

    @Mock
    MunicipalityMapper municipalityMapper;

    @InjectMocks
    MunicipalityService municipalityService;
    private VotingRowDTO votingRowDTO1;
    private VotingRowDTO votingRowDTO2;
    private District district;

    @BeforeEach
    void setUp(){
        // Kreiraj konkretne DTO objekte za testove
        votingRowDTO1 = new VotingRowDTO(1, "Test District 1", 1, "Test Municipality 1", 1, "Test PP 1", 100, "Test Settlement 1", "G", 1, "Test Street 1");
        votingRowDTO2 = new VotingRowDTO(2, "Test District 2", 2, "Test Municipality 2", 2, "Test PP 2", 200, "Test Settlement 2", "O", 2, "Test Street 2");

        district = new District();
        district.setId(votingRowDTO1.getDistrictId()); // Koristi ID iz DTO-a
        district.setDistrictName(votingRowDTO1.getDistrictName());
        district.setTotalVotersByDistrict(5400);

    }

    @Test
    void shouldReturnExistingMunicipalityWhenFound(){
        // ARRANGE
        Municipality existingMunicipality = new Municipality();
        existingMunicipality.setId(votingRowDTO1.getMunicipalityId());
        existingMunicipality.setMunicipalityName(votingRowDTO1.getMunicipalityName());
        existingMunicipality.setTotalVotersByMunicipality(567);
        existingMunicipality.setDistrict(district);
        when(municipalityRepository.findById(votingRowDTO1.getMunicipalityId()))
                .thenReturn(Optional.of(existingMunicipality));
        //ACT
        Municipality municipalityResult = municipalityService.findOrCreateMunicipality(votingRowDTO1,district);

        //ASSERT
        assertNotNull(municipalityResult);
        assertEquals(existingMunicipality.getId(), municipalityResult.getId());
        assertEquals(existingMunicipality.getDistrict(), municipalityResult.getDistrict());
        assertEquals(existingMunicipality.getMunicipalityName(), municipalityResult.getMunicipalityName());
        verify(municipalityRepository, times(1)).findById(votingRowDTO1.getMunicipalityId());
        verify(municipalityMapper, never()).mapFrom(any(VotingRowDTO.class));
        verify(municipalityRepository, never()).save(any(Municipality.class));
    }

    @Test
    void shouldCreateAndReturnNewMunicipalityWhenNotFound() {
        // ARRANGE
        Municipality newMunicipality = new Municipality();
        newMunicipality.setId(votingRowDTO1.getMunicipalityId());
        newMunicipality.setMunicipalityName(votingRowDTO1.getMunicipalityName());
        newMunicipality.setTotalVotersByMunicipality(567);
        newMunicipality.setDistrict(district);

        Municipality savedMunicipality = new Municipality();
        savedMunicipality.setId(votingRowDTO1.getMunicipalityId());
        savedMunicipality.setMunicipalityName(votingRowDTO1.getMunicipalityName());
        savedMunicipality.setTotalVotersByMunicipality(567);
        savedMunicipality.setDistrict(district);
        //Mock ponašanje municipalityRepository.findById() - treba da vrati Optional.empty(
        when(municipalityRepository.findById(votingRowDTO1.getMunicipalityId()))
                .thenReturn(Optional.empty());
        when(municipalityRepository.save(any(Municipality.class)))
                .thenReturn(savedMunicipality);
        when(municipalityMapper.mapFrom(any(VotingRowDTO.class)))
                .thenReturn(newMunicipality);

        //ACT
        Municipality municipalityResult = municipalityService.findOrCreateMunicipality(votingRowDTO1,district);
        // ASSERT
        assertNotNull(municipalityResult);
        assertEquals(savedMunicipality.getId(), municipalityResult.getId());
        assertEquals(savedMunicipality.getMunicipalityName(), municipalityResult.getMunicipalityName());
        assertEquals(savedMunicipality.getTotalVotersByMunicipality(), municipalityResult.getTotalVotersByMunicipality());
        //da je findById() pozvan jednom
        verify(municipalityRepository,  times(1)).findById(votingRowDTO1.getMunicipalityId());
        // 3. Verifikuj da su mapper i save metoda pozvani tačno jednom
        verify(municipalityMapper, times(1)).mapFrom(votingRowDTO1); // Proveri da li je pozvan sa ispravnim DTO-om
        verify(municipalityRepository, times(1)).save(newMunicipality);
    }

    @Test
    void shouldUpdateMunicipalityTotalVotersWhenMunicipalityExists(){

        Integer municipalityIdToUpdate = votingRowDTO2.getMunicipalityId();
        Integer newTotalVoters = 2345;

        Municipality existingMunicipality = new Municipality();
        existingMunicipality.setDistrict(district);
        existingMunicipality.setId(votingRowDTO2.getMunicipalityId());
        existingMunicipality.setMunicipalityName(votingRowDTO2.getMunicipalityName());
        existingMunicipality.setTotalVotersByMunicipality(222);

        when(municipalityRepository.findById(municipalityIdToUpdate))
                .thenReturn(Optional.of(existingMunicipality));

        when(municipalityRepository.save(any(Municipality.class)))
                .thenReturn(existingMunicipality);

        municipalityService.updateMunicipalityTotalVoters(municipalityIdToUpdate, newTotalVoters);

        verify(municipalityRepository, times(1)).findById(municipalityIdToUpdate);
        assertEquals(newTotalVoters, existingMunicipality.getTotalVotersByMunicipality());
        verify(municipalityRepository, times(1)).save(existingMunicipality);

    }

    @Test
    void shouldNotUpdateMunicipalityTotalVotersWhenMunicipalityDoesNotExist(){
        Integer notExistingMunicipalitytId = 999;
        Integer newTotalVoters = 3243;
        when(municipalityRepository.findById(notExistingMunicipalitytId))
                .thenReturn(Optional.empty());

        municipalityService.updateMunicipalityTotalVoters(notExistingMunicipalitytId, newTotalVoters);
        // ASSERT
        verify(municipalityRepository, times(1)).findById(notExistingMunicipalitytId);
        verify(municipalityRepository, never()).save(any(Municipality.class));

    }



}
