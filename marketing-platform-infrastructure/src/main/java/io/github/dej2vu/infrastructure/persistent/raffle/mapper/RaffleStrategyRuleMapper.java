package io.github.dej2vu.infrastructure.persistent.raffle.mapper;

import io.github.dej2vu.infrastructure.persistent.raffle.model.RaffleStrategyRulePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 抽奖策略规则表Mapper
 *
 * @author dej2vu
 * @create 2024-05-24
 */
@Mapper
public interface RaffleStrategyRuleMapper {

    List<RaffleStrategyRulePO> findAll();

    RaffleStrategyRulePO findByStrategyCodeAndRuleModel(@Param("strategyCode") String strategyCode, @Param("ruleModel") String ruleModel);

}
