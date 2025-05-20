package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.dto.input.VotingRawDTO;
import com.discord.Projekat_Izbori.models.District;
import com.discord.Projekat_Izbori.models.Municipality;
import com.discord.Projekat_Izbori.models.Settlement;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;



@Service
public class RikProcessingService {

    private final DataCacheService dataCacheService;
    private final DistrictService districtService;
    private final MunicipalityService municipalityService;
    private final SettlementService settlementService;

    public RikProcessingService(DataCacheService dataCacheService, DistrictService districtService, MunicipalityService municipalityService, SettlementService settlementService) {
        this.dataCacheService = dataCacheService;
        this.districtService = districtService;
        this.municipalityService = municipalityService;
        this.settlementService = settlementService;
    }

    public void processAll() throws IOException {
        List<VotingRawDTO> data = dataCacheService.getCacheDate();
        District district = districtService.extractDistrict(data);
        Map<String, Municipality> municipalities = municipalityService.extractMunicipality(data);
        Map<String, Settlement> settlements = settlementService.extractSettlement(data);

        districtService.saveDistrict(district);
        municipalities.values().forEach(municipalityService::saveMunicipality);
        settlements.values().forEach(settlementService::saveSettlement);
    }

}
