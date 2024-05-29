package io.github.dej2vu.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RuleModel {

    WEIGHT("weight","pre_action","【前置规则】根据抽奖权重返回可抽奖范围KEY"),
    BLACKLIST("blacklist","pre_action","【前置规则】黑名单规则过滤，命中黑名单则直接返回"),
    LOCK("lock","in_action","【中置规则】用户抽奖n次后，解锁对应奖品"),
    LUCKY("lucky","post_action","【中置规则】用户抽奖n次后，解锁对应奖品"),
    ;

    private final String code;
    private final String actionPoint;
    private final String info;

    public static RuleModel codeOf(String code){
        return RuleModel.valueOf(code.toUpperCase());
    }

    public static boolean isPreAction(RuleModel ruleModel){
        return "pre_action".equals(ruleModel.actionPoint);
    }

    public static boolean isInAction(RuleModel ruleModel){
        return "in_action".equals(ruleModel.actionPoint);
    }

    public static boolean isPostAction(RuleModel ruleModel){
        return "post_action".equals(ruleModel.actionPoint);
    }

}