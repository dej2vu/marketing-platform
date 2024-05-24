package io.github.dej2vu.domain.raffle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleStrategyPrize {

    /** 抽奖策略编码 */
    private String strategyCode;
    /** 奖品编码 - 内部流转使用 */
    private String prizeCode;
    /** 奖品标题 */
    private String title;
    /** 奖品副标题 */
    private String subtitle;
    /** 奖品总库存量 */
    private Integer totalInventory;
    /** 奖品剩余库存量 */
    private Integer surplusInventory;
    /** 奖品中奖概率 */
    private BigDecimal rate;
    /** 规则模型，rule配置的模型同步到此表，便于使用 */
    private String ruleModels;
    /** 排序 */
    private Integer sort;

}
