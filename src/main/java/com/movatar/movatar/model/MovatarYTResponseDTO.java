package com.movatar.movatar.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovatarYTResponseDTO {

    private String title;
    private String year;
    private String plot;
    private String imdbId;

    private String trailerUrl;
    private String videoId;
}
