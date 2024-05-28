package io.github.dej2vu.domain.raffle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RafflePrize {

    /** 策略编码 */
    private String strategyCode;
    /** 奖品编码 */
    private String prizeCode;
    /** 奖品对接标识 - 每一个都是一个对应的发奖策略 */
    private String prizeKey;
    /** 奖品配置信息 */
    private String prizeConfig;
    /** 奖品内容描述 */
    private String prizeDesc;

}
