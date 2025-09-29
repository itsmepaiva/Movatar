package com.movatar.movatar.service;

import com.movatar.movatar.model.youtube.YoutubeSearchResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YoutubeService {
    @Value("${youtube.api.search.url}")
    private String baseUrl;

    @Value("${youtube.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String searchTrailerVideoId(String title, String year) {
        String query = title + " " + year + " Official Trailer";
        String finalQuery = query.replace(" ", "+");

        String url = String.format("%s?key=%s&q=%s&part=id&maxResults=1&type=video",
                baseUrl, apiKey, finalQuery);
        try {
            YoutubeSearchResponseDTO response = restTemplate.getForObject(url, YoutubeSearchResponseDTO.class);

            if (response != null && response.getItems().length > 0) {
                return response.getItems()[0].getId().getVideoId();
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar trailer no YouTube: " + e.getMessage());
        }
        return null;
    }
}
