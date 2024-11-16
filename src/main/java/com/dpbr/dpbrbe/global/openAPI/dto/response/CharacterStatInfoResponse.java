package com.dpbr.dpbrbe.global.openAPI.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CharacterStatInfoResponse(
        String date,

        @JsonProperty("character_class")
        String characterClass,

        @JsonProperty("final_stat")
        List<FinalStat> finalStats
) {

    public record FinalStat(
            @JsonProperty("stat_name")
            String statName,

            @JsonProperty("stat_value")
            String statValue
    ) {

    }
}
