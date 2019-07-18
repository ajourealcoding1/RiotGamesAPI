package org.ajou.realcoading.riotgamesapi.riotgamesapi.controller;

import org.ajou.realcoading.riotgamesapi.riotgamesapi.service.SummonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/RiotGamesApi")
public class RiotApiController {

    @Autowired
    private SummonerService summonerService;
}
