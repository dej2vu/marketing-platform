package io.github.dej2vu.infrastructure.persistent.raffle.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @description 抽奖策略奖品明细配置表 - 概率、规则
 *
 * @author dej2vu
 * @create 2024-05-24
 */
@Data
public class RaffleStrategyPrizePO {

    /** 自增ID */
    private Long id;
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
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date updateTime;

}
