package io.github.dej2vu.domain.raffle.service;


import io.github.dej2vu.domain.raffle.model.RaffleFactor;
import io.github.dej2vu.domain.raffle.model.RafflePrize;

/**
 * @description 抽奖接口
 *
 * @author dej2vu
 * @create 2024-05-28
 */
public interface RaffleService {

    /**
     * 执行抽奖；用抽奖因子入参，执行抽奖计算，返回奖品信息
     *
     * @param raffleFactor 抽奖因子实体对象，根据入参信息计算抽奖结果
     * @return 抽奖的奖品
     */
    RafflePrize run(RaffleFactor raffleFactor);

}
