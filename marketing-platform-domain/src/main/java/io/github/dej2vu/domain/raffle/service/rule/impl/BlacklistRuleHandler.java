package io.github.dej2vu.domain.raffle.service.rule.impl;


import com.google.common.base.Strings;
import io.github.dej2vu.constant.Constants;
import io.github.dej2vu.constant.enums.ActionType;
import io.github.dej2vu.domain.raffle.model.RuleAction;
import io.github.dej2vu.domain.raffle.model.RuleMaterial;
import io.github.dej2vu.domain.raffle.repository.RaffleRepository;
import io.github.dej2vu.domain.raffle.service.rule.RuleHandler;
import io.github.dej2vu.domain.raffle.service.rule.annotation.HandlerTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static io.github.dej2vu.constant.enums.RuleModel.BLACKLIST;


/**
 * @description 【前置规则】- 黑名单用户过滤规则
 *
 * @author dej2vu
 * @create 2024-05-28
 */
@Component
@HandlerTag(ruleModel = BLACKLIST)
@RequiredArgsConstructor
public class BlacklistRuleHandler implements RuleHandler<RuleAction.PreAction> {

    private final RaffleRepository raffleRepository;

    @Override
    public RuleAction<RuleAction.PreAction> handle(RuleMaterial ruleMaterial) {

        String userCode = ruleMaterial.getUserCode();
        String strategyCode = ruleMaterial.getStrategyCode();

        // 查询黑名单规则值配置
        String ruleValue = raffleRepository.findBlacklistRuleValueByStrategyCode(strategyCode);

        // 黑名单规则值为空，直接放行
        if (Strings.isNullOrEmpty(ruleValue)){
            return RuleAction.<RuleAction.PreAction>builder()
                    .actionType(ActionType.LET_PASS)
                    .build();
        }

        // {prizeCode}:{userCode},{userCode},{userCode}
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        String prizeCode = splitRuleValue[0];

        // 提取黑名单用户编码，并判断是否包含当前 userCode
        String[] blacklist = splitRuleValue[1].split(Constants.SPLIT);
        boolean contains = Arrays.asList(blacklist).contains(userCode);

        // 如果当前 userCode 属于黑名单，则接管
        if(contains){

            RuleAction.PreAction data = RuleAction.PreAction.builder()
                    .strategyCode(strategyCode)
                    .prizeCode(prizeCode)
                    .build();

            return RuleAction.<RuleAction.PreAction>builder()
                    .ruleModel(BLACKLIST)
                    .actionType(ActionType.TAKE_OVER)
                    .data(data)
                    .build();
        }

        // 当前 userCode 不属于黑名单，直接放行
        return RuleAction.<RuleAction.PreAction>builder()
                .actionType(ActionType.LET_PASS)
                .build();
    }

}
