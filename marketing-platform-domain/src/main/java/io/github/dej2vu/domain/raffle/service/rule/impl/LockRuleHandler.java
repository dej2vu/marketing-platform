package io.github.dej2vu.domain.raffle.service.rule.impl;


import com.google.common.base.Strings;
import io.github.dej2vu.constant.enums.ActionType;
import io.github.dej2vu.domain.raffle.model.RuleAction;
import io.github.dej2vu.domain.raffle.model.RuleMaterial;
import io.github.dej2vu.domain.raffle.repository.RaffleRepository;
import io.github.dej2vu.domain.raffle.service.rule.RuleHandler;
import io.github.dej2vu.domain.raffle.service.rule.annotation.HandlerTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.github.dej2vu.constant.enums.RuleModel.LOCK;


/**
 * @description 【中置规则】- 用户抽奖n次后，解锁对应奖品
 *
 * @author dej2vu
 * @create 2024-05-29
 */
@Component
@HandlerTag(ruleModel = LOCK)
@RequiredArgsConstructor
public class LockRuleHandler implements RuleHandler<RuleAction.PreAction> {

    private final RaffleRepository raffleRepository;

    public Integer userRaffleCount = 2;

    @Override
    public RuleAction<RuleAction.PreAction> handle(RuleMaterial ruleMaterial) {

        String userCode = ruleMaterial.getUserCode();
        String strategyCode = ruleMaterial.getStrategyCode();
        String prizeCode = ruleMaterial.getPrizeCode();

        // 查询规则值配置
        String ruleValue = raffleRepository.findRuleValue(strategyCode, prizeCode, LOCK);

        // 如果规则值为空，直接放行
        if (Strings.isNullOrEmpty(ruleValue)) {
            return RuleAction.<RuleAction.PreAction>builder()
                    .actionType(ActionType.LET_PASS)
                    .build();
        }
        int unlockRequiredCount = Integer.parseInt(ruleValue);

        // 1. 根据用户编码查询用户已抽奖次数，本章节我们先写死为固定的值。后续需要从数据库中查询
        // 2. 如果 userRaffleCount >= unlockRequiredCount，则放行
        if (userRaffleCount >= unlockRequiredCount) {
            return RuleAction.<RuleAction.PreAction>builder()
                    .actionType(ActionType.LET_PASS)
                    .build();
        }

        // 3. 不满足，则接管
        return RuleAction.<RuleAction.PreAction>builder()
                .actionType(ActionType.TAKE_OVER)
                .build();
    }

}
