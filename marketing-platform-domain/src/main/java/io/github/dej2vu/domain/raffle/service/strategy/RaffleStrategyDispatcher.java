package io.github.dej2vu.domain.raffle.service.strategy;

public interface RaffleStrategyDispatcher {

    /**
     * 获取抽奖策略装配的随机结果
     *
     * @param strategyCode 策略编码
     * @return prizeCode 奖品编码
     */
    String dispatchWithRandom(String strategyCode);

    String dispatchWithWeight(String strategyCode, String weightKey);

}
