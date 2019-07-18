package org.ajou.realcoading.riotgamesapi.riotgamesapi.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class SummonerInformation {

    private int profileIconId;
    @Id
    private String name;
    private String puuid;
    private long summonerLevel;
    private String id;
    private String accountId;
    private long revisionDate;

}
