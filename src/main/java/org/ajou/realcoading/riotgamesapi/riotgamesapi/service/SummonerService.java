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
    private LinkedList<String> summonerNamesQueue = new LinkedList<>();

    public List<String> getAvailableSummonerNames() throws IOException {
        File availableSummonerNamesFile = new File("Riotgames-API");
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(availableSummonerNamesFile, new TypeReference<List<String>>() {
        });
    }

    @Scheduled(fixedDelay = 2000L)
    public void getSummnorGameGradePeriodicallyByCityName() throws IOException {
        if (summonerNamesQueue.isEmpty()) {
            List<String> availableSummonerNames = this.getAvailableSummonerNames();
            summonerNamesQueue.addAll(availableSummonerNames);
        }

        String summonerName = summonerNamesQueue.pop();
        summonerNamesQueue.add(summonerName);

        SummonerInformation summonerInformation = riotGamesApiClient.getSummonerInformation(summonerName);
        String encryptedId = summonerInformation.getId();

        List<SummonerGameGrade> summonerGameGradeList = riotGamesApiClient.getSummonerGameGrade(encryptedId);

        SummonerGameGrade SummonerGameGrade = summonerGameGradeList.remove(0);

        SummonerGameGrade SummonerGameGradeFromDb = summonerGameGradeRepository.findGameGradeBySummonerName(summonerName);


        if ((SummonerGameGrade != null && SummonerGameGradeFromDb == null) || !SummonerGameGrade.equals(SummonerGameGradeFromDb)) {
            SummonerGameGrade insertedOrUpdatedSummonerGameGrade = summonerGameGradeRepository.insertOrUpdatedSummonerGameGrade(SummonerGameGrade);
            log.info("SummonerGameGrade has inserted or updated successfully. SummonerGameGrade : {}", insertedOrUpdatedSummonerGameGrade);
        } else {
            log.info("SummonerGameGrade and SummonerGameGradeFromDb are the same");
        }
    }

    public SummonerInformation getCurrentSummnorBySummonerName(String summonerName) {
        return summonerInformationRepository.findCurrentSummonerBySummonerName(summonerName);
    }

    public SummonerGameGrade getGameGradeBySummonerName(String accurateSummonerName) {
        return summonerGameGradeRepository.findGameGradeBySummonerName(accurateSummonerName);
    }
}