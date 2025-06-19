package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Neophodno za Mockito anotacije
public class DataCacheServiceTest {

    @Mock
    private RikImportService rikImportService;

    @InjectMocks
    private DataCacheService dataCacheService;

    private List<VotingRowDTO> mockData;

    @BeforeEach
    void setUp() throws IOException{
        mockData = Arrays.asList(
                new VotingRowDTO(1, "Dist1", 1, "Mun1", 1, "PP1", 100, "Sett1", "G", 1, "Str1"),
                new VotingRowDTO(2, "Dist2", 2, "Mun2", 2, "PP2", 200, "Sett2", "O", 2, "Str2")
        );
    }

    @Test
    void shouldLoadDataAndCacheOnFirstCall() throws IOException{
        // ARRANGE
        // Kada se pozove rikImportService.importData(), vrati naše mock podatke
        Mockito.when(rikImportService.importData()).thenReturn(mockData);
        // ACT
        List<VotingRowDTO> results = dataCacheService.getCacheDate();
        // ASSERT
        // Proveri da li su podaci vraćeni
        assertNotNull(results);
        assertEquals(mockData.size(), results.size());
        assertEquals(mockData.get(0).getDistrictName(), results.get(0).getDistrictName());

        verify(rikImportService, times(1)).importData();
    }

    @Test
    void shouldReturnCachedDataOnSubsequentCallsWithoutCallingImportServiceAgain() throws IOException {
        // ARRANGE
        // Konfiguracija da importData() vrati mockData samo prvi put
        when(rikImportService.importData()).thenReturn(mockData);

        // Prvi poziv za popunjavanje keša
        dataCacheService.getCacheDate();
        // Resetujemo mock da bismo proverili da li će biti poziva u budućnosti
        reset(rikImportService); // Važno: resetuje broj poziva na 0

        // ACT
        List<VotingRowDTO> firstSubsequentCall = dataCacheService.getCacheDate();
        List<VotingRowDTO> secondSubsequentCall = dataCacheService.getCacheDate();

        // ASSERT
        // Proveri da li su podaci vraćeni (iz keša)
        assertNotNull(firstSubsequentCall);
        assertEquals(mockData.size(), firstSubsequentCall.size());
        assertEquals(firstSubsequentCall, secondSubsequentCall); // Proveri da li su Reference isti, što znači da je keširano

        // Proveri da li importData() NIJE pozvan ponovo
        verify(rikImportService, never()).importData();
    }


    @Test
    void shouldClearCacheAndCauseReImportOnNextCall() throws IOException {
        // ARRANGE
        // Konfiguracija mocka za prvi poziv
        when(rikImportService.importData()).thenReturn(mockData);

        // Popuni keš
        dataCacheService.getCacheDate();
        // U ovom trenutku, rikImportService.importData() je pozvan 1 put.

        // Resetuj mock - ovo briše SVE prethodne konfiguracije i brojače poziva.
        // Nakon ovoga, rikImportService.importData() će vratiti null ako se pozove,
        // jer nema definisano ponašanje.
        reset(rikImportService);

        // *** KLJUČNA PROMENA: Ponovo konfiguriši ponašanje mocka za SLEDEĆI poziv ***
        when(rikImportService.importData()).thenReturn(mockData); // <-- DODAJ OVU LINIJU

        // ACT
        dataCacheService.clearCache();
        // Ponovo pozovi getCacheDate() - sada će dovesti do re-importa i pozvati rikImportService.importData()
        List<VotingRowDTO> resultAfterClear = dataCacheService.getCacheDate();

        // ASSERT
        assertNotNull(resultAfterClear);
        assertEquals(mockData.size(), resultAfterClear.size()); // Sada bi ovo trebalo da prođe, jer je mock rekonfigurisan

        // Proveri da li je importData() pozvan ponovo, tačno jednom, nakon čišćenja keša
        verify(rikImportService, times(1)).importData(); // <-- Sada bi i ovo trebalo da prođe
    }




}
