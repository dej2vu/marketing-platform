package io.github.dej2vu.domain.raffle.service;

public interface RaffleService {

    /**
     * 装配抽奖策略配置「触发的时机可以为活动审核通过后进行调用」
     *
     * @param strategyCode 策略编码
     * @return 装配结果
     */
    boolean assembleStrategy(String strategyCode);

    /**
     * 获取抽奖策略装配的随机结果
     *
     * @param strategyCode 策略编码
     * @return prizeCode 奖品编码
     */
    String getRandomResult(String strategyCode);

}
