package io.github.dej2vu.infrastructure.persistent.strategy.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description 抽奖策略表
 *
 * @author dej2vu
 * @create 2024-05-24
 */
@Data
public class Strategy {

    /** 自增ID */
    private Long id;
    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖策略描述 */
    private String strategyDesc;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}
