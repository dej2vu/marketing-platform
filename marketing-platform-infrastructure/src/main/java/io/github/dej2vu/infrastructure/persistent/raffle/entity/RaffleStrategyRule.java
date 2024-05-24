package io.github.dej2vu.infrastructure.persistent.raffle.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description 抽奖策略规则表
 *
 * @author dej2vu
 * @create 2024-05-24
 */
@Data
public class RaffleStrategyRule {

    /** 自增ID */
    private Long id;
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
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}
