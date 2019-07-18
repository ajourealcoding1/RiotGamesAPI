package org.ajou.realcoading.riotgamesapi.riotgamesapi.api;


import org.ajou.realcoading.riotgamesapi.riotgamesapi.domain.SummonerGameGrade;
import org.ajou.realcoading.riotgamesapi.riotgamesapi.domain.SummonerInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RiotGamesApiClient {

    @Autowired
    private RestTemplate restTemplate;

    private final String apiKey = "";// 빈칸으로 둘게요

    private final ParameterizedTypeReference<List<SummonerGameGrade>> summnorGameGradeType = new ParameterizedTypeReference<List<SummonerGameGrade>>() {};


    private final String currentSummnorURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/{cityName}?api_key={apiKey}";
    private final String currentSummnorGradeURL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/{encryptedSummonerId}?api_key={apiKey}";


    public SummonerInformation getSummonerInformation(String summonerName) {
        SummonerInformation summonerInformation = restTemplate.exchange(this.currentSummnorURL, HttpMethod.GET, null, SummonerInformation.class, summonerName, apiKey)
                .getBody();
        return summonerInformation;
    }

    public List<SummonerGameGrade> getSummnorGameGrade(String encryptedSummonerId){
        return  restTemplate.exchange(currentSummnorGradeURL, HttpMethod.GET, null, summnorGameGradeType, encryptedSummonerId, apiKey).getBody();
    }
}