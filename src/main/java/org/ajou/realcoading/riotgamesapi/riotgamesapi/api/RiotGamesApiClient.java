package org.ajou.realcoading.riotgamesapi.riotgamesapi.api;


import org.ajou.realcoading.riotgamesapi.riotgamesapi.domain.SummonerGameGrade;
import org.ajou.realcoading.riotgamesapi.riotgamesapi.domain.SummonerInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RiotGamesApiClient {

    @Autowired
    private RestTemplate restTemplate;

    private final String apiKey = "RGAPI-e04a8259-fa9a-444c-a72f-e13cb5d980b6";  // 만료 기한 : 7월 20일 23pm까지

    private final ParameterizedTypeReference<List<SummonerGameGrade>> summonerGameGradeType = new ParameterizedTypeReference<List<SummonerGameGrade>>() {
    };


    private final String currentSummonerURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/{cityName}?api_key={apiKey}";
    private final String currentSummonerGradeURL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/{encryptedSummonerId}?api_key={apiKey}";


    public SummonerInformation getSummonerInformation(String summonerName) {
        SummonerInformation summonerInformation = restTemplate.exchange(this.currentSummonerURL, HttpMethod.GET, null, SummonerInformation.class, summonerName, apiKey)
                .getBody();
        return summonerInformation;
    }

    public List<SummonerGameGrade> getSummonerGameGrade(String encryptedSummonerId) {
        return restTemplate.exchange(currentSummonerGradeURL, HttpMethod.GET, null, summonerGameGradeType, encryptedSummonerId, apiKey).getBody();
    }
}