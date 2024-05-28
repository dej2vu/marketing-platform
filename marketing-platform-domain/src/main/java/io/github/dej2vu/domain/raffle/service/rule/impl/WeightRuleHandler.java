package io.github.dej2vu.domain.raffle.service.rule.impl;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.github.dej2vu.constant.Constants;
import io.github.dej2vu.constant.enums.ActionType;
import io.github.dej2vu.domain.raffle.model.RuleAction;
import io.github.dej2vu.domain.raffle.model.RuleMaterial;
import io.github.dej2vu.domain.raffle.repository.RaffleRepository;
import io.github.dej2vu.domain.raffle.service.rule.RuleHandler;
import io.github.dej2vu.domain.raffle.service.rule.annotation.HandlerTag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.*;

import static io.github.dej2vu.constant.enums.RuleModel.WEIGHT;


/**
 * @author dej2vu
 * @description 【前置规则】- 根据抽奖权重返回可抽奖范围KEY
 * @create 2024-05-28
 */
@Component
@HandlerTag(ruleModel = WEIGHT)
@RequiredArgsConstructor
public class WeightRuleHandler implements RuleHandler<RuleAction.PreAction> {

    private final RaffleRepository raffleRepository;

    public Long userScore = 4500L;

    /**
     * 权重规则过滤；
     * 1. 权重规则格式；4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
     * 2. 解析数据格式；判断哪个范围符合用户的特定抽奖范围
     *
     * @param ruleMaterial 规则物料实体对象
     * @return 规则过滤结果
     */
    @Override
    public RuleAction<RuleAction.PreAction> handle(RuleMaterial ruleMaterial) {

        String userCode = ruleMaterial.getUserCode();
        String strategyCode = ruleMaterial.getStrategyCode();

        // 查询权重规则值配置
        String ruleValue = raffleRepository.findWeightRuleValueByStrategyCode(strategyCode);

        // 权重规则值为空，直接放行
        if (Strings.isNullOrEmpty(ruleValue)) {
            return RuleAction.<RuleAction.PreAction>builder()
                    .actionType(ActionType.LET_PASS)
                    .build();
        }
        // 1. 根据用户ID查询用户抽奖消耗的积分值，本章节我们先写死为固定的值。后续需要从数据库中查询。
        Map<Long, String> ruleValueGroup = analyticalValue(ruleValue);
        if (MapUtils.isEmpty(ruleValueGroup)) {
            return RuleAction.<RuleAction.PreAction>builder()
                    .actionType(ActionType.LET_PASS)
                    .build();
        }

        // 2. 转换Keys值，并倒序排序，再找出最大符合的值
        // 也就是 4500 匹配到 4000:102,103,104,105; 5000 匹配到 5000:102,103,104,105,106,107
        Optional<Long> matchedKeyOpt = ruleValueGroup.keySet().stream()
                .sorted(Comparator.reverseOrder())
                .toList()
                .stream()
                .filter(key -> userScore >= key)
                .findFirst();

        if(matchedKeyOpt.isPresent()){

            RuleAction.PreAction data = RuleAction.PreAction.builder()
                    .strategyCode(strategyCode)
                    .weightKey(ruleValueGroup.get(matchedKeyOpt.get()))
                    .build();

            return RuleAction.<RuleAction.PreAction>builder()
                    .ruleModel(WEIGHT)
                    .actionType(ActionType.TAKE_OVER)
                    .data(data)
                    .build();
        }

        // 未找到匹配的分值时，直接放行
        return RuleAction.<RuleAction.PreAction>builder()
                .actionType(ActionType.LET_PASS)
                .build();
    }

    private Map<Long, String> analyticalValue(String ruleValue) {
        if (Strings.isNullOrEmpty(ruleValue)) {
            return Collections.emptyMap();
        }
        String[] ruleValueGroups = ruleValue.split(Constants.SPACE);
        Map<Long, String> ruleValueMap = new HashMap<>();
        for (String ruleValueKey : ruleValueGroups) {
            // 检查输入是否为空
            if (ruleValueKey == null || ruleValueKey.isEmpty()) {
                return ruleValueMap;
            }
            // 分割字符串以获取键和值
            String[] parts = ruleValueKey.split(Constants.COLON);
            Preconditions.checkArgument(parts.length == 2,
                    "weight rule invalid input format: %s", ruleValueKey);
            ruleValueMap.put(Long.parseLong(parts[0]), ruleValueKey);
        }
        return ruleValueMap;
    }

}
