package io.github.dej2vu.infrastructure;

import com.alibaba.fastjson.JSON;
import io.github.dej2vu.infrastructure.persistent.prize.mapper.PrizeMapper;
import io.github.dej2vu.infrastructure.persistent.prize.model.PrizePO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * @description 奖品持久化单元测试
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PrizeMapperTest {

    @Resource
    private PrizeMapper prizeMapper;

    @Test
    public void should_return_all_prizes() {
        List<PrizePO> prizes = prizeMapper.findAll();
        log.info("测试结果：{}", JSON.toJSONString(prizes));
    }

}
