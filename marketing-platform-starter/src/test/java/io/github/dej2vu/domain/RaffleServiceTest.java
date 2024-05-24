package io.github.dej2vu.domain;


import com.alibaba.fastjson.JSON;
import io.github.dej2vu.domain.raffle.service.RaffleService;
import io.github.dej2vu.infrastructure.persistent.prize.mapper.PrizeMapper;
import io.github.dej2vu.infrastructure.persistent.prize.model.PrizePO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @description 抽奖单元测试
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RaffleServiceTest {

    @Resource
    private RaffleService raffleService;

    @Test
    public void should_success_assemble_strategy() {
        boolean result = raffleService.assembleStrategy("100001");
        Assertions.assertTrue(result);
    }

    @Test
    public void should_success_return_prize_code() {

        String prizeCodes = IntStream.range(0, 100)
                .mapToObj(i -> raffleService.getRandomResult("100001"))
                .collect(Collectors.joining(", ", "{", "}"));

        log.info("随机抽取100次：prizeCodes:{}", prizeCodes);
    }
}
