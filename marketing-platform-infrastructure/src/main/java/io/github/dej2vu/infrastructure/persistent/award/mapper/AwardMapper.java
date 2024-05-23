package io.github.dej2vu.infrastructure.persistent.award.mapper;

import io.github.dej2vu.infrastructure.persistent.award.entity.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description 奖品表Mapper
 *
 * @author dej2vu
 * @create 2024-05-24
 */
@Mapper
public interface AwardMapper {

    List<Award> findAll();

}
