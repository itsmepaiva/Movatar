package com.movatar.movatar.model.youtube;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YoutubeSearchResponseDTO {
    @JsonProperty("items")
    private YoutubeItemDTO[] items;
}
