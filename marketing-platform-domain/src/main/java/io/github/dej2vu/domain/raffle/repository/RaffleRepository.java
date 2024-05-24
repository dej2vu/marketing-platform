package io.github.dej2vu.domain.raffle.repository;

import io.github.dej2vu.domain.raffle.model.RaffleStrategy;
import io.github.dej2vu.domain.raffle.model.RaffleStrategyPrize;
import io.github.dej2vu.domain.raffle.model.RaffleStrategyRule;

import java.util.List;
import java.util.Map;

public interface RaffleRepository {

    RaffleStrategy findStrategyByStrategyCode(String strategyCode);

    List<RaffleStrategyPrize> findStrategyPrizeByStrategyCode(String strategyCode);

    RaffleStrategyRule findStrategyRuleByStrategyCodeAndRuleModel(String strategyCode, String ruleModel);

    void cachePrizeSearchTable(String key, Map<Integer, String> prizeSearchTable);

    int getRateRange(String strategyCode);

    String getPrizeCodeFromPrizeSearchTable(String strategyCode, Integer rateKey);

}
