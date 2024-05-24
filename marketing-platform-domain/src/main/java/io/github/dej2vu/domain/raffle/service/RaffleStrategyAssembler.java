package io.github.dej2vu.domain.raffle.service;

public interface RaffleStrategyAssembler {

    /**
     * 装配抽奖策略配置「触发的时机可以为活动审核通过后进行调用」
     *
     * @param strategyCode 策略编码
     * @return 装配结果
     */
    boolean assemble(String strategyCode);

}
