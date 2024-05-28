package io.github.dej2vu.domain.raffle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleMaterial {

    /** 用户ID */
    private String userCode;
    /** 策略ID */
    private String strategyCode;
    /** 抽奖奖品ID【规则类型为策略，则不需要奖品ID】 */
    private String prizeCode;

}
