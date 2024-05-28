package io.github.dej2vu.domain.raffle.model;

import io.github.dej2vu.constant.enums.ActionType;
import io.github.dej2vu.constant.enums.RuleModel;
import lombok.*;


/**
 * @description 规则动作实体
 *
 * @author dej2vu
 * @create 2024-05-28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleAction<T extends RuleAction.Action> {

    private RuleModel ruleModel;
    private ActionType actionType;
    private T data;

    static public class Action {

    }

    // 前置动作
    @EqualsAndHashCode(callSuper = true)
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static public class PreAction extends Action {
        /**
         * 策略编码
         */
        private String strategyCode;

        /**
         * 权重值Key；用于抽奖时可以选择权重抽奖。
         */
        private String weightKey;

        /**
         * 奖品编码
         */
        private String prizeCode;
    }

    // 中置动作
    static public class InAction extends Action {

    }

    // 后置动作
    static public class PostAction extends Action {

    }

}
