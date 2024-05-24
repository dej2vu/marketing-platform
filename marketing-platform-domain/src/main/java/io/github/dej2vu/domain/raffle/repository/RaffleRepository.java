package io.github.dej2vu.domain.raffle.repository;

import io.github.dej2vu.domain.raffle.model.RaffleStrategyPrize;

import java.util.List;
import java.util.Map;

public interface RaffleRepository {

    List<RaffleStrategyPrize> findStrategyPrizeByStrategyCode(String strategyCode);

    void cachePrizeSearchTable(String strategyCode, Map<Integer, String> prizeSearchTable);

    int getRateRange(String strategyCode);

    String getPrizeCodeFromPrizeSearchTable(String strategyCode, Integer rateKey);

}
