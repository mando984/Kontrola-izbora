package com.discord.Projekat_Izbori.dto.input;

import lombok.Data;

@Data
public class VotingRawDTO {

    private Long districtId;

    private String districtName;

    private Integer municipalityId;

    private String municipalityName;

    private Long pollingPlaceId;

    private String pollingPlaceName;

    private Integer numberOfVoters;

    private String settlement;

    private String street;
}

