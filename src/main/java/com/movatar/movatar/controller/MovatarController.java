package com.movatar.movatar.controller;

import com.movatar.movatar.model.MovatarResponseDTO;
import com.movatar.movatar.model.MovatarYTResponseDTO;
import com.movatar.movatar.model.MovieDTO;
import com.movatar.movatar.service.DiceBearService;
import com.movatar.movatar.service.IntegradorService;
import com.movatar.movatar.service.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/movie")
public class MovatarController {

    @Autowired
    private IntegradorService integradorService;

    @Autowired
    private OmdbService omdbService;

    @GetMapping
    public ResponseEntity<Object> getMovie(@RequestParam("title")String title, @RequestParam(value = "year", required = false) String year) {
        String anoParaBusca = year != null ? year : "";

        MovieDTO movie = omdbService.getMovie(title, anoParaBusca);
        if (movie != null) {
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.status(404).body(
                    Map.of("erro", "Dados do filme não encontrados na OMDb.")
            );
        }

    }

    @GetMapping("/avatar")
    public ResponseEntity<Object> getMovieAvatar(@RequestParam("title")String title, @RequestParam(value = "year", required = false) String year){
        String anoParaBusca = year != null ? year : "";
        Optional<MovatarResponseDTO> resultado = integradorService.getMovieAndAvatar(title, anoParaBusca);
        return resultado.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(
                Map.of("erro", "Filme não encontrado para o título: " + title)
        ));
    }

    @GetMapping("/trailer")
    public ResponseEntity<Object> getMovieTrailer(@RequestParam("title")String title, @RequestParam(value = "year", required = false) String year){
        String anoParaBusca = year != null ? year : "";
        Optional<MovatarYTResponseDTO> resultado = integradorService.getMovieTrailer(title, anoParaBusca);
        return resultado.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(
                Map.of("erro", "Filme ou trailer não encontrado para o título/ano especificados.")
        ));
    }
}
