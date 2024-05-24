package io.github.dej2vu.infrastructure.persistent.raffle.mapper;

import io.github.dej2vu.infrastructure.persistent.raffle.entity.RaffleStrategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description 抽奖策略表Mapper
 *
 * @author dej2vu
 * @create 2024-05-24
 */
@Mapper
public interface RaffleStrategyMapper {

    List<RaffleStrategy> findAll();

}
