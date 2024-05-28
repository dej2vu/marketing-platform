package io.github.dej2vu.domain.raffle.service.rule.annotation;


import io.github.dej2vu.constant.enums.RuleModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description HandlerTag 自定义注解
 *
 * @author dej2vu
 * @create 2024-05-28
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerTag {

    RuleModel ruleModel();

}
