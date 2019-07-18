package org.ajou.realcoading.riotgamesapi.riotgamesapi.controller;

import org.ajou.realcoading.riotgamesapi.riotgamesapi.service.SummonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/RiotGamesApi")
public class RiotApiController {

    @Autowired
    private SummonerService summonerService;

    @GetMapping("available-summonerName")
    public List<String> getAvailableSummonerName() throws IOException{
        return summonerService.getAvailableSummonerNames();
    }

}
