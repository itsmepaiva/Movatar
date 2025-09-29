package com.movatar.movatar.controller;

import com.movatar.movatar.model.MovatarYTResponseDTO;
import com.movatar.movatar.model.MovieDTO;
import com.movatar.movatar.service.IntegradorService;
import com.movatar.movatar.service.OmdbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MovatarController.class)
public class MovatarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IntegradorService integradorService;

    @MockBean
    private OmdbService omdbService;

    @Test
    void buscarDadosFilme_RetornaSucesso_QuandoFilmeEncontrado() throws Exception {
        MovieDTO movieMock = new MovieDTO();
        movieMock.setTitle("Matrix");
        movieMock.setYear("1999");
        movieMock.setResponse("True");

        when(omdbService.getMovie("Matrix", "1999")).thenReturn(movieMock);

        mockMvc.perform(get("/api/movie")
                .param("title", "Matrix")
                .param("year", "1999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Title").value("Matrix"))
                .andExpect(jsonPath("$.Year").value("1999"));
    }

    @Test
    void buscarDadosFilme_Retorna404_QuandoFilmeNaoEncontrado() throws Exception {
        when(omdbService.getMovie("FilmeInexistente", "")).thenReturn(null);
        mockMvc.perform(get("/api/movie")
                .param("title", "FilmeInexistente"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").exists());
    }

    @Test
    void buscarFilmeETrailer_RetornaSucesso_QuandoIntegracaoEncontrada() throws Exception {
        MovatarYTResponseDTO responseMock = MovatarYTResponseDTO.builder()
                .title("Interestellar")
                .year("2014")
                .trailerUrl("https://www.youtube.com/watch?v=mockVideoId")
                .videoId("MockVideoId")
                .build();

        when(integradorService.getMovieTrailer("Interestellar", "2014"))
                .thenReturn(java.util.Optional.of(responseMock));

        mockMvc.perform(get("/api/movie/trailer")
                        .param("title", "Interestellar")
                        .param("year", "2014"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Interestellar"))
                .andExpect(jsonPath("$.year").value("2014"));
    }

    @Test
    void buscarFilmeETrailer_Retorna404_QuandoIntegracaoFalha() throws Exception {
        when(integradorService.getMovieTrailer("FilmeFalha", "2020"))
                .thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/movie/trailer")
                .param("title", "FilmeFalha")
                .param("year", "2020"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").exists());
    }
}
