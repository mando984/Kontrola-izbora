package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DataCacheService {

    private final RikImportService rikImportService;
    private List<VotingRowDTO> cache;

    public DataCacheService(RikImportService rikImportService) {
        this.rikImportService = rikImportService;
    }

    public List<VotingRowDTO> getCacheDate() throws IOException {
        if(cache == null){
            cache = rikImportService.importData();
        }
        return cache;
    }

    public void clearCache(){
        cache = null;
    }
}
