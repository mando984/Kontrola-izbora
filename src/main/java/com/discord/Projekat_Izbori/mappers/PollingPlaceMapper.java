package com.discord.Projekat_Izbori.mappers;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.models.PollingPlace;
import org.springframework.stereotype.Component;

@Component
public class PollingPlaceMapper {

    public static PollingPlace mapFrom(VotingRowDTO dto){

        PollingPlace pollingPlace = new PollingPlace();
        pollingPlace.setId(dto.getPollingPlaceId());
        pollingPlace.setPollingPlaceName(dto.getPollingPlaceName());
        pollingPlace.setStreet(dto.getStreet());

        pollingPlace.setOfficialRegisteredVoters(dto.getNumberOfVoters());

        pollingPlace.setPollingStationPaperVoters(0);
        pollingPlace.setBallotCount(0);

        return pollingPlace;
    }
}