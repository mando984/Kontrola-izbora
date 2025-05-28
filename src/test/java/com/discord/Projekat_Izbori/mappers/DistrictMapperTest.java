package com.discord.Projekat_Izbori.mappers;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.models.District;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class DistrictMapperTest {

    private DistrictMapper districtMapper;

    @BeforeEach
    void setUp() {
        districtMapper = new DistrictMapper();
    }

    @Test
    void shouldMapVotingRowDtoToDistrict() {
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                5, "zapadnobacki", 0, "Sombor", 2,
                "Miletic mz", 800, "Lemes", "Oktobarska"
        );

        // ACT
        District district = districtMapper.mapFrom(dto);

        // ASSERT
        District expected = new District(5, "zapadnobacki", 0, null);
        assertEquals(expected.getDistrictName(), district.getDistrictName());
        assertEquals(expected.getId(), district.getId());
        assertEquals(expected.getTotalVotersByDistrict(), district.getTotalVotersByDistrict());
    }

    @Test
    void shouldHandleNullDistrictIdGracefully() {
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                null, "zapadnobacki", 0, "Sombor", 2,
                "Miletic mz", 800, "Lemes", "Oktobarska"
        );

        // ACT
        District district = districtMapper.mapFrom(dto);

        // ASSERT
        assertNull(dto.getDistrictId());
        assertEquals("zapadnobacki", district.getDistrictName());
        assertNull(district.getId());
    }

    @Test
    void shouldHandleNullDistrictNameGracefully() {
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                5, null, 0, "Sombor", 2,
                "Miletic mz", 800, "Lemes", "Oktobarska"
        );

        // ACT
        District district = DistrictMapper.mapFrom(dto);

        // ASSERT
        assertNull(district.getDistrictName(), "District name should be null when input is null");
        assertEquals(5, district.getId());
    }

    @Test
    void shouldHandleEmptyDistrictName() {
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                5, "", 0, "Sombor", 2,
                "Miletic mz", 800, "Lemes", "Oktobarska"
        );

        // ACT
        District district = DistrictMapper.mapFrom(dto);

        // ASSERT
        assertTrue(StringUtils.isBlank(district.getDistrictName()), "District name should be blank");
        assertEquals(5, district.getId());
    }
}
