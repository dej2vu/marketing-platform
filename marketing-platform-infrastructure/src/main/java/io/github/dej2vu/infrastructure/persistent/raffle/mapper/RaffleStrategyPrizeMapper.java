package io.github.dej2vu.infrastructure.persistent.raffle.mapper;

import io.github.dej2vu.infrastructure.persistent.raffle.model.RaffleStrategyPrizePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description 抽奖策略奖品明细配置表 - 概率、规则Mapper
 *
 * @author dej2vu
 * @create 2024-05-24
 */
@Mapper
public interface RaffleStrategyPrizeMapper {

    List<RaffleStrategyPrizePO> findAll();

}
