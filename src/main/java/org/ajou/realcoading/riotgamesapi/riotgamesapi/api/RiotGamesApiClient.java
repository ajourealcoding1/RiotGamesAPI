package org.ajou.realcoading.riotgamesapi.riotgamesapi.api;


import org.ajou.realcoading.riotgamesapi.riotgamesapi.domain.SummonerInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class RiotGamesApiClient {
    @Autowired
    private RestTemplate restTemplate;

    private final String apiKey = "";// 빈칸으로 둘게요

    private final String currentSummnorURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/{cityName}?api_key={apiKey}";


    public SummonerInformation getCurrentWeather(String cityName) {
        SummonerInformation summonerInformation = restTemplate.exchange(this.currentSummnorURL, HttpMethod.GET, null, SummonerInformation.class, cityName, apiKey)
                .getBody();
        return summonerInformation;
    }


