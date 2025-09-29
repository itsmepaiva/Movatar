package com.movatar.movatar.model.youtube;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YoutubeVideoIdDTO {
    @JsonProperty("videoId")
    private String videoId;
}
