package io.github.dej2vu.domain.raffle.model;

import io.github.dej2vu.constant.Constants;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleStrategy {

    /** 抽奖策略编码 */
    private String code;
    /** 规则模型，rule配置的模型同步到此表，便于使用 */
    private String ruleModels;
    /** 抽奖策略描述 */
    private String description;

    public String[] ruleModels() {
        if (StringUtils.isBlank(ruleModels)) return null;
        return ruleModels.split(Constants.SPLIT);
    }

    public String getWeightRuleModel() {
        String[] ruleModels = this.ruleModels();
        if (null == ruleModels) return null;
        for (String ruleModel : ruleModels) {
            if ("weight".equals(ruleModel)) return ruleModel;
        }
        return null;
    }

}
