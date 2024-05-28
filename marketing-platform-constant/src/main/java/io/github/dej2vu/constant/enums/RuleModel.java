package io.github.dej2vu.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RuleModel {

    WEIGHT("weight","【抽奖前规则】根据抽奖权重返回可抽奖范围KEY"),
    BLACKLIST("blacklist","【抽奖前规则】黑名单规则过滤，命中黑名单则直接返回"),
    ;

    private final String code;
    private final String info;

}