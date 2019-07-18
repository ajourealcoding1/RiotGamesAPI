package org.ajou.realcoading.riotgamesapi.riotgamesapi.repository;

import org.ajou.realcoading.riotgamesapi.riotgamesapi.domain.SummonerInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class SummonerInformationRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public SummonerInformation insertOrUpdatedinformation(SummonerInformation summonerInformation) {
        return mongoTemplate.save(summonerInformation);
    }

    public SummonerInformation findCurrentSummnorBySummnorName(String summonerName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(summonerName));

        return mongoTemplate.findOne(query, SummonerInformation.class);
    }
}