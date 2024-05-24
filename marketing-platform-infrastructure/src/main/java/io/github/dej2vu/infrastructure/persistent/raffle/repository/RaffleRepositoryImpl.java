package io.github.dej2vu.infrastructure.persistent.raffle.repository;

import io.github.dej2vu.constant.Constants;
import io.github.dej2vu.domain.raffle.model.RaffleStrategyPrize;
import io.github.dej2vu.domain.raffle.repository.RaffleRepository;
import io.github.dej2vu.infrastructure.persistent.raffle.convertor.RaffleStrategyPrizeConvertor;
import io.github.dej2vu.infrastructure.persistent.raffle.mapper.RaffleStrategyPrizeMapper;
import io.github.dej2vu.infrastructure.redis.RedisService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Repository
public class RaffleRepositoryImpl implements RaffleRepository {

    @Resource
    private RaffleStrategyPrizeMapper raffleStrategyPrizeMapper;
    @Resource
    private RedisService redisService;

    @Override
    public List<RaffleStrategyPrize> findStrategyPrizeByStrategyCode(String strategyCode) {

        // 优先从缓存获取
        String cacheKey = Constants.RedisKey.RAFFLE_STRATEGY_PRIZE_LIST_KEY + strategyCode;
        List<RaffleStrategyPrize> cachedStrategyPrizes = redisService.getValue(cacheKey);

        if (!CollectionUtils.isEmpty(cachedStrategyPrizes)) {return cachedStrategyPrizes;}

        List<RaffleStrategyPrize> list = RaffleStrategyPrizeConvertor.convert(raffleStrategyPrizeMapper.findByStrategyCode(strategyCode));
        redisService.setValue(cacheKey, list);
        return list;
    }

    @Override
    public void cachePrizeSearchTable(String strategyCode, Map<Integer, String> prizeSearchTable) {
        // 1. 存储抽奖策略范围值，如10000，用于生成1000以内的随机数
        redisService.setValue(Constants.RedisKey.RAFFLE_STRATEGY_RATE_RANGE_KEY + strategyCode, prizeSearchTable.size());
        // 2. 存储概率查找表
        Map<Integer, String> cachePrizeSearchTable = redisService.getMap(Constants.RedisKey.RAFFLE_STRATEGY_PRIZE_SEARCH_TABLE_KEY + strategyCode);
        cachePrizeSearchTable.putAll(prizeSearchTable);
    }

    @Override
    public int getRateRange(String strategyCode) {
        return redisService.getValue(Constants.RedisKey.RAFFLE_STRATEGY_RATE_RANGE_KEY + strategyCode);
    }

    @Override
    public String getPrizeCodeFromPrizeSearchTable(String strategyCode, Integer rateKey) {
        return redisService.getFromMap(Constants.RedisKey.RAFFLE_STRATEGY_PRIZE_SEARCH_TABLE_KEY + strategyCode, rateKey);
    }
}
