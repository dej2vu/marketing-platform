package io.github.dej2vu.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description 逻辑过滤类型枚举
 *
 * @author dej2vu
 * @create 2024-05-28
 */
@Getter
@AllArgsConstructor
public enum ActionType {

    LET_PASS( "放行；执行后续的流程，不受规则引擎影响"),
    TAKE_OVER("接管；后续的流程，受规则引擎执行结果影响"),
    ;

    private final String info;

}
