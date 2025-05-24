package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.mappers.PollingPlaceMapper;
import com.discord.Projekat_Izbori.models.PollingPlace;
import com.discord.Projekat_Izbori.models.Settlement; // Potrebno za povezivanje
import com.discord.Projekat_Izbori.repositories.PollingPlaceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PollingPlaceService {

    private final PollingPlaceRepository pollingPlaceRepository;
    private final PollingPlaceMapper pollingPlaceMapper;

    public PollingPlaceService(PollingPlaceRepository pollingPlaceRepository, PollingPlaceMapper pollingPlaceMapper) {
        this.pollingPlaceRepository = pollingPlaceRepository;
        this.pollingPlaceMapper = pollingPlaceMapper;
    }

    public PollingPlace findOrCreatePollingPlace(VotingRowDTO dto, Settlement settlement) {
        Optional<PollingPlace> existingPollingPlace =
                pollingPlaceRepository.findByIdAndSettlementId(dto.getPollingPlaceId(), settlement.getId());

        if (existingPollingPlace.isPresent()) {
            return existingPollingPlace.get();
        } else {
            PollingPlace newPollingPlace = pollingPlaceMapper.mapFrom(dto);

            newPollingPlace.setSettlement(settlement);

            return pollingPlaceRepository.save(newPollingPlace);
        }
    }

    public void updatePollingStationPaperVoters(Integer pollingPlaceId, Integer paperVoters) {
        pollingPlaceRepository.findById(pollingPlaceId).ifPresent(pollingPlace -> {
            pollingPlace.setPollingStationPaperVoters(paperVoters);
            pollingPlaceRepository.save(pollingPlace);
        });
    }


    public void updateBallotCount(Integer pollingPlaceId, Integer ballotCount) {
        pollingPlaceRepository.findById(pollingPlaceId).ifPresent(pollingPlace -> {
            pollingPlace.setBallotCount(ballotCount);
            pollingPlaceRepository.save(pollingPlace);
        });
    }
}