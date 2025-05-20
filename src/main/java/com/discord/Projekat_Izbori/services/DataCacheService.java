package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.dto.input.VotingRawDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DataCacheService {

    private final RikImportService rikImportService;
    private List<VotingRawDTO> cache;

    public DataCacheService(RikImportService rikImportService) {
        this.rikImportService = rikImportService;
    }

    public List<VotingRawDTO> getCacheDate() throws IOException {
        if(cache == null){
            cache = rikImportService.importDate();
        }
        return cache;
    }

    public void clearCache(){
        cache = null;
    }
}
