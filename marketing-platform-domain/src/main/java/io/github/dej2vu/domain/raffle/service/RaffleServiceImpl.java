package io.github.dej2vu.domain.raffle.service;

import io.github.dej2vu.domain.raffle.model.RaffleStrategyPrize;
import io.github.dej2vu.domain.raffle.repository.RaffleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RaffleServiceImpl implements RaffleService {

    private final RaffleRepository raffleRepository;

    @Override
    public boolean assembleStrategy(String strategyCode) {
        // 1. 获取策略配置
        List<RaffleStrategyPrize> strategyPrizes = raffleRepository.findStrategyPrizeByStrategyCode(strategyCode);

        // 2. 构造奖品查找表
        Map<Integer, String> prizeSearchTable = buildPrizeSearchTable(strategyPrizes);

        // 3. 存储至 Redis
        raffleRepository.cachePrizeSearchTable(strategyCode, prizeSearchTable);
        return true;
    }

    private Map<Integer, String> buildPrizeSearchTable(List<RaffleStrategyPrize> strategyPrizes){
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
        return IntStream.range(0, prizeSearchTable.size())
                .boxed()
                .collect(Collectors.toMap(i -> i, prizeSearchTable::get));
    }

    @Override
    public String getRandomResult(String strategyCode) {

        int rateRange = raffleRepository.getRateRange(strategyCode);

        return raffleRepository.getPrizeCodeFromPrizeSearchTable(strategyCode, new SecureRandom().nextInt(rateRange));
    }
}
