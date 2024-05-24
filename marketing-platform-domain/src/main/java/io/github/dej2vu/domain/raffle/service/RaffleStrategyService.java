package io.github.dej2vu.domain.raffle.service;

import io.github.dej2vu.domain.raffle.model.RaffleStrategy;
import io.github.dej2vu.domain.raffle.model.RaffleStrategyPrize;
import io.github.dej2vu.domain.raffle.model.RaffleStrategyRule;
import io.github.dej2vu.domain.raffle.repository.RaffleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RaffleStrategyService implements RaffleStrategyAssembler, RaffleStrategyDispatcher {

    private final RaffleRepository raffleRepository;

    @Override
    public boolean assemble(String strategyCode) {
        // 1. 获取策略配置
        List<RaffleStrategyPrize> strategyPrizes = raffleRepository.findStrategyPrizeByStrategyCode(strategyCode);
        // 2. 根据 strategyCode 构造奖品查找表
        assemblePrizeSearchTable(strategyCode, strategyPrizes);

        RaffleStrategy raffleStrategy = raffleRepository.findStrategyByStrategyCode(strategyCode);
        String ruleWeight = raffleStrategy.getRuleWeight();
        if (Objects.isNull(ruleWeight)) return true;

        RaffleStrategyRule raffleStrategyRule = raffleRepository.findStrategyRuleByStrategyCodeAndRuleModel(strategyCode, ruleWeight);
        if (Objects.isNull(raffleStrategyRule)) {
            throw new RuntimeException();
        }
        Map<String, List<String>> ruleWeightValueMap = raffleStrategyRule.getRuleWeightValues();
        Set<String> keys = ruleWeightValueMap.keySet();
        for (String key : keys) {
            List<String> ruleWeightValues = ruleWeightValueMap.get(key);
            List<RaffleStrategyPrize> strategyPrizesClone = new ArrayList<>(strategyPrizes);
            strategyPrizesClone.removeIf(entity -> !ruleWeightValues.contains(entity.getPrizeCode()));
            assemblePrizeSearchTable(String.valueOf(strategyCode).concat("_").concat(key), strategyPrizesClone);
        }

        return true;
    }

    private void assemblePrizeSearchTable(String key, List<RaffleStrategyPrize> strategyPrizes){
        // 1. 获取 minRate
        BigDecimal minRate = strategyPrizes.stream()
                .map(RaffleStrategyPrize::getRate)
                .min(BigDecimal::compareTo)
                .orElseThrow();

        // 2. 获取 totalRate
        BigDecimal totalRate = strategyPrizes.stream().map(RaffleStrategyPrize::getRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3. 用 totalRate % minRate 获得概率范围，百分位、千分位、万分位
        BigDecimal rateRange = totalRate.divide(minRate, 0, RoundingMode.CEILING);

        // 4. 根据奖品概率生成奖品查询表
        List<String> prizeSearchTable = new ArrayList<>(rateRange.intValue());
        for (RaffleStrategyPrize strategyPrize : strategyPrizes) {
            String prizeCode = strategyPrize.getPrizeCode();
            BigDecimal rate = strategyPrize.getRate();
            // 计算出每个概率值需要存放到查找表的数量，循环填充
            for (int i = 0; i < rateRange.multiply(rate).setScale(0, RoundingMode.CEILING).intValue(); i++) {
                prizeSearchTable.add(prizeCode);
            }
        }

        // 5. 对奖品进行乱序处理
        Collections.shuffle(prizeSearchTable);

        // 6. 生成奖品查找表(HashMap), key为乱序后的index，value为奖品编码，后续可使用随机值作为key来查找对用的奖品编码
        Map<Integer, String> shuffledPrizeSearchTable =  IntStream.range(0, prizeSearchTable.size())
                .boxed()
                .collect(Collectors.toMap(i -> i, prizeSearchTable::get));


        // 3. 存储至 Redis
        raffleRepository.cachePrizeSearchTable(key, shuffledPrizeSearchTable);
    }

    @Override
    public String dispatchWithRandom(String strategyCode) {

        int rateRange = raffleRepository.getRateRange(strategyCode);

        return raffleRepository.getPrizeCodeFromPrizeSearchTable(strategyCode, new SecureRandom().nextInt(rateRange));
    }

    @Override
    public String dispatchWithRuleWeightValue(String strategyCode, String ruleWeightValue) {
        String key = String.valueOf(strategyCode).concat("_").concat(ruleWeightValue);
        int rateRange = raffleRepository.getRateRange(key);
        // 通过生成的随机值，获取概率值奖品查找表的结果
        return raffleRepository.getPrizeCodeFromPrizeSearchTable(key, new SecureRandom().nextInt(rateRange));
    }
}
