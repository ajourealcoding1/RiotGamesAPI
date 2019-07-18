package org.ajou.realcoading.riotgamesapi.riotgamesapi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ajou.realcoading.riotgamesapi.riotgamesapi.api.RiotGamesApiClient;
import org.ajou.realcoading.riotgamesapi.riotgamesapi.domain.SummonerGameGrade;
import org.ajou.realcoading.riotgamesapi.riotgamesapi.domain.SummonerInformation;
import org.ajou.realcoading.riotgamesapi.riotgamesapi.repository.SummonerGameGradeRepository;
import org.ajou.realcoading.riotgamesapi.riotgamesapi.repository.SummonerInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class SummonerService {
    @Autowired
    private RiotGamesApiClient riotGamesApiClient;
    @Autowired
    private SummonerInformationRepository summonerInformationRepository;
    @Autowired
    private SummonerGameGradeRepository summonerGameGradeRepository;

    private LinkedList<String> cityNamesQueue = new LinkedList<>();

    public List<String> getAvailableCityNames() throws IOException {
        File availableSummnorNamesFile = new File("availableSummnorNames");
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(availableSummnorNamesFile, new TypeReference<List<String>>() {});
    }

    @Scheduled(fixedDelay = 2000L)
    public void getSummnorGameGradePeriodicallyByCityName() throws IOException {
        if (cityNamesQueue.isEmpty()) {
            List<String> availableSummnorNames = this.getAvailableCityNames();
            cityNamesQueue.addAll(availableSummnorNames);
        }

        String summnorName = cityNamesQueue.pop();
        cityNamesQueue.add(summnorName);

        SummonerInformation summonerInformation = riotGamesApiClient.getSummonerInformation(summnorName);
        String encryptedId = summonerInformation.getId();

        List<SummonerGameGrade> summonerGameGradeList = riotGamesApiClient.getSummnorGameGrade(encryptedId);

        SummonerGameGrade summnorSummonerGameGrade = summonerGameGradeList.remove(0);

        SummonerGameGrade summnorSummonerGameGradeFromDb = summonerGameGradeRepository.findGameGradeBySummnorName(summnorName);


        if(summnorSummonerGameGrade != null || summnorSummonerGameGradeFromDb == null || !summnorSummonerGameGrade.equals(summnorSummonerGameGradeFromDb)) {
            SummonerGameGrade insertedOrUpdatedSummnorSummonerGameGrade = summonerGameGradeRepository.insertOrUpdatedSummnorGameGrade(summnorSummonerGameGrade);
            log.info("SummonerGameGrade has inserted or updated successfully. SummonerGameGrade : {}", insertedOrUpdatedSummnorSummonerGameGrade);
        }
        else{
            log.info("SummonerGameGrade and SummonerGameGradeFromDb are the same");
        }
    }

    public SummonerInformation getCurrentSummnorBySummnorName(String summonerName) {
        return summonerInformationRepository.findCurrentSummnorBySummnorName(summonerName);
    }

    public SummonerGameGrade getGameGradeBySummnorName(String accurateSummnorNameSummonerName){
        return summonerGameGradeRepository.findGameGradeBySummnorName(accurateSummnorNameSummonerName);
    }
}