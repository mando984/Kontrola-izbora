package com.discord.Projekat_Izbori.services;


import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.mappers.DistrictMapper;
import com.discord.Projekat_Izbori.models.District;
import com.discord.Projekat_Izbori.repositories.DistrictRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DistrictServiceTest {
    @Mock
    private DistrictRepository districtRepository;
    @Mock
    private DistrictMapper districtMapper;

    @InjectMocks
    private DistrictService districtService;


    private VotingRowDTO votingRowDTO1;
    private VotingRowDTO votingRowDTO2;

    @BeforeEach
    void setUp(){
        // Kreiraj konkretne DTO objekte za testove
        votingRowDTO1 = new VotingRowDTO(1, "Test District 1", 1, "Test Municipality 1", 1, "Test PP 1", 100, "Test Settlement 1", "G", 1, "Test Street 1");
        votingRowDTO2 = new VotingRowDTO(2, "Test District 2", 2, "Test Municipality 2", 2, "Test PP 2", 200, "Test Settlement 2", "O", 2, "Test Street 2");
    }

    @Test
    void shouldReturnExistingDistrictWhenFound(){
        //Arrange
        // ARRANGE
        District existingDistrict = new District();
        existingDistrict.setId(votingRowDTO1.getDistrictId()); // Koristi ID iz DTO-a
        existingDistrict.setDistrictName(votingRowDTO1.getDistrictName());
        existingDistrict.setTotalVotersByDistrict(500);

        // Ako DistrictService koristi findById (što je manje verovatno za proveru postojanja po DTO-u)
        when(districtRepository.findById(votingRowDTO1.getDistrictId()))
                .thenReturn(Optional.of(existingDistrict));

        //ACT
        District districtResult = districtService.findOrCreateDistrict(votingRowDTO1);

        //Assert
        Assertions.assertNotNull(districtResult);
        assertEquals(existingDistrict.getId(), districtResult.getId());
        assertEquals(existingDistrict.getDistrictName(), districtResult.getDistrictName());
        assertEquals(existingDistrict.getTotalVotersByDistrict(), districtResult.getTotalVotersByDistrict());

        verify(districtRepository, times(1)).findById(votingRowDTO1.getDistrictId());

        verify(districtMapper, never()).mapFrom(any(VotingRowDTO.class));
        verify(districtRepository, never()).save(any(District.class));

    }


    @Test
    void shouldCreateAndReturnNewDistrictWhenNotFound() {
        //novi District objekat koji ce mapper vratiti
        District newDistrict = new District();
        newDistrict.setId(votingRowDTO2.getDistrictId());
        newDistrict.setDistrictName(votingRowDTO2.getDistrictName());
        newDistrict.setTotalVotersByDistrict(1000);
        //novi District objekat koji ce mapper vratiti
        District savedDistrict = new District();
        savedDistrict.setId(votingRowDTO2.getDistrictId());
        savedDistrict.setDistrictName(votingRowDTO2.getDistrictName());
        savedDistrict.setTotalVotersByDistrict(1000);
        // Mock ponašanje districtRepository.findById() - treba da vrati Optional.empty()
        when(districtRepository.findById(votingRowDTO2.getDistrictId()))
                .thenReturn(Optional.empty());
        // Mock ponašanje districtMapper.mapFrom()
        when(districtMapper.mapFrom(any(VotingRowDTO.class)))
                .thenReturn(newDistrict);

        // Mock ponašanje districtRepository.save()
        when(districtRepository.save(any(District.class)))
                .thenReturn(savedDistrict);

        //ACT
        District districtResult = districtService.findOrCreateDistrict(votingRowDTO2);

        // ASSERT

        assertNotNull(districtResult);
        assertEquals(savedDistrict.getId(), districtResult.getId());
        assertEquals(savedDistrict.getDistrictName(), districtResult.getDistrictName());
        assertEquals(savedDistrict.getTotalVotersByDistrict(), districtResult.getTotalVotersByDistrict());


        //da je findById() pozvan jednom
        verify(districtRepository, times(1)).findById(votingRowDTO2.getDistrictId());

        // 3. Verifikuj da su mapper i save metoda pozvani tačno jednom
        verify(districtMapper, times(1)).mapFrom(votingRowDTO2); // Proveri da li je pozvan sa ispravnim DTO-om
        verify(districtRepository, times(1)).save(newDistrict);
    }

}
