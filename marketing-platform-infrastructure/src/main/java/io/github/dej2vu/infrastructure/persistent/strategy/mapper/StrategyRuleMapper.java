package io.github.dej2vu.infrastructure.persistent.strategy.mapper;

import io.github.dej2vu.infrastructure.persistent.strategy.entity.StrategyRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description 抽奖策略规则表Mapper
 *
 * @author dej2vu
 * @create 2024-05-24
 */
@Mapper
public interface StrategyRuleMapper {

    List<StrategyRule> findAll();

}
