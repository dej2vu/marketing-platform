package io.github.dej2vu.domain.raffle.model;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @description 抽奖因子实体
 *
 * @author dej2vu
 * @create 2024-05-28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleFactor {

    /** 用户编码 */
    private String userCode;
    /** 策略编码 */
    private String strategyCode;


    public void checkArguments(){
        Preconditions.checkArgument(Strings.isNullOrEmpty(userCode));
        Preconditions.checkArgument(Strings.isNullOrEmpty(strategyCode));
    }
}
