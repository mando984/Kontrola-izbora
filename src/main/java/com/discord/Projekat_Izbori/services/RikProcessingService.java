package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.aggregators.VotingAggregator;
import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.models.District;
import com.discord.Projekat_Izbori.models.Municipality;
import com.discord.Projekat_Izbori.models.Settlement;
import com.discord.Projekat_Izbori.models.PollingPlace;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class RikProcessingService {

    private final DataCacheService dataCacheService;
    private final DistrictService districtService;
    private final MunicipalityService municipalityService;
    private final SettlementService settlementService;
    private final PollingPlaceService pollingPlaceService;
    private final VotingAggregator votingAggregator;

    public RikProcessingService(DataCacheService dataCacheService,
                                DistrictService districtService,
                                MunicipalityService municipalityService,
                                SettlementService settlementService,
                                PollingPlaceService pollingPlaceService,
                                VotingAggregator votingAggregator) {
        this.dataCacheService = dataCacheService;
        this.districtService = districtService;
        this.municipalityService = municipalityService;
        this.settlementService = settlementService;
        this.pollingPlaceService = pollingPlaceService;
        this.votingAggregator = votingAggregator;
    }

    @Transactional
    public void processAll() throws IOException {
        List<VotingRowDTO> data = dataCacheService.getCacheDate();

        Map<Integer, Integer> districtTotalVoters = votingAggregator.aggregateByDistrictId(data);
        Map<Integer, Integer> municipalityTotalVoters = votingAggregator.aggregateByMunicipalityId(data);
        Map<String, Integer> settlementTotalVoters = votingAggregator.aggregateBySettlementNameAndMunicipalityId(data);


        for (VotingRowDTO dto : data) {
            District district = districtService.findOrCreateDistrict(dto);
            if (district.getTotalVotersByDistrict() == 0 && districtTotalVoters.containsKey(district.getId())) {
                districtService.updateDistrictTotalVoters(district.getId(), districtTotalVoters.get(district.getId()));
            }

            Municipality municipality = municipalityService.findOrCreateMunicipality(dto, district);
            if (municipality.getTotalVotersByMunicipality() == 0 && municipalityTotalVoters.containsKey(municipality.getId())) {
                municipalityService.updateMunicipalityTotalVoters(municipality.getId(), municipalityTotalVoters.get(municipality.getId()));
            }

            Settlement settlement = settlementService.findOrCreateSettlement(dto, municipality);

            String settlementMapKey = dto.getSettlement() + "_" + dto.getMunicipalityId();
            if (settlement.getVotersBySettlement() == 0 && settlementTotalVoters.containsKey(settlementMapKey)) {
                settlementService.updateSettlementTotalVoters(settlement.getId(), settlementTotalVoters.get(settlementMapKey));
            }

            PollingPlace pollingPlace = pollingPlaceService.findOrCreatePollingPlace(dto, settlement);
        }
    }
}