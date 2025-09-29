package com.movatar.movatar.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DiceBearService {

    @Value("${dicebear.api.base.url}")
    private String baseUrl;

    @Value("${dicebear.avatar.style}")
    private String style;

    public String createAvatarUrl(String imdbId) {
        String url = String.format("%s%s/svg?seed=%s", baseUrl, style, imdbId);
        return url;
    }
}
