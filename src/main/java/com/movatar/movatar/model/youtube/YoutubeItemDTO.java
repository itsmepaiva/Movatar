package com.movatar.movatar.model.youtube;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YoutubeItemDTO {
    @JsonProperty("id")
    private YoutubeVideoIdDTO id;
}
