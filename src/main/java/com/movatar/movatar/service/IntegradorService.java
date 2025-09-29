package com.movatar.movatar.service;

import com.movatar.movatar.model.MovatarResponseDTO;
import com.movatar.movatar.model.MovatarYTResponseDTO;
import com.movatar.movatar.model.MovieDTO;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class IntegradorService {

    private final OmdbService omdbService;
    private final DiceBearService diceBearService;
    private final YoutubeService youtubeService;

    public IntegradorService(OmdbService omdbService, DiceBearService diceBearService, YoutubeService youtubeService) {
        this.omdbService = omdbService;
        this.diceBearService = diceBearService;
        this.youtubeService = youtubeService;
    }

    //OMDB API INTEGRADA COM DICEBEAR API
    public Optional<MovatarResponseDTO> getMovieAndAvatar(String title, String year) {
        MovieDTO movie = omdbService.getMovie(title, year);
        if (movie == null || "False".equals(movie.getResponse()) || movie.getImdbId() == null) {
            return Optional.empty();
        }

        String avatarUrl = diceBearService.createAvatarUrl(movie.getImdbId());

        MovatarResponseDTO response = MovatarResponseDTO.builder()
                .title(movie.getTitle())
                .year(movie.getYear())
                .plot(movie.getPlot())
                .imdbId(movie.getImdbId())
                .avatarUrl(avatarUrl)
                .avatarSource("DiceBear API")
                .build();

        return Optional.of(response);
    }


    //OMDB API INTEGRADA COM YOUTUBE DATA API v3
    public Optional<MovatarYTResponseDTO> getMovieTrailer(String title, String year) {
        MovieDTO movie = omdbService.getMovie(title, year);
        if (movie == null || "False".equals(movie.getResponse()) || movie.getImdbId() == null) {
            return Optional.empty();
        }

        String videoId = youtubeService.searchTrailerVideoId(movie.getTitle(), movie.getYear());

        String trailerUrl = null;
        if (videoId != null) {
            trailerUrl = "https://www.youtube.com/watch?v=" + videoId;
        }
        MovatarYTResponseDTO response = MovatarYTResponseDTO.builder()
                .title(movie.getTitle())
                .year(movie.getYear())
                .plot(movie.getPlot())
                .imdbId(movie.getImdbId())
                .videoId(videoId)
                .trailerUrl(trailerUrl != null ? trailerUrl : "Trailer não encontrado")
                .build();
        return Optional.of(response);
    }



}
