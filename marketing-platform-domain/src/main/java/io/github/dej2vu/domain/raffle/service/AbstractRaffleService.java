package io.github.dej2vu.domain.raffle.service;


import io.github.dej2vu.constant.enums.ActionType;
import io.github.dej2vu.constant.enums.RuleModel;
import io.github.dej2vu.domain.raffle.model.RaffleFactor;
import io.github.dej2vu.domain.raffle.model.RafflePrize;
import io.github.dej2vu.domain.raffle.model.RaffleStrategy;
import io.github.dej2vu.domain.raffle.model.RuleAction;
import io.github.dej2vu.domain.raffle.repository.RaffleRepository;
import io.github.dej2vu.domain.raffle.service.strategy.RaffleStrategyDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @description 抽奖业务抽象类，定义抽奖的标准流程
 *
 * @author dej2vu
 * @create 2024-05-28
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractRaffleService implements RaffleService {

    protected final RaffleRepository raffleRepository;
    protected final RaffleStrategyDispatcher raffleStrategyDispatcher;

    @Override
    public RafflePrize run(RaffleFactor raffleFactor) {
        // 1. 参数校验
        raffleFactor.checkArguments();
        String strategyCode = raffleFactor.getStrategyCode();

        // 2. 获取对应策略配置
        RaffleStrategy raffleStrategy = raffleRepository.findStrategyByStrategyCode(strategyCode);

        // 3. 抽奖 - 前置规则过滤
        RuleAction<RuleAction.PreAction> preAction = doRreAction(raffleFactor, raffleStrategy.ruleModels());

        if (ActionType.TAKE_OVER.equals(preAction.getActionType())) {
            RuleAction.PreAction data = preAction.getData();

            if (RuleModel.BLACKLIST.equals(preAction.getRuleModel())) {
                // 黑名单返回固定的奖品ID
                return RafflePrize.builder()
                        .prizeCode(data.getPrizeCode())
                        .build();
            } else if (RuleModel.WEIGHT.equals(preAction.getRuleModel())) {
                // 权重根据返回的信息进行抽奖
                String weightKey = data.getWeightKey();
                String prizeCode = raffleStrategyDispatcher.dispatchWithWeight(strategyCode, weightKey);
                return RafflePrize.builder()
                        .prizeCode(prizeCode)
                        .build();
            }
        }


        // 4. 执行默认抽奖逻辑
        String prizeCode = raffleStrategyDispatcher.dispatchWithRandom(strategyCode);
        return RafflePrize.builder()
                .prizeCode(prizeCode)
                .build();
    }

    protected abstract RuleAction<RuleAction.PreAction> doRreAction(RaffleFactor raffleFactor, String... ruleModels);


}
