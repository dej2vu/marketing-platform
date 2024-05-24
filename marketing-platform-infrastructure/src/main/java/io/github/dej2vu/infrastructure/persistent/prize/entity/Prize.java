package io.github.dej2vu.infrastructure.persistent.prize.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description 奖品表
 *
 * @author dej2vu
 * @create 2024-05-24
 */
@Data
public class Prize {

    /** 自增ID */
    private Long id;
    /** 奖品编码 - 内部流转使用 */
    private String code;
    /** 奖品对接标识 - 每一个都是一个对应的发奖策略 */
    private String key;
    /** 奖品配置信息 */
    private String config;
    /** 奖品内容描述 */
    private String description;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}
