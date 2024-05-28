package io.github.dej2vu.domain.raffle.service.rule;

import io.github.dej2vu.domain.raffle.model.RuleAction;
import io.github.dej2vu.domain.raffle.model.RuleMaterial;

/**
 * @description 抽奖规则处理器接口
 *
 * @author dej2vu
 * @create 2024-05-28
 */
public interface RuleHandler<T extends RuleAction.Action> {

    RuleAction<T> handle(RuleMaterial ruleMaterial);

}
