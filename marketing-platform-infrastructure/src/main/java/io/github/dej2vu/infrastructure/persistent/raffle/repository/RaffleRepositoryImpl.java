package io.github.dej2vu.infrastructure.persistent.raffle.repository;

import io.github.dej2vu.constant.Constants;
import io.github.dej2vu.domain.raffle.model.RaffleStrategy;
import io.github.dej2vu.domain.raffle.model.RaffleStrategyPrize;
import io.github.dej2vu.domain.raffle.model.RaffleStrategyRule;
import io.github.dej2vu.domain.raffle.repository.RaffleRepository;
import io.github.dej2vu.infrastructure.persistent.raffle.convertor.RaffleStrategyPrizeConvertor;
import io.github.dej2vu.infrastructure.persistent.raffle.mapper.RaffleStrategyMapper;
import io.github.dej2vu.infrastructure.persistent.raffle.mapper.RaffleStrategyPrizeMapper;
import io.github.dej2vu.infrastructure.persistent.raffle.mapper.RaffleStrategyRuleMapper;
import io.github.dej2vu.infrastructure.persistent.raffle.model.RaffleStrategyPO;
import io.github.dej2vu.infrastructure.persistent.raffle.model.RaffleStrategyPrizePO;
import io.github.dej2vu.infrastructure.persistent.raffle.model.RaffleStrategyRulePO;
import io.github.dej2vu.infrastructure.redis.RedisService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Repository
public class RaffleRepositoryImpl implements RaffleRepository {

    @Resource
    private RaffleStrategyMapper raffleStrategyMapper;
    @Resource
    private RaffleStrategyPrizeMapper raffleStrategyPrizeMapper;
    @Resource
    private RaffleStrategyRuleMapper raffleStrategyRuleMapper;
    @Resource
    private RedisService redisService;


    @Override
    public RaffleStrategy findStrategyByStrategyCode(String strategyCode) {

        RaffleStrategyPO strategy = raffleStrategyMapper.findByCode(strategyCode);
        return RaffleStrategy.builder()
                .code(strategy.getCode())
                .ruleModels(strategy.getRuleModels())
                .description(strategy.getDescription())
                .build();
    }

    @Override
    public List<RaffleStrategyPrize> findStrategyPrizeByStrategyCode(String strategyCode) {

        // 优先从缓存获取
        String cacheKey = Constants.RedisKey.RAFFLE_STRATEGY_PRIZE_LIST_KEY + strategyCode;
        List<RaffleStrategyPrize> cachedStrategyPrizes = redisService.getValue(cacheKey);

        if (!CollectionUtils.isEmpty(cachedStrategyPrizes)) {return cachedStrategyPrizes;}

        List<RaffleStrategyPrizePO> rawList = raffleStrategyPrizeMapper.findByStrategyCode(strategyCode);
        List<RaffleStrategyPrize> list = RaffleStrategyPrizeConvertor.convert(rawList);
        redisService.setValue(cacheKey, list);
        return list;
    }


    @Override
    public RaffleStrategyRule findStrategyRuleByStrategyCodeAndRuleModel(String strategyCode, String ruleModel) {

        RaffleStrategyRulePO record = raffleStrategyRuleMapper.findByStrategyCodeAndRuleModel(strategyCode, ruleModel);
        return RaffleStrategyRule.builder()
                .strategyCode(record.getStrategyCode())
                .prizeCode(record.getPrizeCode())
                .type(record.getType())
                .model(record.getModel())
                .value(record.getValue())
                .description(record.getDescription())
                .build();
    }

    @Override
    public void cachePrizeSearchTable(String key, Map<Integer, String> prizeSearchTable) {
        // 1. 存储抽奖策略范围值，如10000，用于生成1000以内的随机数
        redisService.setValue(Constants.RedisKey.RAFFLE_STRATEGY_RATE_RANGE_KEY + key, prizeSearchTable.size());
        // 2. 存储概率查找表
        Map<Integer, String> cachePrizeSearchTable = redisService.getMap(Constants.RedisKey.RAFFLE_STRATEGY_PRIZE_SEARCH_TABLE_KEY + key);
        cachePrizeSearchTable.putAll(prizeSearchTable);
    }

    @Override
    public int getRateRange(String key) {
        return redisService.getValue(Constants.RedisKey.RAFFLE_STRATEGY_RATE_RANGE_KEY + key);
    }

    @Override
    public String getPrizeCodeFromPrizeSearchTable(String strategyCode, Integer rateKey) {
        return redisService.getFromMap(Constants.RedisKey.RAFFLE_STRATEGY_PRIZE_SEARCH_TABLE_KEY + strategyCode, rateKey);
    }
}
