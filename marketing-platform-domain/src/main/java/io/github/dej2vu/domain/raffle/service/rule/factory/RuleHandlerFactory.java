package io.github.dej2vu.domain.raffle.service.rule.factory;

import io.github.dej2vu.domain.raffle.model.RuleAction;
import io.github.dej2vu.domain.raffle.service.rule.RuleHandler;
import io.github.dej2vu.domain.raffle.service.rule.annotation.HandlerTag;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 规则处理器工厂
 *
 * @author dej2vu
 * @create 2025-05-28
 */
@Service
public class RuleHandlerFactory {

    public Map<String, RuleHandler<?>> ruleHandlerMap = new ConcurrentHashMap<>();

    public RuleHandlerFactory(List<RuleHandler<?>> ruleHandlers) {
        ruleHandlers.forEach(logic -> {
            HandlerTag handlerTag = AnnotationUtils.findAnnotation(logic.getClass(), HandlerTag.class);
            if (Objects.nonNull(handlerTag)) {
                ruleHandlerMap.put(handlerTag.ruleModel().getCode(), logic);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends RuleAction.Action> Map<String, RuleHandler<T>> openRuleHandler() {
        return (Map<String, RuleHandler<T>>) (Map<?, ?>) ruleHandlerMap;
    }


}
