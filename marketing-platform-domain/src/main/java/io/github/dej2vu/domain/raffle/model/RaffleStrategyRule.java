package io.github.dej2vu.domain.raffle.model;

import io.github.dej2vu.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleStrategyRule {

    /** 抽奖策略编码 */
    private String strategyCode;
    /** 奖品编码 - 内部流转使用【规则类型为策略，则不需要奖品ID】*/
    private String prizeCode;
    /** 抽奖规则类型；STRATEGY-策略规则、PRIZE-奖品规则 */
    private String type;
    /** 抽奖规则类型【random-随机值计算、lock-抽奖几次后解锁、lucky_prize-幸运奖(兜底奖品)】*/
    private String model;
    /** 抽奖规则比值 */
    private String value;
    /** 抽奖规则描述 */
    private String description;


    /**
     * 获取权重值
     * 数据案例；4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
     */
    public Map<String, List<String>> getWeightRuleValues() {
        if (!"weight".equals(model)) return null;
        String[] ruleValueGroups = value.split(Constants.SPACE);
        Map<String, List<String>> resultMap = new HashMap<>();
        for (String ruleValueGroup : ruleValueGroups) {
            // 检查输入是否为空
            if (ruleValueGroup == null || ruleValueGroup.isEmpty()) {
                return resultMap;
            }
            // 分割字符串以获取键和值
            String[] parts = ruleValueGroup.split(Constants.COLON);
            if (parts.length != 2) {
                throw new IllegalArgumentException("weight rule invalid input format" + ruleValueGroup);
            }
            // 解析值
            String[] valueStrings = parts[1].split(Constants.SPLIT);
            // 将键和值放入Map中
            resultMap.put(ruleValueGroup, Arrays.asList(valueStrings));
        }

        return resultMap;
    }

}
