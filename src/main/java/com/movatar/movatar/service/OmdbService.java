package com.movatar.movatar.service;


import com.movatar.movatar.model.MovieDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OmdbService {
    @Value("${omdb.api.base.url}")
    private String baseUrl;

    @Value("${omdb.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public MovieDTO getMovie(String title, String year) {
        String tituloFormatado = title.replace(" ", "+");

        String url = String.format("%s?apikey=%s&t=%s&y=%s", baseUrl, apiKey, tituloFormatado, year);

        try{
            MovieDTO movie = restTemplate.getForObject(url, MovieDTO.class);

            if(movie != null && "False".equals(movie.getResponse())){
                System.err.println("Erro OMDb: FILME NAO ENCONTRADO.");
                return null;
            }
            return movie;
        } catch (Exception e){
            System.err.println("Erro na comunicaçao com a OMDb API: " + e.getMessage());
            return null;
        }
    }


}
