package io.github.dej2vu.infrastructure.persistent.raffle.convertor;

import io.github.dej2vu.domain.raffle.model.RaffleStrategyPrize;
import io.github.dej2vu.infrastructure.persistent.raffle.model.RaffleStrategyPrizePO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RaffleStrategyPrizeConvertor {

    public static List<RaffleStrategyPrize> convert(List<RaffleStrategyPrizePO> list){
        return list.stream().map(RaffleStrategyPrizeConvertor::convert).collect(Collectors.toList());
    }

    public static RaffleStrategyPrize convert(RaffleStrategyPrizePO record){
        return RaffleStrategyPrize.builder()
                .prizeCode(record.getPrizeCode())
                .rate(record.getRate())
                .build();
    }
}
